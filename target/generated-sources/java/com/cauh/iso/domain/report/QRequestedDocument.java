package com.cauh.iso.domain.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRequestedDocument is a Querydsl query type for RequestedDocument
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRequestedDocument extends EntityPathBase<RequestedDocument> {

    private static final long serialVersionUID = 711641760L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRequestedDocument requestedDocument = new QRequestedDocument("requestedDocument");

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

    public final QSOPDisclosureRequestForm sopDisclosureRequestForm;

    public QRequestedDocument(String variable) {
        this(RequestedDocument.class, forVariable(variable), INITS);
    }

    public QRequestedDocument(Path<? extends RequestedDocument> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRequestedDocument(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRequestedDocument(PathMetadata metadata, PathInits inits) {
        this(RequestedDocument.class, metadata, inits);
    }

    public QRequestedDocument(Class<? extends RequestedDocument> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.documentVersion = inits.isInitialized("documentVersion") ? new com.cauh.iso.domain.QDocumentVersion(forProperty("documentVersion"), inits.get("documentVersion")) : null;
        this.sopDisclosureRequestForm = inits.isInitialized("sopDisclosureRequestForm") ? new QSOPDisclosureRequestForm(forProperty("sopDisclosureRequestForm"), inits.get("sopDisclosureRequestForm")) : null;
    }

}

