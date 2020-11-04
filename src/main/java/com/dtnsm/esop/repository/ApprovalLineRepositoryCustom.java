package com.dtnsm.esop.repository;

import com.dtnsm.common.entity.Account;
import com.dtnsm.esop.domain.Approval;
import com.dtnsm.esop.domain.constant.ApprovalLineType;
import com.dtnsm.esop.domain.constant.ApprovalStatus;
import com.dtnsm.esop.domain.constant.ReportType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApprovalLineRepositoryCustom {
    Page<Approval> findAll(ApprovalLineType lineType, ApprovalStatus status, Account user, Pageable pageable);

    Page<Approval> findAllAdmin(ApprovalStatus status, ReportType reportType, Pageable pageable);

    long countApproval(ApprovalLineType lineType, Account user);
}
