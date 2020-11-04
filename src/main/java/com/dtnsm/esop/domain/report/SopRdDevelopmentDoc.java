package com.dtnsm.esop.domain.report;

import com.dtnsm.common.entity.BaseEntity;
import com.dtnsm.esop.domain.constant.DocumentType;
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
@Table(name = "s_sop_development_doc", uniqueConstraints = @UniqueConstraint(columnNames = {"sop_rd_request_form_id", "id"}))
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "SOP_DEV_DOC_SEQ_GENERATOR", sequenceName = "SEQ_SOP_DEV_DOC", initialValue = 1, allocationSize = 1)
public class SopRdDevelopmentDoc extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 8975360995297962082L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOP_DEV_DOC_SEQ_GENERATOR")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sop_rd_request_form_id", referencedColumnName = "id")
    private SopRdRequestForm sopRdRequestForm;

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

    public SopRdDevelopmentDoc(SopRdRequestForm sopRdRequestForm, SopRdDevelopmentDoc sopRdDevelopmentDoc) {
        this.sopRdRequestForm = sopRdRequestForm;
        this.documentType = sopRdDevelopmentDoc.getDocumentType();
        this.docId = sopRdDevelopmentDoc.getDocId();
        this.title = sopRdDevelopmentDoc.getTitle();
        this.version = sopRdDevelopmentDoc.getVersion();
        this.categoryId = sopRdDevelopmentDoc.getCategoryId();
        this.docNo = sopRdDevelopmentDoc.getDocNo();
    }
}
