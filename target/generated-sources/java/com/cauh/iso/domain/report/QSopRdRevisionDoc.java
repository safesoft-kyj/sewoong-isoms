package com.cauh.iso.domain.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSopRdRevisionDoc is a Querydsl query type for SopRdRevisionDoc
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSopRdRevisionDoc extends EntityPathBase<SopRdRevisionDoc> {

    private static final long serialVersionUID = 1723704768L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSopRdRevisionDoc sopRdRevisionDoc = new QSopRdRevisionDoc("sopRdRevisionDoc");

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

    public final QSopRdRequestForm sopRdRequestForm;

    public QSopRdRevisionDoc(String variable) {
        this(SopRdRevisionDoc.class, forVariable(variable), INITS);
    }

    public QSopRdRevisionDoc(Path<? extends SopRdRevisionDoc> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSopRdRevisionDoc(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSopRdRevisionDoc(PathMetadata metadata, PathInits inits) {
        this(SopRdRevisionDoc.class, metadata, inits);
    }

    public QSopRdRevisionDoc(Class<? extends SopRdRevisionDoc> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.documentVersion = inits.isInitialized("documentVersion") ? new com.cauh.iso.domain.QDocumentVersion(forProperty("documentVersion"), inits.get("documentVersion")) : null;
        this.sopRdRequestForm = inits.isInitialized("sopRdRequestForm") ? new QSopRdRequestForm(forProperty("sopRdRequestForm"), inits.get("sopRdRequestForm")) : null;
    }

}

