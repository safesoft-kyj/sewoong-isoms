package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QISOTrainingTestLog is a Querydsl query type for ISOTrainingTestLog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QISOTrainingTestLog extends EntityPathBase<ISOTrainingTestLog> {

    private static final long serialVersionUID = -501089532L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QISOTrainingTestLog iSOTrainingTestLog = new QISOTrainingTestLog("iSOTrainingTestLog");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QISOTrainingLog isoTrainingLog;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final SimplePath<Quiz> quiz = createSimple("quiz", Quiz.class);

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final EnumPath<com.cauh.iso.domain.constant.TrainingStatus> status = createEnum("status", com.cauh.iso.domain.constant.TrainingStatus.class);

    public QISOTrainingTestLog(String variable) {
        this(ISOTrainingTestLog.class, forVariable(variable), INITS);
    }

    public QISOTrainingTestLog(Path<? extends ISOTrainingTestLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QISOTrainingTestLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QISOTrainingTestLog(PathMetadata metadata, PathInits inits) {
        this(ISOTrainingTestLog.class, metadata, inits);
    }

    public QISOTrainingTestLog(Class<? extends ISOTrainingTestLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.isoTrainingLog = inits.isInitialized("isoTrainingLog") ? new QISOTrainingLog(forProperty("isoTrainingLog"), inits.get("isoTrainingLog")) : null;
    }

}

