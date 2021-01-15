package com.cauh.iso.repository;

import com.cauh.common.entity.Account;
import com.cauh.iso.domain.AgreementPersonalInformation;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface AgreementPersonalInformationRepository extends PagingAndSortingRepository<AgreementPersonalInformation, Integer>, QuerydslPredicateExecutor<AgreementPersonalInformation> {

    Optional<AgreementPersonalInformation> findByInternalUser(Account user);

}
