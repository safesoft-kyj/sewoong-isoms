package com.dtnsm.esop.xdocreport.dto;

import com.dtnsm.esop.domain.report.SopRdRequestForm;
import lombok.Data;

import java.util.List;

@Data
public class SopRdRequestFormDTO {
    private boolean reviewer;
    private SopRdRequestForm form;
    private ApprovalLineDTO reportedBy;
    private List<ApprovalLineDTO> reviewers;
    private ApprovalLineDTO confirmedBy;
}
