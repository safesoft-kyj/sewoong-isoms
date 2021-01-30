package com.cauh.iso.domain.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSopRdDevelopmentDoc is a Querydsl query type for SopRdDevelopmentDoc
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSopRdDevelopmentDoc extends EntityPathBase<SopRdDevelopmentDoc> {

    private static final long serialVersionUID = -1733967686L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSopRdDevelopmentDoc sopRdDevelopmentDoc = new QSopRdDevelopmentDoc("sopRdDevelopmentDoc");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    public final StringPath categoryId = createString("categoryId");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final StringPath docId = createString("docId");

    public final StringPath docNo = createString("docNo");

    public final EnumPath<com.cauh.iso.domain.constant.DocumentType> documentType = createEnum("documentType", com.cauh.iso.domain.constant.DocumentType.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath sopId = createString("sopId");

    public final QSopRdRequestForm sopRdRequestForm;

    public final StringPath title = createString("title");

    public final StringPath version = createString("version");

    public QSopRdDevelopmentDoc(String variable) {
        this(SopRdDevelopmentDoc.class, forVariable(variable), INITS);
    }

    public QSopRdDevelopmentDoc(Path<? extends SopRdDevelopmentDoc> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSopRdDevelopmentDoc(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSopRdDevelopmentDoc(PathMetadata metadata, PathInits inits) {
        this(SopRdDevelopmentDoc.class, metadata, inits);
    }

    public QSopRdDevelopmentDoc(Class<? extends SopRdDevelopmentDoc> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sopRdRequestForm = inits.isInitialized("sopRdRequestForm") ? new QSopRdRequestForm(forProperty("sopRdRequestForm"), inits.get("sopRdRequestForm")) : null;
    }

}

