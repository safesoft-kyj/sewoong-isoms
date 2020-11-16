package com.cauh.esop.repository;

import com.cauh.esop.domain.TrainingLog;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TrainingLogRepository extends PagingAndSortingRepository<TrainingLog, Integer>, QuerydslPredicateExecutor<TrainingLog> {
}
