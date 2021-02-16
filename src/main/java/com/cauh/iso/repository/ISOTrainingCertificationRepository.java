package com.cauh.iso.repository;

import com.cauh.common.entity.Account;
import com.cauh.iso.domain.ISO;
import com.cauh.iso.domain.ISOTrainingCertification;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.Optional;

public interface ISOTrainingCertificationRepository extends PagingAndSortingRepository<ISOTrainingCertification, Integer>, QuerydslPredicateExecutor<ISOTrainingCertification> {
    Integer countByCreatedDate(Date createdDate);

    Optional<ISOTrainingCertification> findByIsoAndUser(ISO iso, Account user);
}
