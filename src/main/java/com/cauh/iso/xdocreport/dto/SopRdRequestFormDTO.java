package com.cauh.iso.xdocreport.dto;

import com.cauh.iso.domain.report.SopRfRequestForm;
import lombok.Data;

import java.util.List;

@Data
public class SopRdRequestFormDTO {
    private boolean reviewer;
    private SopRfRequestForm form;
    private ApprovalLineDTO reportedBy;
    private List<ApprovalLineDTO> reviewers;
    private ApprovalLineDTO confirmedBy;
}
