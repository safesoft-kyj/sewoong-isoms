package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QISOAccessLog is a Querydsl query type for ISOAccessLog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QISOAccessLog extends EntityPathBase<ISOAccessLog> {

    private static final long serialVersionUID = 170368044L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QISOAccessLog iSOAccessLog = new QISOAccessLog("iSOAccessLog");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    public final EnumPath<com.cauh.iso.domain.constant.DocumentAccessType> accessType = createEnum("accessType", com.cauh.iso.domain.constant.DocumentAccessType.class);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final StringPath id = createString("id");

    public final QISO iso;

    public final QISOCertification isoCertification;

    public final QISOTrainingCertification isoTrainingCertification;

    public final EnumPath<com.cauh.iso.domain.constant.ISOType> isoType = createEnum("isoType", com.cauh.iso.domain.constant.ISOType.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public QISOAccessLog(String variable) {
        this(ISOAccessLog.class, forVariable(variable), INITS);
    }

    public QISOAccessLog(Path<? extends ISOAccessLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QISOAccessLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QISOAccessLog(PathMetadata metadata, PathInits inits) {
        this(ISOAccessLog.class, metadata, inits);
    }

    public QISOAccessLog(Class<? extends ISOAccessLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.iso = inits.isInitialized("iso") ? new QISO(forProperty("iso")) : null;
        this.isoCertification = inits.isInitialized("isoCertification") ? new QISOCertification(forProperty("isoCertification")) : null;
        this.isoTrainingCertification = inits.isInitialized("isoTrainingCertification") ? new QISOTrainingCertification(forProperty("isoTrainingCertification"), inits.get("isoTrainingCertification")) : null;
    }

}

