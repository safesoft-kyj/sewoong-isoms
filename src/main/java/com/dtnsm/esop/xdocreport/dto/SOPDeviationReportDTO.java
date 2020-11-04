package com.dtnsm.esop.xdocreport.dto;

import com.dtnsm.esop.domain.report.SOPDeviationReport;
import lombok.Data;

import java.util.List;

@Data
public class SOPDeviationReportDTO {
    private boolean reviewer;
    private SOPDeviationReport form;
    private ApprovalLineDTO reportedBy;
    private List<ApprovalLineDTO> reviewers;
    private ApprovalLineDTO confirmedBy;
}
