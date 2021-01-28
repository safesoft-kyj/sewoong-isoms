package com.cauh.iso.controller;

import com.cauh.common.entity.Account;
import com.cauh.common.security.annotation.CurrentUser;
import com.cauh.iso.component.DocumentViewer;
import com.cauh.iso.domain.ISOAccessLog;
import com.cauh.iso.domain.ISOCertification;
import com.cauh.iso.domain.ISOCertificationAttachFile;
import com.cauh.iso.domain.QISOCertification;
import com.cauh.iso.domain.constant.DocumentAccessType;
import com.cauh.iso.domain.constant.ISOType;
import com.cauh.iso.domain.constant.PostStatus;
import com.cauh.iso.exception.MyFileNotFoundException;
import com.cauh.iso.security.annotation.IsAdmin;
import com.cauh.iso.service.FileStorageService;
import com.cauh.iso.service.ISOAccessLogService;
import com.cauh.iso.service.ISOCertificationService;
import com.cauh.iso.validator.ISOCertificationValidator;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
@Slf4j
@SessionAttributes({"isoCertification"})
@RequiredArgsConstructor
public class ISOCertificationsController {

    private final ISOCertificationService isoCertificationService;
    private final ISOCertificationValidator isoCertificationValidator;
    private final ISOAccessLogService isoAccessLogService;
    private final FileStorageService fileStorageService;
    private final DocumentViewer documentViewer;

