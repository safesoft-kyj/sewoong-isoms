package com.cauh.iso.domain.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSopRfDevelopmentDoc is a Querydsl query type for SopRfDevelopmentDoc
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSopRfDevelopmentDoc extends EntityPathBase<SopRfDevelopmentDoc> {

    private static final long serialVersionUID = 1281135932L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSopRfDevelopmentDoc sopRfDevelopmentDoc = new QSopRfDevelopmentDoc("sopRfDevelopmentDoc");

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

    public final QSopRfRequestForm sopRfRequestForm;

    public final StringPath title = createString("title");

    public final StringPath version = createString("version");

    public QSopRfDevelopmentDoc(String variable) {
        this(SopRfDevelopmentDoc.class, forVariable(variable), INITS);
    }

    public QSopRfDevelopmentDoc(Path<? extends SopRfDevelopmentDoc> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSopRfDevelopmentDoc(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSopRfDevelopmentDoc(PathMetadata metadata, PathInits inits) {
        this(SopRfDevelopmentDoc.class, metadata, inits);
    }

    public QSopRfDevelopmentDoc(Class<? extends SopRfDevelopmentDoc> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sopRfRequestForm = inits.isInitialized("sopRfRequestForm") ? new QSopRfRequestForm(forProperty("sopRfRequestForm"), inits.get("sopRfRequestForm")) : null;
    }

}

