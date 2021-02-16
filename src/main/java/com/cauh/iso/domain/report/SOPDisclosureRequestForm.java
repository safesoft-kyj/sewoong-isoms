package com.cauh.iso.domain.report;

import com.cauh.common.entity.BaseEntity;
import com.cauh.common.utils.DateUtils;
import com.cauh.iso.domain.Approval;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
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
@Table(name = "s_sop_disclosure_form",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id", "approval_id"}),
        indexes = @Index(columnList = "request_start_date,request_end_date")
    )
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "SOP_DISCLOSURE_FORM_SEQ_GENERATOR", sequenceName = "SEQ_DISCLOSURE_FORM", initialValue = 1, allocationSize = 1)
@Audited(withModifiedFlag = true)
public class SOPDisclosureRequestForm extends BaseEntity implements Serializable {

    @Builder
    public SOPDisclosureRequestForm(Integer id) {
        this.id = id;
    }

    private static final long serialVersionUID = 593521865801919744L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOP_DISCLOSURE_FORM_SEQ_GENERATOR")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "approval_id", referencedColumnName = "id")
    private Approval approval;

    @OneToMany(mappedBy = "sopDisclosureRequestForm")
    private List<ExternalCustomer> externalCustomers = new ArrayList<>();

    @Column(name = "name_of_requester", columnDefinition = "nvarchar(50)")
    private String nameOfRequester;

    @Column(name = "team_dept", columnDefinition = "nvarchar(100)")
    private String teamDept;

    @Column(name = "protocol_title_or_no", length = 100)
    private String protocolTitleOrNo;

    @Column(name = "project_or_team_manager", columnDefinition = "nvarchar(50)")
    private String projectOrTeamManager;

    @Column(name = "company_name_or_institute_name", columnDefinition = "nvarchar(50)")
    private String companyNameOrInstituteName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "request_start_date")
    private Date requestStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "request_end_date")
    private Date requestEndDate;

    @Column(name = "document_access")
    @Enumerated(EnumType.STRING)
    private DocumentAccess documentAccess;

    @Column(name = "document_access_other")
    private String documentAccessOther;

    @Column(name = "purpose_of_disclosure")
    @Enumerated(EnumType.STRING)
    private PurposeOfDisclosure purposeOfDisclosure;

    @Column(name = "purpose_of_disclosure_other")
    private String purposeOfDisclosureOther;

    @OneToMany(mappedBy = "sopDisclosureRequestForm")
    @Where(clause = "document_type='SOP'")
    private List<RequestedDocument> requestedDocumentSOPs = new ArrayList<>();

    @OneToMany(mappedBy = "sopDisclosureRequestForm")
    @Where(clause = "document_type='RF'")
    private List<RequestedDocument> requestedDocumentRFs = new ArrayList<>();

    @OneToMany(mappedBy = "sopDisclosureRequestForm")
    @NotAudited
    private List<DisclosureSOPTrainingLog> disclosureSOPTrainingLog = new ArrayList<>();

    @OneToMany(mappedBy = "sopDisclosureRequestForm")
    @NotAudited
    private List<DisclosureISOTrainingLog> disclosureISOTrainingLog = new ArrayList<>();

    public boolean isRequestedSOP() {
        return !ObjectUtils.isEmpty(requestedDocumentSOPs);
    }

    public boolean isRequestedRF() {
        return !ObjectUtils.isEmpty(requestedDocumentRFs);
    }

    public boolean isSOPTrainingLog() {
        return !ObjectUtils.isEmpty(disclosureSOPTrainingLog);
    }

    public boolean isISOTrainingLog() {
        return !ObjectUtils.isEmpty(disclosureISOTrainingLog);
    }

    public String getStrRequestDate() {
        return DateUtils.format(requestStartDate, "dd-MMM-yyyy").toUpperCase() + " to " + DateUtils.format(requestEndDate, "dd-MMM-yyyy").toUpperCase();
    }

    public SOPDisclosureRequestForm(Approval approval, SOPDisclosureRequestForm sopDisclosureRequestForm) {
        this.approval = approval;
        if(!ObjectUtils.isEmpty(sopDisclosureRequestForm.getExternalCustomers())) {
            for(ExternalCustomer externalCustomer : sopDisclosureRequestForm.getExternalCustomers()) {
                externalCustomers.add(new ExternalCustomer(this, externalCustomer));
            }
        }

        if(!ObjectUtils.isEmpty(sopDisclosureRequestForm.getRequestedDocumentSOPs())) {
            for(RequestedDocument sop : sopDisclosureRequestForm.getRequestedDocumentSOPs()) {
                this.requestedDocumentSOPs.add(new RequestedDocument(this, sop));
            }
        }
        if(!ObjectUtils.isEmpty(sopDisclosureRequestForm.getRequestedDocumentRFs())) {
            for(RequestedDocument rf : sopDisclosureRequestForm.getRequestedDocumentRFs()) {
                this.requestedDocumentRFs.add(new RequestedDocument(this, rf));
            }
        }

        if(!ObjectUtils.isEmpty(sopDisclosureRequestForm.getDisclosureSOPTrainingLog())) {
            for(DisclosureSOPTrainingLog sop : sopDisclosureRequestForm.getDisclosureSOPTrainingLog()) {
                this.disclosureSOPTrainingLog.add(new DisclosureSOPTrainingLog(this, sop));
            }
        }

        if(!ObjectUtils.isEmpty(sopDisclosureRequestForm.getDisclosureISOTrainingLog())) {
            for(DisclosureISOTrainingLog iso : sopDisclosureRequestForm.getDisclosureISOTrainingLog()) {
                this.disclosureISOTrainingLog.add(new DisclosureISOTrainingLog(this, iso));
            }
        }

        this.purposeOfDisclosure = sopDisclosureRequestForm.getPurposeOfDisclosure();
        this.documentAccess = sopDisclosureRequestForm.getDocumentAccess();
        this.nameOfRequester = sopDisclosureRequestForm.getNameOfRequester();
        this.teamDept = sopDisclosureRequestForm.getTeamDept();
        this.protocolTitleOrNo = sopDisclosureRequestForm.getProtocolTitleOrNo();
        this.projectOrTeamManager = sopDisclosureRequestForm.getProjectOrTeamManager();
        this.companyNameOrInstituteName = sopDisclosureRequestForm.getCompanyNameOrInstituteName();
        this.requestStartDate = sopDisclosureRequestForm.getRequestStartDate();
        this.requestEndDate = sopDisclosureRequestForm.getRequestEndDate();
        this.documentAccessOther = sopDisclosureRequestForm.getDocumentAccessOther();
        this.purposeOfDisclosureOther = sopDisclosureRequestForm.getPurposeOfDisclosureOther();

    }


    @Transient
    private String[] sopIds;

    @Transient
    private String[] rfIds;

    @Transient
    private String[] sopUserIds;

    @Transient
    private String[] isoUserIds;
}