    @GetMapping("/certifications")
    public String CertList(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 15) Pageable pageable, @CurrentUser Account user, Model model) {
        QISOCertification qISOCertification = QISOCertification.iSOCertification;

        //인증현황 리스트
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qISOCertification.deleted.eq(false));
        model.addAttribute("certificationList", isoCertificationService.getList(builder, pageable));

        return "home/certifications/list";
    }


    @IsAdmin
    @GetMapping("/certifications/new")
    public String certificationNew(Model model) {
        model.addAttribute("isoCertification", new ISOCertification());
        return "home/certifications/edit";
    }

    @GetMapping("/certifications/{certificationId}")
    public String certificationsView(@PathVariable("certificationId") Integer noticeId, Model model, RedirectAttributes attributes) {
        Optional<ISOCertification> isoCertification = isoCertificationService.getCertification(noticeId);
        if(isoCertification.isPresent()) {
            model.addAttribute("isoCertification", isoCertification.get());
        } else {
            attributes.addFlashAttribute("message", "존재하지 않는 게시물 입니다.");
            return "redirect:/notice";
        }
        return "home/certifications/view";
    }

    @IsAdmin
    @GetMapping("/certifications/{certificationId}/edit")
    public String noticeEdit(@PathVariable("certificationId") Integer noticeId, Model model, RedirectAttributes attributes) {
        Optional<ISOCertification> isoCertification = isoCertificationService.getCertification(noticeId);
        if(isoCertification.isPresent()) {
            model.addAttribute("isoCertification", isoCertification.get());
        } else {
            attributes.addFlashAttribute("message", "존재하지 않는 게시물 입니다.");
            return "redirect:/certifications";
        }
        return "home/certifications/edit";
    }

    @IsAdmin
    @DeleteMapping("/certifications/{certificationId}")
    public String noticeRemove(@PathVariable("certificationId") Integer noticeId, RedirectAttributes attributes, HttpServletRequest request) {
        Optional<ISOCertification> isoCertification = isoCertificationService.getCertification(noticeId);
        if(isoCertification.isPresent()) {
            isoCertificationService.remove(isoCertification.get());
            attributes.addFlashAttribute("message", "게시물이 삭제 되었습니다.");
            return "redirect:/certifications?" + (StringUtils.isEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString());
        } else {
            attributes.addFlashAttribute("message", "존재하지 않는 게시물 입니다.");
            return "redirect:/certifications";
        }
    }

    @IsAdmin
    @Transactional
    @PostMapping({"/certifications/new", "/certifications/{certId}/edit"})
        public String saveNotice(@PathVariable(value = "certId", required = false) Integer certId, @ModelAttribute("isoCertification") ISOCertification isoCertification, BindingResult bindingResult, SessionStatus sessionStatus,
                             @RequestParam(value = "uploadingFiles") MultipartFile[] uploadingFiles,
                             RedirectAttributes attributes, HttpServletRequest request) {

        if(!ObjectUtils.isEmpty(isoCertification)){
            isoCertification.setUploadingFiles(uploadingFiles);
        }

        isoCertificationValidator.validate(isoCertification, bindingResult);

        if(bindingResult.hasErrors()) {
            return "home/certifications/edit";
        }

        isoCertification.setPostStatus(PostStatus.NONE);
        ISOCertification savedISOCertification = isoCertificationService.save(isoCertification, uploadingFiles);
        sessionStatus.setComplete();

//        if(ObjectUtils.isEmpty(certId)) {
//            attributes.addFlashAttribute("message", "ISO Certification이 저장 되었습니다.");
//            return "redirect:/certifications/" + savedISOCertification.getId()  + (StringUtils.isEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString());
//        } else {
//            attributes.addFlashAttribute("message", "ISO Certification이 수정 되었습니다.");
//            return "redirect:/certifications/{certId}" + (StringUtils.isEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString());
//        }

        attributes.addFlashAttribute("message", "ISO Certification이 저장 되었습니다.");
        return "redirect:/certifications/";
    }

    @GetMapping("/certifications/{id}/downloadFile/{attachFileId:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") Integer id, @PathVariable("attachFileId") String attachFileId, HttpServletRequest request) {
        // Load file as Resource
        Optional<ISOCertificationAttachFile> optionalAttachFile = isoCertificationService.getAttachFile(attachFileId);
        if(optionalAttachFile.isPresent()) {
            ISOCertificationAttachFile attachFile = optionalAttachFile.get();
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

    @GetMapping("/certifications/view/{certId}")
    public void viewer(@PathVariable("certId") Integer id, HttpServletResponse response) throws Exception {
        // Load file as Resource
        Optional<ISOCertification> isoCertificationOptional = isoCertificationService.getCertification(id);
        if(isoCertificationOptional.isPresent()) {
            ISOCertification isoCertification = isoCertificationOptional.get();

            //1번째 파일
            ISOCertificationAttachFile isoCertificationAttachFile = isoCertification.getAttachFiles().get(0);

            if(!ObjectUtils.isEmpty(isoCertificationAttachFile)) {
                Resource resource = fileStorageService.loadFileAsResource(isoCertificationAttachFile.getFileName());
                documentViewer.toHTML("pdf", resource.getInputStream(), response.getOutputStream());
            }else {
                throw new RuntimeException("첨부된 파일이 존재하지 않습니다.");
            }
        }
    }

    @GetMapping("/certifications/download/{certId}")
    @ResponseBody
    public ResponseEntity<Resource> download(@PathVariable("certId") Integer id, HttpServletRequest request) {
        // Load file as Resource
        Optional<ISOCertification> isoCertificationOptional = isoCertificationService.getCertification(id);

        if(isoCertificationOptional.isPresent()) {
            ISOCertification isoCertification = isoCertificationOptional.get();
            if(!ObjectUtils.isEmpty(isoCertification.getAttachFiles())) {
                //1번째 파일
                ISOCertificationAttachFile isoCertificationAttachFile = isoCertification.getAttachFiles().get(0);
                Resource resource = fileStorageService.loadFileAsResource(isoCertificationAttachFile.getFileName());

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

                //ISO Access Log 추가
                isoAccessLogService.save(isoCertification, DocumentAccessType.VIEWER);

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + isoCertificationAttachFile.getOriginalFileName() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.of(Optional.empty());
            }
        } else {
            return ResponseEntity.of(Optional.empty());
        }
    }

    @GetMapping("/ajax/certifications/isFile/{certId}")
    @ResponseBody
    public Boolean IsCertFile(@PathVariable("certId") Integer certId){
        Optional<ISOCertification> isoCertificationOptional = isoCertificationService.getCertification(certId);
        if(isoCertificationOptional.isPresent()){
            ISOCertification isoCertification = isoCertificationOptional.get();
            if(!ObjectUtils.isEmpty(isoCertification.getAttachFiles())){
                try{
                    Resource resource = fileStorageService.loadFileAsResource(isoCertification.getAttachFiles().get(0).getFileName());
                } catch(MyFileNotFoundException e){
                    log.info("File is Not Found");
                    return false;
                }
                return true;
            }
        }
        return false;
    }
}


