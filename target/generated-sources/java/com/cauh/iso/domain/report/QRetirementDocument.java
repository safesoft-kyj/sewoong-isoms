package com.cauh.iso.domain.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRetirementDocument is a Querydsl query type for RetirementDocument
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRetirementDocument extends EntityPathBase<RetirementDocument> {

    private static final long serialVersionUID = 167837469L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRetirementDocument retirementDocument = new QRetirementDocument("retirementDocument");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final EnumPath<com.cauh.iso.domain.constant.DocumentType> documentType = createEnum("documentType", com.cauh.iso.domain.constant.DocumentType.class);

    public final com.cauh.iso.domain.QDocumentVersion documentVersion;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final BooleanPath retired = createBoolean("retired");

    public final QRetirementApprovalForm retirementApprovalForm;

    public final DateTimePath<java.util.Date> retirementDate = createDateTime("retirementDate", java.util.Date.class);

    public QRetirementDocument(String variable) {
        this(RetirementDocument.class, forVariable(variable), INITS);
    }

    public QRetirementDocument(Path<? extends RetirementDocument> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRetirementDocument(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRetirementDocument(PathMetadata metadata, PathInits inits) {
        this(RetirementDocument.class, metadata, inits);
    }

    public QRetirementDocument(Class<? extends RetirementDocument> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.documentVersion = inits.isInitialized("documentVersion") ? new com.cauh.iso.domain.QDocumentVersion(forProperty("documentVersion"), inits.get("documentVersion")) : null;
        this.retirementApprovalForm = inits.isInitialized("retirementApprovalForm") ? new QRetirementApprovalForm(forProperty("retirementApprovalForm"), inits.get("retirementApprovalForm")) : null;
    }

}

