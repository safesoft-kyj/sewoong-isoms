package com.cauh.iso.xdocreport.dto;

import com.cauh.iso.domain.report.RetirementApprovalForm;
import lombok.Data;

import java.util.List;

@Data
public class SopRdRetirementFormDTO {
    private boolean reviewerQA;
    private boolean reviewerTeamManager;
    private RetirementApprovalForm form;
    private List<RetirementDocumentDTO> docs;
    private ApprovalLineDTO reportedBy;
    private ApprovalLineDTO reviewedByTeamManager;
    private ApprovalLineDTO reviewedByQA;
    private ApprovalLineDTO confirmedBy;
}
