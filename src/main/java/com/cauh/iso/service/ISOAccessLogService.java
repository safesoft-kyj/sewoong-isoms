package com.cauh.iso.service;

import com.cauh.iso.domain.DocumentAccessLog;
import com.cauh.iso.domain.DocumentVersion;
import com.cauh.iso.domain.ISO;
import com.cauh.iso.domain.ISOAccessLog;
import com.cauh.iso.domain.constant.DocumentAccessType;
import com.cauh.iso.repository.ISOAccessLogRepository;
import com.cauh.iso.repository.ISORepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ISOAccessLogService {
    private final ISORepository isoRepository;
    private final ISOAccessLogRepository isoAccessLogRepository;

    public Optional<ISOAccessLog> save(ISO iso, DocumentAccessType accessType) {
        try {
            ISOAccessLog accessLog = ISOAccessLog.builder()
                    .iso(iso)
                    .accessType(accessType)
                    .build();
            return Optional.of(isoAccessLogRepository.save(accessLog));
        } catch (Exception error) {
            log.warn("ISO / {} / 로그 저장 오류 DocVerId:{}", accessType, iso.getId());
            return Optional.empty();
        } finally {
            log.debug("ISO / {} / 로그 저장[docVerId:{}]", accessType, iso.getId());
        }
    }

    public Optional<ISOAccessLog> save(String isoId, DocumentAccessType accessType) {
        return save(isoRepository.findById(isoId).get(), accessType);
    }

    public Page<ISOAccessLog> findAll(Pageable pageable) {
        return isoAccessLogRepository.findAll(pageable);
    }

}
