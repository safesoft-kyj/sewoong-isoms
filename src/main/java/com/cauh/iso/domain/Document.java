package com.cauh.iso.domain;

import com.cauh.common.entity.BaseEntity;
import com.cauh.iso.domain.constant.DocumentType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_document", uniqueConstraints = {@UniqueConstraint(columnNames = "doc_id")}, indexes = {@Index(columnList = "type,category_id")})
@Slf4j
@ToString(of = {"title"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class Document extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", length = 40)
    private String id;

    @Column(name = "type", length = 3)
    @Enumerated(EnumType.STRING)
    private DocumentType type;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @Column(name = "document_no", length = 4, nullable = false)
    private String documentNo;

    @Column(name = "doc_id", length = 30, nullable = false)
    private String docId;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "sop_id", referencedColumnName = "id")
    private Document sop;

    @OrderBy("docId asc")
    @Where(clause = "type='RF'")
    @OneToMany(mappedBy = "sop")
    private List<Document> rfList;

//    @Where(clause = "status='CURRENT'")
//    @ManyToOne(mappedBy = "document", fetch = FetchType.LAZY)
//    private DocumentVersion currentRFVersion;
//
//    @Where(clause = "status='APPROVED'")
//    @OneToOne(mappedBy = "document", fetch = FetchType.LAZY)
//    private DocumentVersion approvedRFVersion;
//
//    @Where(clause = "status='SUPERSEDED'")
//    @OneToOne(mappedBy = "document", fetch = FetchType.LAZY)
//    private DocumentVersion supersededRFVersion;

    @OneToMany(mappedBy = "document")
    private List<DocumentVersion> documentVersionList;


    @Builder
    public Document(String id, Document sop, DocumentType type, Category Category, String documentNo, String docId, String title) {
        this.id = id;
        this.sop = sop;
        this.type = type;
        this.category = Category;
        this.documentNo = documentNo;
        this.docId = docId;
        this.title = title;
    }

}
