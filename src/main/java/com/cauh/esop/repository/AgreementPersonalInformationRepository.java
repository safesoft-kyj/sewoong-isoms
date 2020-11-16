package com.cauh.esop.repository;

import com.cauh.esop.domain.AgreementPersonalInformation;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AgreementPersonalInformationRepository extends PagingAndSortingRepository<AgreementPersonalInformation, Integer>, QuerydslPredicateExecutor<AgreementPersonalInformation> {
}
