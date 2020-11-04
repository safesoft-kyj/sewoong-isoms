package com.dtnsm.esop.xdocreport.dto;

import com.dtnsm.esop.domain.report.SOPWaiverApprovalForm;
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
