package com.cauh.esop.repository;

import com.cauh.esop.domain.OfflineTraining;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OfflineTrainingRepository extends PagingAndSortingRepository<OfflineTraining, Integer>, QuerydslPredicateExecutor<OfflineTraining> {
}
