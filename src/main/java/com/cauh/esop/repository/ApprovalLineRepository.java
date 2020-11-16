package com.cauh.esop.repository;

import com.cauh.esop.domain.ApprovalLine;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ApprovalLineRepository extends PagingAndSortingRepository<ApprovalLine, Integer>, QuerydslPredicateExecutor<ApprovalLine>, ApprovalLineRepositoryCustom {
}
