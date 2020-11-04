package com.dtnsm.esop.repository;

import com.dtnsm.esop.domain.NonDisclosureAgreement;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NonDisclosureAgreementRepository extends PagingAndSortingRepository<NonDisclosureAgreement, Integer>, QuerydslPredicateExecutor<NonDisclosureAgreement> {
}
