package com.cauh.iso.service;

import com.cauh.iso.domain.*;
import com.cauh.iso.domain.constant.PostStatus;
import com.cauh.iso.repository.ISOAttachFileRepository;
import com.cauh.iso.repository.ISORepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ISOService {
    private final ISORepository isoRepository;
    private final ISOAttachFileRepository isoAttachFileRepository;
    private final FileStorageService fileStorageService;
    private final MailService mailService;

    public Iterable<ISO> getTopISOs(Predicate predicate){
        return isoRepository.findAll(predicate, Sort.by(Sort.Direction.DESC, "id"));
    }

    public Page<ISO> getList(Predicate predicate, Pageable pageable) {
        return isoRepository.findAll(predicate, pageable);
    }

    public ISO save(ISO iso, MultipartFile[] files) {
        ISO savedISO = isoRepository.save(iso);
        if(!ObjectUtils.isEmpty(iso.getAttachFiles())) {
            //TODO 수정필요.
            iso.getAttachFiles().stream().filter(f -> f.isDeleted()).forEach(removeFile ->
                    isoAttachFileRepository.deleteById(removeFile.getId()));
        }
        if(!ObjectUtils.isEmpty(files)) {
            for(MultipartFile file : files) {
                if(file.isEmpty() == false) {
                    String fileName = fileStorageService.storeFile(file, "iso_" + savedISO.getId());
                    log.info("==> upload fileName : {}, contentType : {}, fileSize : {}", fileName, file.getContentType(), file.getSize());

                    ISOAttachFile attacheFile = ISOAttachFile.builder()
                            .iso(savedISO)
                            .fileName(fileName)
                            .originalFileName(file.getOriginalFilename())
                            .fileType(file.getContentType())
                            .fileSize(file.getSize())
                            .build();
                    isoAttachFileRepository.save(attacheFile);
                }
            }
        }

        return savedISO;
    }

    public void remove(ISO iso) {
        iso.setDeleted(true);
        isoRepository.save(iso);
    }

    public Optional<ISO> getISO(Integer noticeId) {
        return isoRepository.findById(noticeId);
    }

    public Optional<ISOAttachFile> getAttachFile(String id) {
        return isoAttachFileRepository.findById(id);
    }


    public void sendMail(Integer isoId) {
        ISO iso = getISO(isoId).get();

        HashMap<String, Object> model = new HashMap<>();
        model.put("iso", iso);
        List<String> toList = mailService.getReceiveEmails();
        if(ObjectUtils.isEmpty(toList) == false) {

            Mail mail = Mail.builder()
                    .to(toList.toArray(new String[toList.size()]))
                    .subject("[ISO-MS/System/ISO-14155] " + iso.getTitle())
                    .model(model)
                    .templateName("iso-template")
                    .build();
            mailService.sendMail(mail);
        }

        iso.setPostStatus(PostStatus.SENT);
        isoRepository.save(iso);
    }


}
