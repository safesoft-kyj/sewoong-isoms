package com.cauh.iso.repository;

import com.cauh.common.entity.Account;
import com.cauh.iso.domain.Approval;
import com.cauh.iso.domain.constant.ApprovalLineType;
import com.cauh.iso.domain.constant.ApprovalStatus;
import com.cauh.iso.domain.constant.ReportType;
import com.cauh.iso.xdocreport.dto.TrainingDeviationLogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ApprovalLineRepositoryCustom {

    List<TrainingDeviationLogDTO> findAllByReportType(ReportType reportType);

    List<TrainingDeviationLogDTO> findAllByReportTypeAndStatus(ReportType reportType, ApprovalStatus status);

    Page<Approval> findAll(ApprovalLineType lineType, ApprovalStatus status, Account user, Pageable pageable);

    Page<Approval> findAllAdmin(ApprovalStatus status, ReportType reportType, Pageable pageable);

    long countApproval(ApprovalLineType lineType, Account user);
}
