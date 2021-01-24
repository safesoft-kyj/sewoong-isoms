package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QISOTrainingPeriod is a Querydsl query type for ISOTrainingPeriod
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QISOTrainingPeriod extends EntityPathBase<ISOTrainingPeriod> {

    private static final long serialVersionUID = 700563919L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QISOTrainingPeriod iSOTrainingPeriod = new QISOTrainingPeriod("iSOTrainingPeriod");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final DateTimePath<java.util.Date> endDate = createDateTime("endDate", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QISO iso;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final com.cauh.common.entity.QAccount retrainingUser;

    public final DateTimePath<java.util.Date> startDate = createDateTime("startDate", java.util.Date.class);

    public final ListPath<TrainingLog, QTrainingLog> trainingLogs = this.<TrainingLog, QTrainingLog>createList("trainingLogs", TrainingLog.class, QTrainingLog.class, PathInits.DIRECT2);

    public final EnumPath<com.cauh.iso.domain.constant.TrainingType> trainingType = createEnum("trainingType", com.cauh.iso.domain.constant.TrainingType.class);

    public QISOTrainingPeriod(String variable) {
        this(ISOTrainingPeriod.class, forVariable(variable), INITS);
    }

    public QISOTrainingPeriod(Path<? extends ISOTrainingPeriod> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QISOTrainingPeriod(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QISOTrainingPeriod(PathMetadata metadata, PathInits inits) {
        this(ISOTrainingPeriod.class, metadata, inits);
    }

    public QISOTrainingPeriod(Class<? extends ISOTrainingPeriod> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.iso = inits.isInitialized("iso") ? new QISO(forProperty("iso")) : null;
        this.retrainingUser = inits.isInitialized("retrainingUser") ? new com.cauh.common.entity.QAccount(forProperty("retrainingUser"), inits.get("retrainingUser")) : null;
    }

}

