package com.cauh.iso.repository;

import com.cauh.common.entity.Account;
import com.cauh.iso.domain.AgreementsWithdrawal;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface AgreementsWithdrawalRepository extends PagingAndSortingRepository<AgreementsWithdrawal, Integer>, QuerydslPredicateExecutor<AgreementsWithdrawal> {

    Optional<AgreementsWithdrawal> findByUser(Account user);

}
