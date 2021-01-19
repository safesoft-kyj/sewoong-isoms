package com.cauh.iso.controller;

import com.cauh.common.entity.Account;
import com.cauh.common.security.annotation.CurrentUser;
import com.cauh.iso.domain.*;
import com.cauh.iso.domain.constant.ApprovalLineType;
import com.cauh.iso.domain.constant.PostStatus;
import com.cauh.iso.security.annotation.IsAdmin;
import com.cauh.iso.service.ISOService;
import com.cauh.iso.service.NoticeService;
import com.cauh.iso.service.TrainingMatrixService;
import com.cauh.iso.validator.ISOValidator;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    private final TrainingMatrixService trainingMatrixService;

    @GetMapping("/iso-14155")
    public String ISOlist(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 15) Pageable pageable, @CurrentUser Account user, Model model){
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

        Optional<TrainingMatrixFile> optionalTrainingMatrixFile = trainingMatrixService.findFirstByOrderByIdDesc();
        model.addAttribute("trainingMatrixFile", optionalTrainingMatrixFile.isPresent() ? optionalTrainingMatrixFile.get() : null);
        return "iso/iso14155/list";
    }

    @GetMapping("/iso-14155/new")
    public String newISO(Model model){
        model.addAttribute("iso", new ISO());
        return "iso/iso14155/edit";
    }

    @IsAdmin
    @GetMapping("/iso-14155/{isoId}/edit")
    public String noticeEdit(@PathVariable("isoId") Integer isoId, Model model, RedirectAttributes attributes) {
        Optional<ISO> iso = isoService.getISO(isoId);
        if(iso.isPresent()) {
            model.addAttribute("iso", iso.get());
        } else {
            attributes.addFlashAttribute("message", "존재하지 않는 게시물 입니다.");
            return "redirect:/iso-14155";
        }
        return "iso/iso14155/edit";
    }

    @IsAdmin
    @Transactional
    @PostMapping({"/iso-14155/new", "/iso-14155/{isoId}/edit"})
    public String saveNotice(@PathVariable(value = "isoId", required = false) Integer isoId,
                             @ModelAttribute("iso") ISO iso,
                             BindingResult bindingResult, SessionStatus sessionStatus,
                             @RequestParam(value = "uploadingFiles") MultipartFile[] uploadingFiles,
                             RedirectAttributes attributes,
                             HttpServletRequest request) {

        //업로드 진행할 파일의 이름을 넣음.
        List<String> uploadFilnames = Arrays.stream(uploadingFiles).distinct().map(file -> file.getOriginalFilename()).collect(Collectors.toList());
        iso.setUploadFileNames(uploadFilnames);

        log.info("File names : {}", uploadFilnames);

        isoValidator.validate(iso, bindingResult);

        if(bindingResult.hasErrors()) {
            return "iso/iso14155/edit";
        }

        iso.setPostStatus(PostStatus.NONE);

        ISO savedISO = isoService.save(iso, uploadingFiles);
        sessionStatus.setComplete();



        if(ObjectUtils.isEmpty(isoId)) {
            attributes.addFlashAttribute("message", "ISO 14155가 저장 되었습니다.");
            return "redirect:/iso-14155/" + savedISO.getId()  + (StringUtils.isEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString());
        } else {
            attributes.addFlashAttribute("message", "ISO 14155가 수정 되었습니다.");
            return "redirect:/iso-14155/{isoId}" + (StringUtils.isEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString());
        }

    }

    @GetMapping("/iso-14155/{isoId}")
    public String noticeView(@PathVariable("isoId") Integer isoId, Model model, RedirectAttributes attributes) {
        Optional<ISO> iso = isoService.getISO(isoId);
        if(iso.isPresent()) {
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
    public Map<String, String> sendEmail(@PathVariable("isoId") Integer isoId) {
        Map<String, String> model = new HashMap<>();
        isoService.sendMail(isoId);
        model.put("result", "success");
        model.put("id", Integer.toString(isoId));
        return model;
    }

    @IsAdmin
    @DeleteMapping("/iso-14155/{isoId}")
    public String noticeRemove(@PathVariable("isoId") Integer isoId, RedirectAttributes attributes, HttpServletRequest request) {
        Optional<ISO> iso = isoService.getISO(isoId);
        if(iso.isPresent()) {
            isoService.remove(iso.get());
            attributes.addFlashAttribute("message", "ISO-14155 게시물이 삭제 되었습니다.");
            return "redirect:/iso-14155?" + (StringUtils.isEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString());
        } else {
            attributes.addFlashAttribute("message", "존재하지 않는 ISO-14155 게시물 입니다.");
            return "redirect:/iso-14155";
        }
    }

}
