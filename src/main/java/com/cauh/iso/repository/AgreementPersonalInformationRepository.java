package com.cauh.iso.repository;

import com.cauh.common.entity.Account;
import com.cauh.iso.domain.AgreementPersonalInformation;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AgreementPersonalInformationRepository extends PagingAndSortingRepository<AgreementPersonalInformation, Integer>, QuerydslPredicateExecutor<AgreementPersonalInformation> {

    Optional<AgreementPersonalInformation> findByInternalUser(Account user);

}
