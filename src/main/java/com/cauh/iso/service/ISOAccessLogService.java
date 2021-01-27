package com.cauh.iso.service;

import com.cauh.iso.domain.*;
import com.cauh.iso.domain.constant.DocumentAccessType;
import com.cauh.iso.domain.constant.ISOType;
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
                    .isoType(iso.getIsoType())
                    .accessType(accessType)
                    .build();
            return Optional.of(isoAccessLogRepository.save(accessLog));
        } catch (Exception error) {
            log.warn("ISO / {} / 로그 저장 오류 isoId:{}", accessType, iso.getId());
            return Optional.empty();
        } finally {
            log.debug("ISO / {} / 로그 저장[isoId:{}]", accessType, iso.getId());
        }
    }

    public Optional<ISOAccessLog> save(ISOCertification isoCertification, DocumentAccessType accessType) {
        try {
            ISOAccessLog accessLog = ISOAccessLog.builder()
                    .isoCertification(isoCertification)
                    .isoType(ISOType.ISO_CERT_STATUS)
                    .accessType(accessType)
                    .build();

            return Optional.of(isoAccessLogRepository.save(accessLog));
        } catch (Exception error) {
            log.warn("ISO 인증 현황 / {} / 로그 저장 오류 isoCertification:{}", accessType, isoCertification.getId());
            return Optional.empty();
        } finally {
            log.debug("ISO 인증 현황 / {} / 로그 저장[isoCertification:{}]", accessType, isoCertification.getId());
        }
    }

    public Optional<ISOAccessLog> save(ISOTrainingCertification isoTrainingCertification, DocumentAccessType accessType) {
        try {
            ISOAccessLog accessLog = ISOAccessLog.builder()
                    .isoTrainingCertification(isoTrainingCertification)
                    .isoType(ISOType.ISO_14155_CERT)
                    .accessType(accessType)
                    .build();

            return Optional.of(isoAccessLogRepository.save(accessLog));
        } catch (Exception error) {
            log.warn("ISO-14155 수료증 / {} / 로그 저장 오류 isoTrainingCertification ID:{}", accessType, isoTrainingCertification.getId());
            return Optional.empty();
        } finally {
            log.debug("ISO-14155 수료증 / {} / 로그 저장[isoTrainingCertification ID :{}]", accessType, isoTrainingCertification.getId());
        }
    }


    public Optional<ISOAccessLog> save(String isoId, DocumentAccessType accessType) {
        return save(isoRepository.findById(isoId).get(), accessType);
    }

    public Page<ISOAccessLog> findAll(Pageable pageable) {
        return isoAccessLogRepository.findAll(pageable);
    }

}
