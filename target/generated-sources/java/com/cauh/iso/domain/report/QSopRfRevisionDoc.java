package com.cauh.iso.domain.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSopRfRevisionDoc is a Querydsl query type for SopRfRevisionDoc
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSopRfRevisionDoc extends EntityPathBase<SopRfRevisionDoc> {

    private static final long serialVersionUID = 1981870206L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSopRfRevisionDoc sopRfRevisionDoc = new QSopRfRevisionDoc("sopRfRevisionDoc");

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

    public final QSopRfRequestForm sopRfRequestForm;

    public QSopRfRevisionDoc(String variable) {
        this(SopRfRevisionDoc.class, forVariable(variable), INITS);
    }

    public QSopRfRevisionDoc(Path<? extends SopRfRevisionDoc> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSopRfRevisionDoc(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSopRfRevisionDoc(PathMetadata metadata, PathInits inits) {
        this(SopRfRevisionDoc.class, metadata, inits);
    }

    public QSopRfRevisionDoc(Class<? extends SopRfRevisionDoc> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.documentVersion = inits.isInitialized("documentVersion") ? new com.cauh.iso.domain.QDocumentVersion(forProperty("documentVersion"), inits.get("documentVersion")) : null;
        this.sopRfRequestForm = inits.isInitialized("sopRfRequestForm") ? new QSopRfRequestForm(forProperty("sopRfRequestForm"), inits.get("sopRfRequestForm")) : null;
    }

}

