package com.cauh.iso.domain.report;

import com.cauh.common.entity.BaseEntity;
import com.cauh.iso.domain.DocumentVersion;
import com.cauh.iso.domain.constant.DocumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_sop_revision_doc", uniqueConstraints = @UniqueConstraint(columnNames = {"sop_rd_request_form_id", "id"}))
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "SOP_REV_DOC_SEQ_GENERATOR", sequenceName = "SEQ_SOP_REV_DOC", initialValue = 1, allocationSize = 1)
public class SopRdRevisionDoc extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 3602687788455784829L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOP_REV_DOC_SEQ_GENERATOR")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sop_rd_request_form_id", referencedColumnName = "id")
    private SopRdRequestForm sopRdRequestForm;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", length = 3)
    private DocumentType documentType;

    @ManyToOne
    @JoinColumn(name = "document_version_id", referencedColumnName = "id")
    private DocumentVersion documentVersion;

    public String getDocInfo() {
        return documentVersion.getDocument().getDocId() + "/" + documentVersion.getDocument().getTitle() + "/" + documentVersion.getVersion();
    }

    public SopRdRevisionDoc(SopRdRequestForm sopRdRequestForm, SopRdRevisionDoc sopRdRevisionDoc) {
        this.sopRdRequestForm = sopRdRequestForm;
        this.documentType = sopRdRevisionDoc.getDocumentType();
        this.documentVersion = sopRdRevisionDoc.getDocumentVersion();
    }
}
