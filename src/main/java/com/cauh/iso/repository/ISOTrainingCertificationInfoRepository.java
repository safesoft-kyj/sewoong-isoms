package com.cauh.iso.repository;

import com.cauh.iso.domain.ISOTrainingCertificationInfo;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ISOTrainingCertificationInfoRepository extends PagingAndSortingRepository<ISOTrainingCertificationInfo, Integer>, QuerydslPredicateExecutor<ISOTrainingCertificationInfo> {
}
