package com.dtnsm.esop.repository;

import com.dtnsm.esop.domain.TrainingLog;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TrainingLogRepository extends PagingAndSortingRepository<TrainingLog, Integer>, QuerydslPredicateExecutor<TrainingLog> {
}
