package com.cauh.iso.domain.report;

import com.cauh.common.entity.BaseEntity;
import com.cauh.iso.domain.Approval;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_sop_rf_request_form", uniqueConstraints = @UniqueConstraint(columnNames = {"id", "approval_id"}))
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "SOP_RF_REQ_FORM_SEQ_GENERATOR", sequenceName = "SEQ_SOP_RF_REQ_FORM", initialValue = 1, allocationSize = 1)
@Audited(withModifiedFlag = true)
public class SopRfRequestForm extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -6353816772049120756L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOP_RF_REQ_FORM_SEQ_GENERATOR")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "approval_id", referencedColumnName = "id")
    private Approval approval;

    @Column(name = "name_of_requester", columnDefinition = "nvarchar(50)")
    private String nameOfRequester;
    @Column(name = "name_of_team_dept", columnDefinition = "nvarchar(50)")
    private String nameOfTeamDept;

    @Column(name = "new_sop_development")
    private boolean newSOPDevelopment;

    @Column(name = "newRFDevelopment")
    private boolean newRFDevelopment;

    @Column(name = "sop_revision")
    private boolean sopRevision;

    @Transient
    private String[] sopRevisionIds;

    @Column(name = "rf_revision")
    private boolean rfRevision;

    @Transient
    private String[] rfRevisionIds;

    @Column(name = "reason_for_the_request", columnDefinition = "nvarchar(200)")
    private String reasonForTheRequest;

    @Column(name = "requested_by_comments", columnDefinition = "nvarchar(200)")
    private String requestedByComments;

    @OneToMany(mappedBy = "sopRfRequestForm")
    @Where(clause = "document_type='SOP'")
    private List<SopRfRevisionDoc> sopRevisionDocs = new ArrayList<>();

    @OneToMany(mappedBy = "sopRfRequestForm")
    @Where(clause = "document_type='RF'")
    private List<SopRfRevisionDoc> rfRevisionDocs = new ArrayList<>();

    @OneToMany(mappedBy = "sopRfRequestForm")
    @Where(clause = "document_type='SOP'")
    private List<SopRfDevelopmentDoc> sopDevelopmentDocs = new ArrayList<>();

    @OneToMany(mappedBy = "sopRfRequestForm")
    @Where(clause = "document_type='RF'")
    private List<SopRfDevelopmentDoc> rfDevelopmentDocs = new ArrayList<>();

    public SopRfRequestForm(Approval approval, SopRfRequestForm sopRfRequestForm) {
        this.approval = approval;
        this.nameOfRequester = sopRfRequestForm.getNameOfRequester();
        this.nameOfTeamDept = sopRfRequestForm.getNameOfTeamDept();
        this.newSOPDevelopment = sopRfRequestForm.isNewSOPDevelopment();
        this.newRFDevelopment = sopRfRequestForm.isNewRFDevelopment();
        this.sopRevision = sopRfRequestForm.isSopRevision();
        this.rfRevision = sopRfRequestForm.isRfRevision();
        this.reasonForTheRequest = sopRfRequestForm.getReasonForTheRequest();
        this.requestedByComments = sopRfRequestForm.getRequestedByComments();
        if(!ObjectUtils.isEmpty(sopRfRequestForm.getSopRevisionDocs())) {
            for(SopRfRevisionDoc sopRevisionDoc : sopRfRequestForm.getSopRevisionDocs()) {
                this.sopRevisionDocs.add(new SopRfRevisionDoc(this, sopRevisionDoc));
            }
        }
        if(!ObjectUtils.isEmpty(sopRfRequestForm.getRfRevisionDocs())) {
            for(SopRfRevisionDoc rdRevisionDoc : sopRfRequestForm.getRfRevisionDocs()) {
                this.rfRevisionDocs.add(new SopRfRevisionDoc(this, rdRevisionDoc));
            }
        }
        if(!ObjectUtils.isEmpty(sopRfRequestForm.getSopDevelopmentDocs())) {
            for(SopRfDevelopmentDoc sopRfDevelopmentDoc : sopRfRequestForm.getSopDevelopmentDocs()) {
                this.sopDevelopmentDocs.add(new SopRfDevelopmentDoc(this, sopRfDevelopmentDoc));
            }
        }
        if(!ObjectUtils.isEmpty(sopRfRequestForm.getRfDevelopmentDocs())) {
            for(SopRfDevelopmentDoc rdDevelopmentDoc : sopRfRequestForm.getRfDevelopmentDocs()) {
                this.rfDevelopmentDocs.add(new SopRfDevelopmentDoc(this, rdDevelopmentDoc));
            }
        }
    }
}
