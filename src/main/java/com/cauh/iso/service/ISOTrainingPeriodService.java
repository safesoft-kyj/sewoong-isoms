package com.cauh.iso.service;

import com.cauh.iso.domain.ISO;
import com.cauh.iso.domain.ISOTrainingPeriod;
import com.cauh.iso.domain.QISOTrainingPeriod;
import com.cauh.iso.domain.constant.TrainingType;
import com.cauh.iso.repository.ISOTrainingPeriodRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ISOTrainingPeriodService {

    private final ISOTrainingPeriodRepository isoTrainingPeriodRepository;

    public void saveAll(ISO savedISO, ISO iso) {
        if(ObjectUtils.isEmpty(iso.getIsoTrainingPeriods())) { //신규 생성 시
            ISOTrainingPeriod isoTrainingPeriod = ISOTrainingPeriod.builder()
                    .iso(savedISO)
                    .trainingType(TrainingType.SELF)
                    .startDate(iso.getStartDate())
                    .endDate(iso.getEndDate()).build();
            isoTrainingPeriodRepository.save(isoTrainingPeriod);
        } else { //수정 시,
            //ISO Training Period중에서 SELF Type인 것만 불러옴.
            List<ISOTrainingPeriod> isoTrainingPeriodList = iso.getIsoTrainingPeriods().stream().filter(i -> i.getTrainingType() == TrainingType.SELF).collect(Collectors.toList());
            for(ISOTrainingPeriod isoTrainingPeriod : isoTrainingPeriodList) {
                isoTrainingPeriod.setStartDate(iso.getStartDate());
                isoTrainingPeriod.setEndDate(iso.getEndDate());
                isoTrainingPeriodRepository.save(isoTrainingPeriod);
            }
        }
    }

}
