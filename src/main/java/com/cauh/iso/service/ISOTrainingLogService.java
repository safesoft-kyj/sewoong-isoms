package com.cauh.iso.service;

import com.cauh.iso.domain.*;
import com.cauh.iso.repository.ISOTrainingLogRepository;
import com.cauh.iso.repository.ISOTrainingTestLogRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ISOTrainingLogService {

    private final ISOTrainingLogRepository isoTrainingLogRepository;
    private final ISOTrainingTestLogRepository isoTrainingTestLogRepository;

    public ISOTrainingLog saveOrUpdate(ISOTrainingLog isoTrainingLog, Quiz quiz) {
        ISOTrainingLog savedTrainingLog = isoTrainingLogRepository.save(isoTrainingLog);

        log.info("@ISO Training Log User : {}, 저장 완료. {}, ", savedTrainingLog.getUser().getUsername(), savedTrainingLog.getId());
        if(!ObjectUtils.isEmpty(quiz)) {
            log.info("==> Training Test Log 저장 : {}", savedTrainingLog.getId());
            ISOTrainingTestLog trainingTestLog = ISOTrainingTestLog.builder()
                    .isoTrainingLog(savedTrainingLog)
                    .score(savedTrainingLog.getScore())
                    .status(savedTrainingLog.getStatus())
                    .quiz(quiz)
                    .build();
            isoTrainingTestLogRepository.save(trainingTestLog);

            log.info("<== ISO Training Test Log 저장 완료.");
        }
        return savedTrainingLog;
    }

    public Optional<ISOTrainingLog> findById(Integer trainingId) {
        return isoTrainingLogRepository.findById(trainingId);
    }

    public Iterable<ISOTrainingLog> findAll(Predicate predicate, OrderSpecifier<?> order) {
        return isoTrainingLogRepository.findAll(predicate, order);
    }

    public Page<ISOTrainingLog> findAll(Predicate predicate, Pageable pageable) {
        return isoTrainingLogRepository.findAll(predicate, pageable);
    }

    public Optional<ISOTrainingLog> findOne(Predicate predicate) {
        return isoTrainingLogRepository.findOne(predicate);
    }

}
