package com.cauh.iso.xdocreport.dto;

import com.cauh.iso.domain.report.SOPWaiverApprovalForm;
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
