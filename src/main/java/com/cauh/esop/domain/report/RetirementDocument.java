package com.cauh.esop.domain.report;

import com.cauh.common.entity.BaseEntity;
import com.cauh.esop.domain.DocumentVersion;
import com.cauh.esop.domain.constant.DocumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_sop_retirement_documents", uniqueConstraints = @UniqueConstraint(columnNames = {"retirement_form_id", "document_version_id"}))
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "SOP_RETIREMENT_DOCUMENT_SEQ_GENERATOR", sequenceName = "SEQ_RETIREMENT_DOCUMENT", initialValue = 1, allocationSize = 1)
public class RetirementDocument extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -7631252944262485303L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOP_RETIREMENT_DOCUMENT_SEQ_GENERATOR")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "retirement_form_id", referencedColumnName = "id")
    private RetirementApprovalForm retirementApprovalForm;

    @ManyToOne
    @JoinColumn(name = "document_version_id", referencedColumnName = "id")
    private DocumentVersion documentVersion;

    @Column(name = "document_type")
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "retirement_date", insertable = false)
    private Date retirementDate;

    @Column(name = "retired")
    private boolean retired;

    public RetirementDocument(RetirementApprovalForm retirementApprovalForm, RetirementDocument retirementDocument) {
        this.retirementApprovalForm = retirementApprovalForm;
        this.documentVersion = retirementDocument.getDocumentVersion();
        this.documentType = retirementDocument.getDocumentType();
//        this.retirementDate = retirementDocument.getRetirementDate();
//        this.retired = retirementDocument.isRetired();
    }
}

