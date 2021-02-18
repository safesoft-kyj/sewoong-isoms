package com.cauh.iso.repository;

import com.cauh.common.entity.Account;
import com.cauh.iso.domain.ISO;
import com.cauh.iso.domain.ISOTrainingCertification;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

public interface ISOTrainingCertificationRepository extends PagingAndSortingRepository<ISOTrainingCertification, Integer>, QuerydslPredicateExecutor<ISOTrainingCertification> {

    @Transactional(readOnly = true)
    //년도가 동일한 Certification만 모으기.
    @Query(value = "SELECT count(isoCert.id) " +
            "from ISOTrainingCertification isoCert " +
            "WHERE function('CONVERT', VARCHAR(4), isoCert.createdDate, 120) = function('CONVERT', VARCHAR(4), :createdDate, 120)")
    Integer countByCreatedDate(Date createdDate);

    Optional<ISOTrainingCertification> findByIsoAndUser(ISO iso, Account user);
}
