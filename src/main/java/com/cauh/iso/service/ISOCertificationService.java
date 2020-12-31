package com.cauh.iso.service;

import com.cauh.iso.domain.ISOCertification;
import com.cauh.iso.domain.ISOCertificationAttachFile;
import com.cauh.iso.repository.ISOCertificationAttachFileRepository;
import com.cauh.iso.repository.ISOCertificationRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ISOCertificationService {

    private final ISOCertificationRepository isoCertificationRepository;
    private final ISOCertificationAttachFileRepository isoCertificationAttachFileRepository;
    private final FileStorageService fileStorageService;

    public Page<ISOCertification> getList(Predicate predicate, Pageable pageable) {
        return isoCertificationRepository.findAll(predicate, pageable);
    }

    public Optional<ISOCertification> getCertification(Integer id){
        return isoCertificationRepository.findById(id);
    }

    public Optional<ISOCertificationAttachFile> getAttachFile(String id) {
        return isoCertificationAttachFileRepository.findById(id);
    }

    public void remove(ISOCertification isoCertification) {
        isoCertification.setDeleted(true);
        List<ISOCertificationAttachFile> isoCertificationAttachFiles = isoCertification.getAttachFiles();
        for(ISOCertificationAttachFile isoCertificationAttachFile : isoCertificationAttachFiles) {
            isoCertificationAttachFile.setDeleted(true);
        }
        isoCertificationRepository.save(isoCertification);
    }

    public ISOCertification save(ISOCertification isoCertification, MultipartFile[] files){
        ISOCertification savedISOCertification = isoCertificationRepository.save(isoCertification);
        if(!ObjectUtils.isEmpty(isoCertification.getAttachFiles())) {
            isoCertification.getAttachFiles().stream().filter(f -> f.isDeleted()).forEach(removeFile ->
                    isoCertificationAttachFileRepository.deleteById(removeFile.getId()));
//                noticeAttacheFileRepository.save(removeFile));
        }
        if(!ObjectUtils.isEmpty(files)) {
            for(MultipartFile file : files) {
                if(file.isEmpty() == false) {
                    String fileName = fileStorageService.storeFile(file, "iso_cert_" + savedISOCertification.getId());
                    log.info("==> upload fileName : {}, contentType : {}, fileSize : {}", fileName, file.getContentType(), file.getSize());

                    ISOCertificationAttachFile attacheFile = ISOCertificationAttachFile.builder()
                            .isoCertification(isoCertification)
                            .fileName(fileName)
                            .originalFileName(file.getOriginalFilename())
                            .fileType(file.getContentType())
                            .fileSize(file.getSize())
                            .build();
                    isoCertificationAttachFileRepository.save(attacheFile);
                }
            }
        }
        return savedISOCertification;
    }

}
