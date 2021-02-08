package com.cauh.iso.domain.report;

import com.cauh.common.entity.BaseEntity;
import com.cauh.iso.domain.Approval;
import com.cauh.iso.domain.DocumentVersion;
import com.cauh.iso.utils.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_sop_deviation_report", uniqueConstraints = @UniqueConstraint(columnNames = {"id", "approval_id"}))
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "DEVIATION_REPORT_SEQ_GENERATOR", sequenceName = "SEQ_DEVIATION_REPORT", initialValue = 1, allocationSize = 1)
public class SOPDeviationReport extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 5138960676465200069L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEVIATION_REPORT_SEQ_GENERATOR")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "approval_id", referencedColumnName = "id")
    private Approval approval;

//    @Column(name = "project_no", columnDefinition = "nvarchar(255)")
//    private String projectNo;
//
//    @Column(name = "protocol_no", columnDefinition = "nvarchar(255)")
//    private String protocolNo;
//
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @Column(name = "date_of_occurrence")
//    private Date dateOfOccurrence;
//
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @Column(name = "date_of_discovery")
//    private Date dateOfDiscovery;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "confirmation_date")
    private Date confirmationDate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "deviated_sop_document_id", referencedColumnName = "id")
    private DocumentVersion deviatedSOPDocument;

//    private String deviatedSOPVersion;
//    private String deviatedSOPEffectiveDate;
    @Column(name = "deviation_details", columnDefinition = "nvarchar(1000)")
    private String deviationDetails;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "corrective_completion_date")
    private Date correctiveCompletionDate;

    @Column(name = "corrective_action", columnDefinition = "nvarchar(1000)")
    private String correctiveAction;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "preventive_completion_date")
    private Date preventiveCompletionDate;

    @Column(name = "preventive_action", columnDefinition = "nvarchar(1000)")
    private String preventiveAction;

    @Column(name = "training_period_id", updatable = false)
    private Integer trainingPeriodId;

    @Column(name = "training_log_id", updatable = false)
    private Integer trainingLogId;

    //교육 기간 위반 SOP
    @Column(name = "tr_deviated_sop_document_id", length = 40)
    private String trDeviatedSOPDocumentId;

    public SOPDeviationReport(Approval approval, SOPDeviationReport sopDeviationReport) {
        this.approval = approval;
//        this.projectNo = sopDeviationReport.getProjectNo();
//        this.protocolNo = sopDeviationReport.getProtocolNo();
//        this.dateOfOccurrence = sopDeviationReport.getDateOfOccurrence();
//        this.dateOfDiscovery = sopDeviationReport.getDateOfDiscovery();
        this.confirmationDate = sopDeviationReport.getConfirmationDate();
        this.deviatedSOPDocument = sopDeviationReport.getDeviatedSOPDocument();
        this.deviationDetails = sopDeviationReport.getDeviationDetails();
        this.correctiveCompletionDate = sopDeviationReport.getCorrectiveCompletionDate();
        this.correctiveAction = sopDeviationReport.getCorrectiveAction();
        this.preventiveCompletionDate = sopDeviationReport.getPreventiveCompletionDate();
        this.preventiveAction = sopDeviationReport.getPreventiveAction();
        this.trainingPeriodId = sopDeviationReport.getTrainingPeriodId();
        this.trainingLogId = sopDeviationReport.getTrainingLogId();
    }

//    public String getStrDateOfOccurrence() {
//        return toDateString(dateOfOccurrence);
//    }
//    public String getStrDateOfDiscovery() {
//        return toDateString(dateOfDiscovery);
//    }

    public String getStrConfirmationDate() {
        return toDateString(confirmationDate);
    }
    public String getStrCorrectiveCompletionDate() {
        return toDateString(correctiveCompletionDate);
    }
    public String getStrPreventiveCompletionDate() {
        return toDateString(preventiveCompletionDate);
    }

    public String getDeviatedDocTitle() {
        //${form.deviatedSOPDocument.document.DocId} / ${form.deviatedSOPDocument.document.Title}
        return deviatedSOPDocument.getDocument().getDocId() + " / " + deviatedSOPDocument.getDocument().getTitle();
    }

    public String getDeviatedDocVersion() {
        return deviatedSOPDocument.getVersion();
    }

    public String getDeviatedDocEffectiveDate() {
        return DateUtils.format(deviatedSOPDocument.getEffectiveDate(), "dd-MMM-yyyy").toUpperCase();
    }

    protected String toDateString(Date date) {
        if(ObjectUtils.isEmpty(date)) {
            return "";
        } else {
            return DateUtils.format(date, "dd-MMM-yyyy").toUpperCase();
        }
    }
}
