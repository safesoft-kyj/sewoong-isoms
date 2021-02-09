package com.cauh.iso.repository;

import com.cauh.iso.domain.Approval;
import com.cauh.iso.domain.constant.ApprovalStatus;
import com.cauh.iso.domain.constant.ReportType;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ApprovalRepository extends PagingAndSortingRepository<Approval, Integer>, QuerydslPredicateExecutor<Approval>, ApprovalRepositoryCustom {

    List<Approval> findAllByType(ReportType reportType, Sort sort);
    List<Approval> findAllByTypeAndStatus(ReportType reportType, ApprovalStatus status, Sort sort);
}
