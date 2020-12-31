package com.cauh.iso.repository;

import com.cauh.iso.domain.DocumentVersion;
import com.cauh.iso.domain.ISOCertification;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ISOCertificationRepository extends PagingAndSortingRepository<ISOCertification, Integer>, QuerydslPredicateExecutor<ISOCertification> {
}
