package com.cauh.iso.repository;

import com.cauh.iso.domain.OfflineTraining;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OfflineTrainingRepository extends PagingAndSortingRepository<OfflineTraining, Integer>, QuerydslPredicateExecutor<OfflineTraining> {
}
