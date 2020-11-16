package com.cauh.esop.domain;

import com.cauh.common.entity.BaseEntity;
import com.cauh.esop.domain.constant.DocumentAccessType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_document_access_log")
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class DocumentAccessLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4211490968271251391L;
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", length = 40)
    private String id;

    @ManyToOne
    @JoinColumn(name = "document_version_id", referencedColumnName = "id")
    private DocumentVersion documentVersion;

    @Column(name = "access_type")
    @Enumerated(EnumType.STRING)
    private DocumentAccessType accessType;

    @Builder
    public DocumentAccessLog(DocumentVersion documentVersion, DocumentAccessType accessType) {
        this.documentVersion = documentVersion;
        this.accessType = accessType;
    }
}
