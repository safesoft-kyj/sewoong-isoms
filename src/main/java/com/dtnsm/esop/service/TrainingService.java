package com.dtnsm.esop.service;

import com.dtnsm.common.entity.Account;
import com.dtnsm.esop.domain.TrainingLog;
import com.dtnsm.esop.domain.TrainingPeriod;
import com.dtnsm.esop.domain.constant.TrainingType;
import com.dtnsm.esop.repository.TrainingLogRepository;
import com.dtnsm.esop.repository.TrainingPeriodRepository;
import com.dtnsm.esop.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainingService {
    private final TrainingLogRepository trainingLogRepository;
    private final TrainingPeriodRepository trainingPeriodRepository;

    public boolean requestReTraining(Integer id, Account user) {
        Optional<TrainingLog> optional = trainingLogRepository.findById(id);
        if(optional.isPresent()) {
            Date startDate = DateUtils.truncate(new Date());
            Date endDate = DateUtils.addDay(startDate, 7);

            TrainingLog trainingLog = optional.get();

            TrainingPeriod trainingPeriod = new TrainingPeriod();
            trainingPeriod.setStartDate(startDate);
            trainingPeriod.setEndDate(endDate);
            trainingPeriod.setTrainingType(TrainingType.RE_TRAINING);
            trainingPeriod.setDocumentVersion(trainingLog.getDocumentVersion());
            trainingPeriod.setRetrainingUser(user);

            trainingPeriodRepository.save(trainingPeriod);

            return true;
        } else {
            return false;
        }
    }
}
