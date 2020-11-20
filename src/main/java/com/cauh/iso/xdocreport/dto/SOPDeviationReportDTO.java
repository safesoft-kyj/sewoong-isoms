package com.cauh.iso.xdocreport.dto;

import com.cauh.iso.domain.report.SOPDeviationReport;
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
