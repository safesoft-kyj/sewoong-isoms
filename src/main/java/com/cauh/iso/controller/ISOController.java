package com.cauh.iso.controller;

import com.cauh.common.entity.Account;
import com.cauh.common.security.annotation.CurrentUser;
import com.cauh.common.service.UserService;
import com.cauh.iso.domain.*;
import com.cauh.iso.domain.constant.ApprovalLineType;
import com.cauh.iso.domain.constant.ISOType;
import com.cauh.iso.domain.constant.PostStatus;
import com.cauh.iso.domain.constant.TrainingType;
import com.cauh.iso.security.annotation.IsAdmin;
import com.cauh.iso.service.*;
import com.cauh.iso.validator.ISOValidator;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Slf4j
@SessionAttributes({"iso"})
@RequiredArgsConstructor
public class ISOController {

    private final ISOService isoService;
    private final ISOValidator isoValidator;
    private final FileStorageService fileStorageService;
    private final UserService userService;


    @GetMapping("/iso-14155")
    public String ISOlist(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 15) Pageable pageable, @CurrentUser Account user, Model model) {
        QISO qISO = QISO.iSO;

        //공지사항 리스트
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qISO.deleted.eq(false));
        model.addAttribute("isoList", isoService.getList(builder, pageable));

        //공지사항(상단공지)
        Date today = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        builder.and(qISO.topViewEndDate.goe(Date.valueOf(format.format(today))));
        model.addAttribute("topISOList", isoService.getTopISOs(builder));

//        Optional<ISOTrainingMatrixFile> optionalTrainingMatrixFile = isotrainingMatrixService.findFirstByOrderByIdDesc();
//        model.addAttribute("isoTrainingMatrixFile", optionalTrainingMatrixFile.isPresent() ? optionalTrainingMatrixFile.get() : null);

        return "iso/iso14155/list";
    }

    @GetMapping("/iso-14155/new")
    public String newISO(Model model) {
        ISO iso = new ISO();
        iso.setTrainingAll(true);
        model.addAttribute("userMap", userService.getUserMap());
        model.addAttribute("iso", iso);

        return "iso/iso14155/edit";
    }

    @IsAdmin
    @GetMapping("/iso-14155/{isoId}/edit")
    public String isoEdit(@PathVariable("isoId") String isoId, Model model, RedirectAttributes attributes) {
        Optional<ISO> isoOptional = isoService.getISO(isoId);
        if (isoOptional.isPresent()) {

            ISO iso = isoOptional.get();
            if (iso.isTraining()) {
                ISOTrainingPeriod isoTrainingPeriod = iso.getIsoTrainingPeriod().size() > 0?iso.getIsoTrainingPeriod().get(0):null;
                iso.setStartDate(isoTrainingPeriod.getStartDate());
                iso.setEndDate(isoTrainingPeriod.getEndDate());

                //TrainingAll이 있으면 trainingAll로 세팅 아니면 유저별 참석
                if(iso.getIsoTrainingMatrix().stream().filter(d -> d.isTrainingAll()).count() > 0) {
                    iso.setTrainingAll(true);
                } else {
                    List<String> ids = iso.getIsoTrainingMatrix().stream().map(im -> Integer.toString(im.getUser().getId())).collect(Collectors.toList());
                    iso.setUserIds(ids.toArray(new String[ids.size()]));
                }
            }

            model.addAttribute("iso", iso);
            model.addAttribute("userMap", userService.getUserMap());
        } else {
            attributes.addFlashAttribute("message", "존재하지 않는 게시물 입니다.");
            return "redirect:/iso-14155";
        }
        return "iso/iso14155/edit";
    }

    @IsAdmin
    @Transactional
    @PostMapping({"/iso-14155/new", "/iso-14155/{isoId}/edit"})
    public String saveISO(@PathVariable(value = "isoId", required = false) String isoId,
                          @ModelAttribute("iso") ISO iso,
                          @RequestParam(value = "uploadingFile") MultipartFile uploadingFile,
                          BindingResult bindingResult, SessionStatus sessionStatus,
                          RedirectAttributes attributes, Model model,
                          HttpServletRequest request) {

        //업로드 진행할 파일의 이름을 넣음.
        iso.setUploadFileName(uploadingFile.getOriginalFilename());
        isoValidator.validate(iso, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("userMap", userService.getUserMap());
            return "iso/iso14155/edit";
        }

        iso.setPostStatus(PostStatus.NONE);
        iso.setIsoType(ISOType.ISO_14155);
        ISO savedISO = isoService.saveISO(iso, uploadingFile);

        sessionStatus.setComplete();

        if (ObjectUtils.isEmpty(isoId)) {
            attributes.addFlashAttribute("message", "ISO 14155가 저장 되었습니다.");
            return "redirect:/iso-14155/" + savedISO.getId() + (StringUtils.isEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString());
        } else {
            attributes.addFlashAttribute("message", "ISO 14155가 수정 되었습니다.");
            return "redirect:/iso-14155/{isoId}" + (StringUtils.isEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString());
        }

    }

    @GetMapping("/iso-14155/{isoId}")
    public String isoView(@PathVariable("isoId") String isoId, Model model, RedirectAttributes attributes) {
        Optional<ISO> iso = isoService.getISO(isoId);
        if (iso.isPresent()) {
            model.addAttribute("iso", iso.get());
        } else {
            attributes.addFlashAttribute("message", "존재하지 않는 ISO 게시물 입니다.");
            return "redirect:/iso-14155";
        }
        return "iso/iso14155/view";
    }

    @IsAdmin
    @GetMapping("/ajax/iso-14155/{isoId}/send")
    @ResponseBody
    public Map<String, String> sendEmail(@PathVariable("isoId") String isoId) {
        Map<String, String> model = new HashMap<>();
        isoService.sendMail(isoId);
        model.put("result", "success");
        model.put("id", isoId);
        return model;
    }

    @IsAdmin
    @DeleteMapping("/iso-14155/{isoId}")
    public String isoRemove(@PathVariable("isoId") String isoId, RedirectAttributes attributes, HttpServletRequest request) {
        Optional<ISO> iso = isoService.getISO(isoId);
        if (iso.isPresent()) {
            isoService.remove(iso.get());
            attributes.addFlashAttribute("message", "ISO-14155 게시물이 삭제 되었습니다.");
            return "redirect:/iso-14155?" + (StringUtils.isEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString());
        } else {
            attributes.addFlashAttribute("message", "존재하지 않는 ISO-14155 게시물 입니다.");
            return "redirect:/iso-14155";
        }
    }

    @GetMapping("/iso-14155/{id}/downloadFile/{attachFileId:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") Integer id, @PathVariable("attachFileId") String attachFileId,
                                                 HttpServletRequest request) {
        // Load file as Resource
        Optional<ISOAttachFile> optionalAttachFile = isoService.getAttachFile(attachFileId);
        if (optionalAttachFile.isPresent()) {
            ISOAttachFile attachFile = optionalAttachFile.get();
            Resource resource = fileStorageService.loadFileAsResource(attachFile.getFileName());

            // Try to determine file's content type
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                log.info("Could not determine file type.");
            }

            // Fallback to the default content type if type could not be determined
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachFile.getOriginalFileName() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.of(Optional.empty());
        }
    }

}
