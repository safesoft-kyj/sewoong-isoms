package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDocumentAccessLog is a Querydsl query type for DocumentAccessLog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDocumentAccessLog extends EntityPathBase<DocumentAccessLog> {

    private static final long serialVersionUID = 757823252L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDocumentAccessLog documentAccessLog = new QDocumentAccessLog("documentAccessLog");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    public final EnumPath<com.cauh.iso.domain.constant.DocumentAccessType> accessType = createEnum("accessType", com.cauh.iso.domain.constant.DocumentAccessType.class);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final QDocumentVersion documentVersion;

    public final StringPath id = createString("id");

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public QDocumentAccessLog(String variable) {
        this(DocumentAccessLog.class, forVariable(variable), INITS);
    }

    public QDocumentAccessLog(Path<? extends DocumentAccessLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDocumentAccessLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDocumentAccessLog(PathMetadata metadata, PathInits inits) {
        this(DocumentAccessLog.class, metadata, inits);
    }

    public QDocumentAccessLog(Class<? extends DocumentAccessLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.documentVersion = inits.isInitialized("documentVersion") ? new QDocumentVersion(forProperty("documentVersion"), inits.get("documentVersion")) : null;
    }

}

