package com.dtnsm.esop.service;

import com.dtnsm.esop.domain.Mail;
import com.dtnsm.esop.domain.Notice;
import com.dtnsm.esop.domain.NoticeAttachFile;
import com.dtnsm.esop.domain.constant.NoticeStatus;
import com.dtnsm.esop.repository.NoticeAttacheFileRepository;
import com.dtnsm.esop.repository.NoticeRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final NoticeAttacheFileRepository noticeAttacheFileRepository;
    private final FileStorageService fileStorageService;

    private final MailService mailService;

    public Iterable<Notice> getTopNotices(Predicate predicate) {
        return noticeRepository.findAll(predicate, Sort.by(Sort.Direction.DESC, "id"));
    }

    public Page<Notice> getList(Predicate predicate, Pageable pageable) {
        return noticeRepository.findAll(predicate, pageable);
    }

    public Notice save(Notice notice, MultipartFile[] files) {
        Notice savedNotice = noticeRepository.save(notice);
        if(!ObjectUtils.isEmpty(notice.getAttachFiles())) {
            notice.getAttachFiles().stream().filter(f -> f.isDeleted()).forEach(removeFile ->
                    noticeAttacheFileRepository.deleteById(removeFile.getId()));
//                noticeAttacheFileRepository.save(removeFile));
        }
        if(!ObjectUtils.isEmpty(files)) {
            for(MultipartFile file : files) {
                if(file.isEmpty() == false) {
                    String fileName = fileStorageService.storeFile(file, "notice_" + savedNotice.getId());
                    log.info("==> upload fileName : {}, contentType : {}, fileSize : {}", fileName, file.getContentType(), file.getSize());

                    NoticeAttachFile attacheFile = NoticeAttachFile.builder()
                            .notice(savedNotice)
                            .fileName(fileName)
                            .originalFileName(file.getOriginalFilename())
                            .fileType(file.getContentType())
                            .fileSize(file.getSize())
                            .build();
                    noticeAttacheFileRepository.save(attacheFile);
                }
            }
        }

        return savedNotice;
    }

    public void remove(Notice notice) {
        notice.setDeleted(true);
        noticeRepository.save(notice);
    }

    public Optional<Notice> getNotice(Integer noticeId) {
        return noticeRepository.findById(noticeId);
    }

    public Optional<NoticeAttachFile> getAttachFile(String id) {
        return noticeAttacheFileRepository.findById(id);
    }


    public void sendMail(Integer noticeId) {
        Notice notice = getNotice(noticeId).get();

        HashMap<String, Object> model = new HashMap<>();
        model.put("notice", notice);
        List<String> toList = mailService.getReceiveEmails();
        if(ObjectUtils.isEmpty(toList) == false) {

            Mail mail = Mail.builder()
                    .to(toList.toArray(new String[toList.size()]))
                    .subject("[공지사항] " + notice.getTitle())
                    .model(model)
                    .templateName("notice-template")
                    .build();
            mailService.sendMail(mail);
        }

        notice.setNoticeStatus(NoticeStatus.SENT);
        noticeRepository.save(notice);
    }
}
