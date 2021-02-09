package com.cauh.iso.xdocreport.dto;


import com.cauh.iso.domain.*;
import com.cauh.iso.domain.constant.ApprovalLineType;
import com.cauh.iso.domain.constant.TrainingStatus;
import com.cauh.iso.domain.report.SOPDeviationReport;
import com.cauh.iso.utils.DateUtils;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class TrainingDeviationLogDTO {

    private String confirmationDate;
    private String sopDocId;
    private String sopTitle;
    private String version;
    private String effectiveDate;
    private String dueDate;


    private String reporterName;
    private String reportedDate;

    private String approverName;
    private String approvedDate;

    private String deviationDetails;
    private String correctiveAction;
    private String correctiveDate;
    private String preventiveAction;
    private String preventiveDate;

    private String status;
    private String completionDate;


    @QueryProjection
    public TrainingDeviationLogDTO(Approval approval, Date dueDate, TrainingStatus trainingStatus, Date completionDate){
        SOPDeviationReport deviationReport = approval.getSopDeviationReport();

        this.confirmationDate = DateUtils.format(deviationReport.getConfirmationDate(), "dd-MMM-yyyy").toUpperCase();
        this.sopDocId = deviationReport.getDeviatedSOPDocument().getDocument().getDocId();
        this.sopTitle = deviationReport.getDeviatedSOPDocument().getDocument().getTitle();
        this.version = deviationReport.getDeviatedDocVersion();
        this.effectiveDate = deviationReport.getDeviatedDocEffectiveDate();
        this.dueDate = DateUtils.format(dueDate, "dd-MMM-yyyy").toUpperCase();

        approval.getApprovalLines().stream().forEach(a -> {
                if(a.getLineType() == ApprovalLineType.requester) {
                    this.reporterName = a.getDisplayName();
                    this.reportedDate = a.getStrDate();
                } else if (a.getLineType() == ApprovalLineType.approver) {
                    this.approverName = a.getDisplayName();
                    this.approvedDate = a.getStrDate();
                }
        });

        this.deviationDetails = deviationReport.getDeviationDetails();
        this.correctiveAction = deviationReport.getCorrectiveAction();
        this.correctiveDate = DateUtils.format(deviationReport.getCorrectiveCompletionDate(), "dd-MMM-yyyy").toUpperCase();
        this.preventiveAction = deviationReport.getPreventiveAction();
        this.preventiveDate = DateUtils.format(deviationReport.getPreventiveCompletionDate(), "dd-MMM-yyyy").toUpperCase();

        this.status = trainingStatus == TrainingStatus.COMPLETED?"Training Completed":"Not Completed";
        this.completionDate = DateUtils.format(completionDate, "dd-MMM-yyyy").toUpperCase();
    }

}
