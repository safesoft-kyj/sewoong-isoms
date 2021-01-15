package com.cauh.iso.repository;

import com.cauh.common.entity.Account;
import com.cauh.iso.domain.ConfidentialityPledge;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ConfidentialityPledgeRepository extends PagingAndSortingRepository<ConfidentialityPledge, Integer>, QuerydslPredicateExecutor<ConfidentialityPledge> {

    Optional<ConfidentialityPledge> findByInternalUser(Account user);
}
