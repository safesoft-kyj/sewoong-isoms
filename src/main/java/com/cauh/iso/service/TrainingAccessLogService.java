package com.cauh.iso.service;

import com.cauh.common.entity.Account;
import com.cauh.iso.domain.ISO;
import com.cauh.iso.domain.ISOAccessLog;
import com.cauh.iso.domain.TrainingAccessLog;
import com.cauh.iso.domain.constant.DocumentAccessType;
import com.cauh.iso.domain.constant.TrainingLogType;
import com.cauh.iso.repository.TrainingAccessLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingAccessLogService {

    private final TrainingAccessLogRepository trainingAccessLogRepository;

    public Optional<TrainingAccessLog> save(Account user, TrainingLogType trainingLogType, DocumentAccessType accessType) {
        try {
            TrainingAccessLog trainingAccessLog = TrainingAccessLog.builder()
                    .user(user).trainingLogType(trainingLogType).accessType(accessType).build();
            return Optional.of(trainingAccessLogRepository.save(trainingAccessLog));
        } catch (Exception error) {
            log.warn("{} / 로그 저장 오류[{}]", accessType, trainingLogType.name());
            return Optional.empty();
        } finally {
            log.debug("{} / 로그 저장[{}]", accessType, trainingLogType.name());
        }
    }

    public Page<TrainingAccessLog> findAll(Pageable pageable) {
        return trainingAccessLogRepository.findAll(pageable);
    }
}
