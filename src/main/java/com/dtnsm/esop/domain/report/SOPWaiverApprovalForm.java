package com.dtnsm.esop.domain.report;

import com.dtnsm.common.entity.BaseEntity;
import com.dtnsm.esop.domain.Approval;
import com.dtnsm.esop.domain.DocumentVersion;
import com.dtnsm.esop.utils.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_sop_waiver_approval_form", uniqueConstraints = @UniqueConstraint(columnNames = {"id", "approval_id"}))
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "SOP_WAIVER_SEQ_GENERATOR", sequenceName = "SEQ_SOP_WAIVER_FORM", initialValue = 1, allocationSize = 1)
public class SOPWaiverApprovalForm extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 5138960676465200069L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOP_WAIVER_SEQ_GENERATOR")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "approval_id", referencedColumnName = "id")
    private Approval approval;

    @Column(name = "project_no", columnDefinition = "nvarchar(255)")
    private String projectNo;

    @Column(name = "protocol_no", columnDefinition = "nvarchar(255)")
    private String protocolNo;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @Column(name = "date_of_waiver_request")
//    private Date dateOfWaiverRequest;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "waiver_start_date")
    private Date waiverStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "waiver_end_date")
    private Date waiverEndDate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "deviated_sop_document_id", referencedColumnName = "id")
    private DocumentVersion deviatedSOPDocument;

    @Column(name = "deviation_details", columnDefinition = "nvarchar(500)")
    private String deviationDetails;

    public SOPWaiverApprovalForm(Approval approval, SOPWaiverApprovalForm sopWaiverApprovalForm) {
        this.approval = approval;
        this.projectNo = sopWaiverApprovalForm.getProjectNo();
        this.protocolNo = sopWaiverApprovalForm.getProtocolNo();
        this.waiverStartDate = sopWaiverApprovalForm.getWaiverStartDate();
        this.waiverEndDate = sopWaiverApprovalForm.getWaiverEndDate();
        this.deviatedSOPDocument = sopWaiverApprovalForm.getDeviatedSOPDocument();
        this.deviationDetails = sopWaiverApprovalForm.getDeviationDetails();
    }

    public String getStrProtocolNo() {
        if(StringUtils.isEmpty(protocolNo)) {
            return "N/A";
        } else {
            return protocolNo;
        }
    }

    public String getStrWaiverStartDate() {
        if(ObjectUtils.isEmpty(waiverStartDate)) {
            return "";
        } else {
            return DateUtils.format(waiverStartDate, "dd-MMM-yyyy").toUpperCase();
        }
    }

    public String getStrWaiverEndDate() {
        if(ObjectUtils.isEmpty(waiverEndDate)) {
            return "";
        } else {
            return DateUtils.format(waiverEndDate, "dd-MMM-yyyy").toUpperCase();
        }
    }

    public String getStrDocIdAndTitle() {
        return deviatedSOPDocument.getDocument().getDocId() + "&" + deviatedSOPDocument.getDocument().getTitle();
    }

    public String getStrEffectiveDate() {
        return DateUtils.format(deviatedSOPDocument.getEffectiveDate(), "dd-MMM-yyyy").toUpperCase();
    }

    public String getStrVersion() {
        return deviatedSOPDocument.getVersion();
    }
}
