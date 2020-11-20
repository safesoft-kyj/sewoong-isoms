package com.cauh.iso.repository;

import com.cauh.iso.domain.TrainingPeriod;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TrainingPeriodRepository extends PagingAndSortingRepository<TrainingPeriod, Integer>, QuerydslPredicateExecutor<TrainingPeriod> {
}
