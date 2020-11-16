package com.cauh.esop.service;

import com.cauh.esop.domain.DocumentAccessLog;
import com.cauh.esop.domain.DocumentVersion;
import com.cauh.esop.domain.constant.DocumentAccessType;
import com.cauh.esop.repository.DocumentAccessLogRepository;
import com.cauh.esop.repository.DocumentVersionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentAccessLogService {
    private final DocumentAccessLogRepository documentAccessLogRepository;
    private final DocumentVersionRepository documentVersionRepository;

    public Optional<DocumentAccessLog> save(DocumentVersion documentVersion, DocumentAccessType accessType) {
        try {
            DocumentAccessLog accessLog = DocumentAccessLog.builder()
                    .documentVersion(documentVersion)
                    .accessType(accessType)
                    .build();
            return Optional.of(documentAccessLogRepository.save(accessLog));
        } catch (Exception error) {
            log.warn("SOP/RD / {} / 로그 저장 오류 DocVerId:{}", accessType, documentVersion.getId());
            return Optional.empty();
        } finally {
            log.debug("SOP/RD / {} / 로그 저장[docVerId:{}]", accessType, documentVersion.getId());
        }
    }

    public Optional<DocumentAccessLog> save(String docVerId, DocumentAccessType accessType) {
        return save(documentVersionRepository.findById(docVerId).get(), accessType);
    }

    public Page<DocumentAccessLog> findAll(Pageable pageable) {
        return documentAccessLogRepository.findAll(pageable);
    }
}
