package com.cauh.esop.service;

import com.cauh.common.entity.Account;
import com.cauh.esop.domain.Quiz;
import com.cauh.esop.domain.TrainingLog;
import com.cauh.esop.domain.TrainingTestLog;
import com.cauh.esop.domain.constant.DeviationReportStatus;
import com.cauh.esop.domain.constant.TrainingStatus;
import com.cauh.esop.repository.TrainingLogRepository;
import com.cauh.esop.repository.TrainingTestLogRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingLogService {
    private final TrainingLogRepository trainingLogRepository;
    private final TrainingTestLogRepository trainingTestLogRepository;

    public Optional<TrainingLog> findById(Integer id) {
        return trainingLogRepository.findById(id);
    }

    public TrainingLog saveOrUpdate(TrainingLog trainingLog, Quiz quiz) {
        TrainingLog savedTrainingLog = trainingLogRepository.save(trainingLog);

        log.info("@Training Log User : {}, 저장 완료. {}, ", savedTrainingLog.getUser().getUsername(), savedTrainingLog.getId());
        if(!ObjectUtils.isEmpty(quiz)) {
            log.info("==> Training Test Log 저장 : {}", savedTrainingLog.getId());
            TrainingTestLog trainingTestLog = TrainingTestLog.builder()
                    .trainingLog(savedTrainingLog)
                    .score(savedTrainingLog.getScore())
                    .status(savedTrainingLog.getStatus())
                    .quiz(quiz)
                    .build();

            trainingTestLogRepository.save(trainingTestLog);

            log.info("<== Training Test Log 저장 완료.");
        }
        return savedTrainingLog;
    }

    public void saveAll(List<TrainingLog> trainingLogs, Account user) {
        for(TrainingLog trainingLog : trainingLogs) {
            trainingLog.setUser(user);
            trainingLog.setProgressPercent(100);
            trainingLog.setReportStatus(DeviationReportStatus.NA);
            trainingLog.setStatus(TrainingStatus.COMPLETED);
            trainingLogRepository.save(trainingLog);
        }
    }

    public Iterable<TrainingLog> findAll(Predicate predicate, OrderSpecifier<?> order) {
        return trainingLogRepository.findAll(predicate, order);
    }

    public Page<TrainingLog> findAll(Predicate predicate, Pageable pageable) {
        return trainingLogRepository.findAll(predicate, pageable);
    }

    public Optional<TrainingLog> findOne(Predicate predicate) {
        return trainingLogRepository.findOne(predicate);
    }
}
