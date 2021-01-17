package com.cauh.iso.repository;

import com.cauh.iso.domain.AgreementsWithdrawal;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AgreementsWithdrawalRepository extends PagingAndSortingRepository<AgreementsWithdrawal, Integer>, QuerydslPredicateExecutor<AgreementsWithdrawal> {
}
