package com.cauh.iso.domain.report;

import com.cauh.common.entity.BaseEntity;
import com.cauh.iso.domain.DocumentVersion;
import com.cauh.iso.domain.constant.DocumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_sop_requested_documents")//uniqueConstraints = @UniqueConstraint(columnNames = {"request_form_id", "document_version_id"})
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "SOP_DISCLOSURE_REQ_DOC_SEQ_GENERATOR", sequenceName = "SEQ_DISCLOSURE_REQ_DOC", initialValue = 1, allocationSize = 1)
public class RequestedDocument extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 593521865801919744L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOP_DISCLOSURE_REQ_DOC_SEQ_GENERATOR")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "request_form_id", referencedColumnName = "id")
    private SOPDisclosureRequestForm sopDisclosureRequestForm;

    @ManyToOne
    @JoinColumn(name = "document_version_id", referencedColumnName = "id")
    private DocumentVersion documentVersion;

    @Column(name = "document_type")
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    public String getDocInfo() {
        return documentVersion.getDocument().getDocId() + " " + documentVersion.getDocument().getTitle() + " v" + documentVersion.getVersion();
    }

    public RequestedDocument(SOPDisclosureRequestForm sopDisclosureRequestForm, RequestedDocument requestedDocument) {
        this.sopDisclosureRequestForm = sopDisclosureRequestForm;
        this.documentVersion = requestedDocument.getDocumentVersion();
        this.documentType = requestedDocument.getDocumentType();
    }
}
