package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrainingPeriod is a Querydsl query type for TrainingPeriod
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTrainingPeriod extends EntityPathBase<TrainingPeriod> {

    private static final long serialVersionUID = 1843046124L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrainingPeriod trainingPeriod = new QTrainingPeriod("trainingPeriod");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final QDocumentVersion documentVersion;

    public final DateTimePath<java.util.Date> endDate = createDateTime("endDate", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final com.cauh.common.entity.QAccount retrainingUser;

    public final DateTimePath<java.util.Date> startDate = createDateTime("startDate", java.util.Date.class);

    public final ListPath<TrainingLog, QTrainingLog> trainingLogs = this.<TrainingLog, QTrainingLog>createList("trainingLogs", TrainingLog.class, QTrainingLog.class, PathInits.DIRECT2);

    public final EnumPath<com.cauh.iso.domain.constant.TrainingType> trainingType = createEnum("trainingType", com.cauh.iso.domain.constant.TrainingType.class);

    public QTrainingPeriod(String variable) {
        this(TrainingPeriod.class, forVariable(variable), INITS);
    }

    public QTrainingPeriod(Path<? extends TrainingPeriod> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrainingPeriod(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrainingPeriod(PathMetadata metadata, PathInits inits) {
        this(TrainingPeriod.class, metadata, inits);
    }

    public QTrainingPeriod(Class<? extends TrainingPeriod> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.documentVersion = inits.isInitialized("documentVersion") ? new QDocumentVersion(forProperty("documentVersion"), inits.get("documentVersion")) : null;
        this.retrainingUser = inits.isInitialized("retrainingUser") ? new com.cauh.common.entity.QAccount(forProperty("retrainingUser"), inits.get("retrainingUser")) : null;
    }

}

