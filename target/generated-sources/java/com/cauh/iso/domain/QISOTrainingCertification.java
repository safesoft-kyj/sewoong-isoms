package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QISOTrainingCertification is a Querydsl query type for ISOTrainingCertification
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QISOTrainingCertification extends EntityPathBase<ISOTrainingCertification> {

    private static final long serialVersionUID = 1721367788L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QISOTrainingCertification iSOTrainingCertification = new QISOTrainingCertification("iSOTrainingCertification");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    public final StringPath certHtml = createString("certHtml");

    public final StringPath certNo = createString("certNo");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QISO iso;

    public final QISOTrainingLog isoTrainingLog;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final com.cauh.common.entity.QAccount user;

    public QISOTrainingCertification(String variable) {
        this(ISOTrainingCertification.class, forVariable(variable), INITS);
    }

    public QISOTrainingCertification(Path<? extends ISOTrainingCertification> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QISOTrainingCertification(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QISOTrainingCertification(PathMetadata metadata, PathInits inits) {
        this(ISOTrainingCertification.class, metadata, inits);
    }

    public QISOTrainingCertification(Class<? extends ISOTrainingCertification> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.iso = inits.isInitialized("iso") ? new QISO(forProperty("iso")) : null;
        this.isoTrainingLog = inits.isInitialized("isoTrainingLog") ? new QISOTrainingLog(forProperty("isoTrainingLog"), inits.get("isoTrainingLog")) : null;
        this.user = inits.isInitialized("user") ? new com.cauh.common.entity.QAccount(forProperty("user"), inits.get("user")) : null;
    }

}

