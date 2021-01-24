package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QISOTrainingLog is a Querydsl query type for ISOTrainingLog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QISOTrainingLog extends EntityPathBase<ISOTrainingLog> {

    private static final long serialVersionUID = -616594954L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QISOTrainingLog iSOTrainingLog = new QISOTrainingLog("iSOTrainingLog");

    public final DateTimePath<java.util.Date> completeDate = createDateTime("completeDate", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QISO iso;

    public final QISOOfflineTraining isoOfflineTraining;

    public final QISOTrainingPeriod isoTrainingPeriod;

    public final ListPath<ISOTrainingTestLog, QISOTrainingTestLog> isoTrainingTestLogs = this.<ISOTrainingTestLog, QISOTrainingTestLog>createList("isoTrainingTestLogs", ISOTrainingTestLog.class, QISOTrainingTestLog.class, PathInits.DIRECT2);

    public final NumberPath<Integer> lastPageNo = createNumber("lastPageNo", Integer.class);

    public final NumberPath<Double> progressPercent = createNumber("progressPercent", Double.class);

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final EnumPath<com.cauh.iso.domain.constant.TrainingStatus> status = createEnum("status", com.cauh.iso.domain.constant.TrainingStatus.class);

    public final NumberPath<Integer> trainingTime = createNumber("trainingTime", Integer.class);

    public final EnumPath<com.cauh.iso.domain.constant.TrainingType> type = createEnum("type", com.cauh.iso.domain.constant.TrainingType.class);

    public final com.cauh.common.entity.QAccount user;

    public QISOTrainingLog(String variable) {
        this(ISOTrainingLog.class, forVariable(variable), INITS);
    }

    public QISOTrainingLog(Path<? extends ISOTrainingLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QISOTrainingLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QISOTrainingLog(PathMetadata metadata, PathInits inits) {
        this(ISOTrainingLog.class, metadata, inits);
    }

    public QISOTrainingLog(Class<? extends ISOTrainingLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.iso = inits.isInitialized("iso") ? new QISO(forProperty("iso")) : null;
        this.isoOfflineTraining = inits.isInitialized("isoOfflineTraining") ? new QISOOfflineTraining(forProperty("isoOfflineTraining")) : null;
        this.isoTrainingPeriod = inits.isInitialized("isoTrainingPeriod") ? new QISOTrainingPeriod(forProperty("isoTrainingPeriod"), inits.get("isoTrainingPeriod")) : null;
        this.user = inits.isInitialized("user") ? new com.cauh.common.entity.QAccount(forProperty("user"), inits.get("user")) : null;
    }

}

