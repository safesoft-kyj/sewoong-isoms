package com.cauh.iso.repository;

import com.cauh.iso.domain.ISOTrainingLog;
import com.cauh.iso.domain.ISOTrainingPeriod;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ISOTrainingLogRepository extends PagingAndSortingRepository<ISOTrainingLog, Integer>, QuerydslPredicateExecutor<ISOTrainingLog> {
}
