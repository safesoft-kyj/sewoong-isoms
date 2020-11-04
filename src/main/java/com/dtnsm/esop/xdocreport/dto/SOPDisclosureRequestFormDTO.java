package com.dtnsm.esop.xdocreport.dto;

import com.dtnsm.esop.domain.report.SOPDisclosureRequestForm;
import lombok.Data;

import java.util.List;

@Data
public class SOPDisclosureRequestFormDTO {
    private boolean reviewer;
    private SOPDisclosureRequestForm form;
    private ApprovalLineDTO reportedBy;
    private List<ApprovalLineDTO> reviewers;
    private ApprovalLineDTO confirmedBy;
}
