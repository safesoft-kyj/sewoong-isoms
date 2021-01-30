package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrainingTestLog is a Querydsl query type for TrainingTestLog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTrainingTestLog extends EntityPathBase<TrainingTestLog> {

    private static final long serialVersionUID = 556120455L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrainingTestLog trainingTestLog = new QTrainingTestLog("trainingTestLog");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final SimplePath<Quiz> quiz = createSimple("quiz", Quiz.class);

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final EnumPath<com.cauh.iso.domain.constant.TrainingStatus> status = createEnum("status", com.cauh.iso.domain.constant.TrainingStatus.class);

    public final QTrainingLog trainingLog;

    public QTrainingTestLog(String variable) {
        this(TrainingTestLog.class, forVariable(variable), INITS);
    }

    public QTrainingTestLog(Path<? extends TrainingTestLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrainingTestLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrainingTestLog(PathMetadata metadata, PathInits inits) {
        this(TrainingTestLog.class, metadata, inits);
    }

    public QTrainingTestLog(Class<? extends TrainingTestLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.trainingLog = inits.isInitialized("trainingLog") ? new QTrainingLog(forProperty("trainingLog"), inits.get("trainingLog")) : null;
    }

}

