package com.cauh.iso.repository;

import com.cauh.iso.domain.Approval;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ApprovalRepository extends PagingAndSortingRepository<Approval, Integer>, QuerydslPredicateExecutor<Approval>, ApprovalRepositoryCustom {
}
