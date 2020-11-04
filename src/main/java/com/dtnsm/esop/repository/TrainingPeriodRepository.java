package com.dtnsm.esop.repository;

import com.dtnsm.esop.domain.TrainingPeriod;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TrainingPeriodRepository extends PagingAndSortingRepository<TrainingPeriod, Integer>, QuerydslPredicateExecutor<TrainingPeriod> {
}
