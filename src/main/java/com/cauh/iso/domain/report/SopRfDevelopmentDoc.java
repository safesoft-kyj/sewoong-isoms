package com.cauh.iso.domain.report;

import com.cauh.common.entity.BaseEntity;
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
@Table(name = "s_sop_development_doc", uniqueConstraints = @UniqueConstraint(columnNames = {"sop_rd_request_form_id", "id"}))
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "SOP_DEV_DOC_SEQ_GENERATOR", sequenceName = "SEQ_SOP_DEV_DOC", initialValue = 1, allocationSize = 1)
@Audited(withModifiedFlag = true)
public class SopRfDevelopmentDoc extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 8975360995297962082L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOP_DEV_DOC_SEQ_GENERATOR")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sop_rd_request_form_id", referencedColumnName = "id")
    private SopRfRequestForm sopRfRequestForm;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", length = 3)
    private DocumentType documentType;

    @Column(name = "doc_id", length = 30, nullable = false)
    private String docId;

    @Column(name = "category_id", length = 10)
    private String categoryId;

    @Column(name = "sop_id", length = 10)
    private String sopId;

    @Column(name = "doc_no", length = 10, nullable = false)
    private String docNo;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "version", length = 5)
    private String version;

    public String getDocInfo() {
        return docId + "/" + title + "/" + version;
    }

    public SopRfDevelopmentDoc(SopRfRequestForm sopRfRequestForm, SopRfDevelopmentDoc sopRfDevelopmentDoc) {
        this.sopRfRequestForm = sopRfRequestForm;
        this.documentType = sopRfDevelopmentDoc.getDocumentType();
        this.docId = sopRfDevelopmentDoc.getDocId();
        this.title = sopRfDevelopmentDoc.getTitle();
        this.version = sopRfDevelopmentDoc.getVersion();
        this.categoryId = sopRfDevelopmentDoc.getCategoryId();
        this.docNo = sopRfDevelopmentDoc.getDocNo();
    }
}
