package com.dtnsm.esop.domain.report;

import com.dtnsm.common.entity.BaseEntity;
import com.dtnsm.esop.domain.Approval;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Where;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_sop_rd_request_form", uniqueConstraints = @UniqueConstraint(columnNames = {"id", "approval_id"}))
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "SOP_RD_REQ_FORM_SEQ_GENERATOR", sequenceName = "SEQ_SOP_RD_REQ_FORM", initialValue = 1, allocationSize = 1)
public class SopRdRequestForm extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -6353816772049120756L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOP_RD_REQ_FORM_SEQ_GENERATOR")
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

    @Column(name = "newRDDevelopment")
    private boolean newRDDevelopment;

    @Column(name = "sop_revision")
    private boolean sopRevision;

    @Transient
    private String[] sopRevisionIds;

    @Column(name = "rd_revision")
    private boolean rdRevision;

    @Transient
    private String[] rdRevisionIds;

    @Column(name = "reason_for_the_request", columnDefinition = "nvarchar(200)")
    private String reasonForTheRequest;

    @Column(name = "requested_by_comments", columnDefinition = "nvarchar(200)")
    private String requestedByComments;

    @OneToMany(mappedBy = "sopRdRequestForm", fetch = FetchType.EAGER)
    @Where(clause = "document_type='SOP'")
    private List<SopRdRevisionDoc> sopRevisionDocs = new ArrayList<>();

    @OneToMany(mappedBy = "sopRdRequestForm", fetch = FetchType.EAGER)
    @Where(clause = "document_type='RD'")
    private List<SopRdRevisionDoc> rdRevisionDocs = new ArrayList<>();

    @OneToMany(mappedBy = "sopRdRequestForm")
    @Where(clause = "document_type='SOP'")
    private List<SopRdDevelopmentDoc> sopDevelopmentDocs = new ArrayList<>();

    @OneToMany(mappedBy = "sopRdRequestForm")
    @Where(clause = "document_type='RD'")
    private List<SopRdDevelopmentDoc> rdDevelopmentDocs = new ArrayList<>();

    public SopRdRequestForm(Approval approval, SopRdRequestForm sopRdRequestForm) {
        this.approval = approval;
        this.nameOfRequester = sopRdRequestForm.getNameOfRequester();
        this.nameOfTeamDept = sopRdRequestForm.getNameOfTeamDept();
        this.newSOPDevelopment = sopRdRequestForm.isNewSOPDevelopment();
        this.newRDDevelopment = sopRdRequestForm.isNewRDDevelopment();
        this.sopRevision = sopRdRequestForm.isSopRevision();
        this.rdRevision = sopRdRequestForm.isRdRevision();
        this.reasonForTheRequest = sopRdRequestForm.getReasonForTheRequest();
        this.requestedByComments = sopRdRequestForm.getRequestedByComments();
        if(!ObjectUtils.isEmpty(sopRdRequestForm.getSopRevisionDocs())) {
            for(SopRdRevisionDoc sopRevisionDoc : sopRdRequestForm.getSopRevisionDocs()) {
                this.sopRevisionDocs.add(new SopRdRevisionDoc(this, sopRevisionDoc));
            }
        }
        if(!ObjectUtils.isEmpty(sopRdRequestForm.getRdRevisionDocs())) {
            for(SopRdRevisionDoc rdRevisionDoc : sopRdRequestForm.getRdRevisionDocs()) {
                this.rdRevisionDocs.add(new SopRdRevisionDoc(this, rdRevisionDoc));
            }
        }
        if(!ObjectUtils.isEmpty(sopRdRequestForm.getSopDevelopmentDocs())) {
            for(SopRdDevelopmentDoc sopRdDevelopmentDoc : sopRdRequestForm.getSopDevelopmentDocs()) {
                this.sopDevelopmentDocs.add(new SopRdDevelopmentDoc(this, sopRdDevelopmentDoc));
            }
        }
        if(!ObjectUtils.isEmpty(sopRdRequestForm.getRdDevelopmentDocs())) {
            for(SopRdDevelopmentDoc rdDevelopmentDoc : sopRdRequestForm.getRdDevelopmentDocs()) {
                this.rdDevelopmentDocs.add(new SopRdDevelopmentDoc(this, rdDevelopmentDoc));
            }
        }
    }
}
