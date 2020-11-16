package com.cauh.esop.domain;

import com.cauh.common.entity.BaseEntity;
import com.cauh.esop.domain.constant.ApprovalStatus;
import com.cauh.esop.domain.constant.ReportType;
import com.cauh.esop.domain.report.*;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_approval", indexes = @Index(columnList = "status,deleted"))
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "APPROVAL_SEQ_GENERATOR", sequenceName = "SEQ_APPROVAL", initialValue = 1, allocationSize = 1)
public class Approval extends BaseEntity implements Serializable, Cloneable {
    private static final long serialVersionUID = 7912477670521301292L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPROVAL_SEQ_GENERATOR")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 30, nullable = false, updatable = false)
    private ReportType type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

//    @Column(name = "content", columnDefinition = "nvarchar(MAX)")
//    private String content;

    @OneToMany(mappedBy = "approval")
    private List<ApprovalLine> approvalLines = new ArrayList<>();

    @OneToOne(mappedBy = "approval")
    private SOPDeviationReport sopDeviationReport;

    @OneToOne(mappedBy = "approval")
    private SopRdRequestForm sopRdRequestForm;

//    @OneToOne
//    private RDApprovalForm rdApprovalForm;

    @OneToOne(mappedBy = "approval")
    private SOPWaiverApprovalForm sopWaiverApprovalForm;

    @OneToOne(mappedBy = "approval")
    private SOPDisclosureRequestForm sopDisclosureRequestForm;

    @OneToOne(mappedBy = "approval")
    private RetirementApprovalForm retirementApprovalForm;

    @Column(name = "username", length = 64, nullable = false, updatable = false)
    private String username;

    @Column(name = "deleted")
    private boolean deleted;

    /**
     *  재상신/수정기간
     */
    @Transient
    private boolean renew;

    @Transient
    private String keyword;

    @Builder
    public Approval(Integer id, ReportType type, ApprovalStatus status, List<ApprovalLine> approvalLines) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.approvalLines = approvalLines;
    }

    @QueryProjection
    public Approval(Integer id, ReportType type, ApprovalStatus status, String createdBy, Timestamp createdDate, String lastModifiedBy, Timestamp lastModifiedDate) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
    }
    @QueryProjection
    public Approval(Integer id, ReportType type, ApprovalStatus status, String createdBy, Timestamp createdDate, String lastModifiedBy, Timestamp lastModifiedDate, String keyword) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.keyword = keyword;
    }

    public Approval(Approval approval) {
        this.id = null;
        this.renew = true;
        this.deleted = false;
        this.type = approval.getType();
        if(!ObjectUtils.isEmpty(approval.getApprovalLines())) {
            for(ApprovalLine line : approval.getApprovalLines()) {
                this.approvalLines.add(new ApprovalLine(this, line));
            }
        }
        if(approval.getType() == ReportType.SOP_RD_Request_Form) {
            this.sopRdRequestForm = new SopRdRequestForm(this, approval.getSopRdRequestForm());
        } else if(approval.getType() == ReportType.SOP_RD_Retirement_Form) {
            this.retirementApprovalForm = new RetirementApprovalForm(this, approval.getRetirementApprovalForm());
        } else if(approval.getType() == ReportType.SOP_Deviation_Report) {
            this.sopDeviationReport = new SOPDeviationReport(this, approval.getSopDeviationReport());
        } else if(approval.getType() == ReportType.SOP_Disclosure_Request_Form) {
            this.sopDisclosureRequestForm = new SOPDisclosureRequestForm(this, approval.getSopDisclosureRequestForm());
        } else if(approval.getType() == ReportType.SOP_Waiver_Approval_Form) {
            this.sopWaiverApprovalForm = new SOPWaiverApprovalForm(this, approval.getSopWaiverApprovalForm());
        }
    }
}
