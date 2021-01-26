package com.cauh.iso.repository;

import com.cauh.iso.domain.ISOTrainingTestLog;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ISOTrainingTestLogRepository extends PagingAndSortingRepository<ISOTrainingTestLog, Integer>, QuerydslPredicateExecutor<ISOTrainingTestLog> {
}
