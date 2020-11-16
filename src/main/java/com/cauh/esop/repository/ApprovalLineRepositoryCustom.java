package com.cauh.esop.repository;

import com.cauh.common.entity.Account;
import com.cauh.esop.domain.Approval;
import com.cauh.esop.domain.constant.ApprovalLineType;
import com.cauh.esop.domain.constant.ApprovalStatus;
import com.cauh.esop.domain.constant.ReportType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApprovalLineRepositoryCustom {
    Page<Approval> findAll(ApprovalLineType lineType, ApprovalStatus status, Account user, Pageable pageable);

    Page<Approval> findAllAdmin(ApprovalStatus status, ReportType reportType, Pageable pageable);

    long countApproval(ApprovalLineType lineType, Account user);
}
