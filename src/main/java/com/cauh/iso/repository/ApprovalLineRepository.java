package com.cauh.iso.repository;

import com.cauh.iso.domain.Approval;
import com.cauh.iso.domain.ApprovalLine;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ApprovalLineRepository extends PagingAndSortingRepository<ApprovalLine, Integer>, QuerydslPredicateExecutor<ApprovalLine>, ApprovalLineRepositoryCustom {

}
