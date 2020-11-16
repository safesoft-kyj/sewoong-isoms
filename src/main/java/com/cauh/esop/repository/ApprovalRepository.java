package com.cauh.esop.repository;

import com.cauh.esop.domain.Approval;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ApprovalRepository extends PagingAndSortingRepository<Approval, Integer>, QuerydslPredicateExecutor<Approval>, ApprovalRepositoryCustom {
}
