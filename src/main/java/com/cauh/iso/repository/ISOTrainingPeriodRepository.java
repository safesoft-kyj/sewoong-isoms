package com.cauh.iso.repository;

import com.cauh.iso.domain.ISO;
import com.cauh.iso.domain.ISOTrainingMatrix;
import com.cauh.iso.domain.ISOTrainingPeriod;
import com.cauh.iso.domain.constant.TrainingType;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ISOTrainingPeriodRepository extends PagingAndSortingRepository<ISOTrainingPeriod, Integer>, QuerydslPredicateExecutor<ISOTrainingPeriod> {
    List<ISOTrainingPeriod> findAllByIsoAndTrainingType(ISO iso, TrainingType trainingType);

}
