package com.dtnsm.esop.domain.report;

import com.dtnsm.common.entity.BaseEntity;
import com.dtnsm.esop.domain.Approval;
import com.dtnsm.esop.domain.DocumentVersion;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_sop_retirement_form", uniqueConstraints = @UniqueConstraint(columnNames = {"id", "approval_id"}))
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "SOP_RETIREMENT_FORM_SEQ_GENERATOR", sequenceName = "SEQ_RETIREMENT_FORM", initialValue = 1, allocationSize = 1)
public class RetirementApprovalForm extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 694379143653985708L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOP_RETIREMENT_FORM_SEQ_GENERATOR")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "approval_id", referencedColumnName = "id")
    private Approval approval;

    @OneToMany(mappedBy = "retirementApprovalForm")
    @Where(clause = "document_type='SOP'")
    private List<RetirementDocument> retirementDocumentSOPs = new ArrayList<>();

    @OneToMany(mappedBy = "retirementApprovalForm")
    @Where(clause = "document_type='RD'")
    private List<RetirementDocument> retirementDocumentRDs = new ArrayList<>();

    @Column(name = "reason", columnDefinition = "nvarchar(500)")
    private String reason;

    public RetirementApprovalForm(Approval approval, RetirementApprovalForm retirementApprovalForm) {
        this.approval = approval;
        this.reason = retirementApprovalForm.getReason();
        if(!ObjectUtils.isEmpty(retirementApprovalForm.getRetirementDocumentSOPs())) {
            for(RetirementDocument sop : retirementApprovalForm.getRetirementDocumentSOPs()) {
                this.retirementDocumentSOPs.add(new RetirementDocument(this, sop));
            }
        }
        if(!ObjectUtils.isEmpty(retirementApprovalForm.getRetirementDocumentRDs())) {
            for(RetirementDocument rd : retirementApprovalForm.getRetirementDocumentRDs()) {
                this.retirementDocumentRDs.add(new RetirementDocument(this, rd));
            }
        }
    }

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @Column(name = "retirementDate")
//    private Date retirementDate;

    @Transient
    private String[] sopIds;

    @Transient
    private String[] rdIds;

    @Transient
    private List<DocumentVersion> retirementDocuments = new ArrayList<>();

    @Transient
    private List<String> sopRdIds = new ArrayList<>();
}
