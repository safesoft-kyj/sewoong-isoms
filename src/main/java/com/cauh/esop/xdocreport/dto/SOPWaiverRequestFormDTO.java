package com.cauh.esop.xdocreport.dto;

import com.cauh.esop.domain.report.SOPWaiverApprovalForm;
import lombok.Data;

import java.util.List;

@Data
public class SOPWaiverRequestFormDTO {
    private boolean reviewer;
    private SOPWaiverApprovalForm form;
    private ApprovalLineDTO reportedBy;
    private List<ApprovalLineDTO> reviewers;
    private ApprovalLineDTO confirmedBy;
}
