package com.cauh.iso.controller;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.QAccount;
import com.cauh.common.entity.constant.UserStatus;
import com.cauh.common.repository.UserRepository;
import com.cauh.common.security.annotation.CurrentUser;
import com.cauh.iso.domain.Notice;
import com.cauh.iso.domain.NoticeAttachFile;
import com.cauh.iso.domain.QNotice;
import com.cauh.iso.domain.TrainingMatrixFile;
import com.cauh.iso.domain.constant.ApprovalLineType;
import com.cauh.iso.domain.constant.PostStatus;
import com.cauh.iso.repository.ApprovalLineRepository;
import com.cauh.iso.security.annotation.IsAdmin;
import com.cauh.iso.service.FileStorageService;
import com.cauh.iso.service.NoticeService;
import com.cauh.iso.service.TrainingMatrixService;
import com.cauh.iso.validator.NoticeValidator;
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
import org.springframework.security.core.GrantedAuthority;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
@Slf4j
@SessionAttributes({"notice"})
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;
    private final NoticeValidator noticeValidator;
    private final FileStorageService fileStorageService;
    private final TrainingMatrixService trainingMatrixService;
    private final ApprovalLineRepository approvalLineRepository;
    private final UserRepository userRepository;

//    @Transactional
    @GetMapping("/notice")
    public String noticeList(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 15) Pageable pageable, @CurrentUser Account user, Model model) {
        model.addAttribute("reviewCount", approvalLineRepository.countApproval(ApprovalLineType.reviewer, user));
        model.addAttribute("approvalCount", approvalLineRepository.countApproval(ApprovalLineType.approver, user));

        //갖고있는 권한에 Admin이 있으면
        if(user.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(",")).contains("ADMIN")) {
            model.addAttribute("signUpRequestCount", userRepository.countByUserStatus(UserStatus.SIGNUP_REQUEST));
        }

        approvalLineRepository.countApproval(ApprovalLineType.reviewer, user);
        QNotice qNotice = QNotice.notice;

        //공지사항 리스트
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qNotice.deleted.eq(false));
        model.addAttribute("noticeList", noticeService.getList(builder, pageable));

        //공지사항(상단공지)
        Date today = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        builder.and(qNotice.topViewEndDate.goe(Date.valueOf(format.format(today))));
        model.addAttribute("topNoticeList", noticeService.getTopNotices(builder));

        Optional<TrainingMatrixFile> optionalTrainingMatrixFile = trainingMatrixService.findFirstByOrderByIdDesc();
        model.addAttribute("trainingMatrixFile", optionalTrainingMatrixFile.isPresent() ? optionalTrainingMatrixFile.get() : null);
        return "home/notice/list";
    }

    @IsAdmin
    @GetMapping("/notice/new")
    public String noticeNew(Model model) {
        model.addAttribute("notice", new Notice());
        return "home/notice/edit";
    }

    @IsAdmin
    @Transactional
    @PostMapping({"/notice/new", "/notice/{noticeId}/edit"})
    public String saveNotice(@PathVariable(value = "noticeId", required = false) Integer noticeId, @ModelAttribute("notice") Notice notice, BindingResult bindingResult, SessionStatus sessionStatus,
                             @RequestParam(value = "uploadingFiles") MultipartFile[] uploadingFiles,
                             RedirectAttributes attributes,
                             HttpServletRequest request) {
        noticeValidator.validate(notice, bindingResult);

        if(bindingResult.hasErrors()) {
            return "home/notice/edit";
        }

        notice.setPostStatus(PostStatus.NONE);

//        if(ObjectUtils.isEmpty(uploadingFiles) == false) {
//            for(MultipartFile uploadedFile : uploadingFiles) {
//                if(!ObjectUtils.isEmpty(uploadedFile) && !StringUtils.isEmpty(uploadedFile.getOriginalFilename())) {
//                    log.info("-----> {}", uploadedFile.getOriginalFilename());
//                    uploadedFile.transferTo(new File("C:\\Temp\\UploadFiles\\" + uploadedFile.getOriginalFilename()));
//                }
//            }
//        }
        Notice savedNotice = noticeService.save(notice, uploadingFiles);
        sessionStatus.setComplete();



        if(ObjectUtils.isEmpty(noticeId)) {
            attributes.addFlashAttribute("message", "공지사항이 저장 되었습니다.");
            return "redirect:/notice/" + savedNotice.getId()  + (StringUtils.isEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString());
        } else {
            attributes.addFlashAttribute("message", "공지사항이 수정 되었습니다.");
            return "redirect:/notice/{noticeId}" + (StringUtils.isEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString());
        }

    }

    @GetMapping("/notice/{noticeId}")
    public String noticeView(@PathVariable("noticeId") Integer noticeId, Model model, RedirectAttributes attributes) {
        Optional<Notice> notice = noticeService.getNotice(noticeId);
        if(notice.isPresent()) {
            model.addAttribute("notice", notice.get());
        } else {
            attributes.addFlashAttribute("message", "존재하지 않는 게시물 입니다.");
            return "redirect:/notice";
        }
        return "home/notice/view";
    }

    @IsAdmin
    @GetMapping("/ajax/notice/{noticeId}/send")
    @ResponseBody
    public Map<String, String> sendEmail(@PathVariable("noticeId") Integer noticeId) {
        Map<String, String> model = new HashMap<>();
        noticeService.sendMail(noticeId);
        model.put("result", "success");
        model.put("id", Integer.toString(noticeId));
        return model;
    }

    @IsAdmin
    @DeleteMapping("/notice/{noticeId}")
    public String noticeRemove(@PathVariable("noticeId") Integer noticeId, RedirectAttributes attributes, HttpServletRequest request) {
        Optional<Notice> notice = noticeService.getNotice(noticeId);
        if(notice.isPresent()) {
            noticeService.remove(notice.get());
            attributes.addFlashAttribute("message", "게시물이 삭제 되었습니다.");
            return "redirect:/notice?" + (StringUtils.isEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString());
        } else {
            attributes.addFlashAttribute("message", "존재하지 않는 게시물 입니다.");
            return "redirect:/notice";
        }
    }

    @IsAdmin
    @GetMapping("/notice/{noticeId}/edit")
    public String noticeEdit(@PathVariable("noticeId") Integer noticeId, Model model, RedirectAttributes attributes) {
        Optional<Notice> notice = noticeService.getNotice(noticeId);
        if(notice.isPresent()) {
            model.addAttribute("notice", notice.get());
        } else {
            attributes.addFlashAttribute("message", "존재하지 않는 게시물 입니다.");
            return "redirect:/notice";
        }
        return "home/notice/edit";
    }

    @GetMapping("/notice/{id}/downloadFile/{attachFileId:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") Integer id, @PathVariable("attachFileId") String attachFileId, HttpServletRequest request) {
        // Load file as Resource
        Optional<NoticeAttachFile> optionalAttachFile = noticeService.getAttachFile(attachFileId);
        if(optionalAttachFile.isPresent()) {
            NoticeAttachFile attachFile = optionalAttachFile.get();
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
