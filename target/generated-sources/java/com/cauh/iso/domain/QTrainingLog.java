package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrainingLog is a Querydsl query type for TrainingLog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTrainingLog extends EntityPathBase<TrainingLog> {

    private static final long serialVersionUID = -1315636743L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrainingLog trainingLog = new QTrainingLog("trainingLog");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    public final DateTimePath<java.util.Date> completeDate = createDateTime("completeDate", java.util.Date.class);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final QDocumentVersion documentVersion;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Integer> lastPageNo = createNumber("lastPageNo", Integer.class);

    public final QOfflineTraining offlineTraining;

    public final StringPath organizationOther = createString("organizationOther");

    public final NumberPath<Double> progressPercent = createNumber("progressPercent", Double.class);

    public final EnumPath<com.cauh.iso.domain.constant.DeviationReportStatus> reportStatus = createEnum("reportStatus", com.cauh.iso.domain.constant.DeviationReportStatus.class);

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final EnumPath<com.cauh.iso.domain.constant.TrainingStatus> status = createEnum("status", com.cauh.iso.domain.constant.TrainingStatus.class);

    public final QTrainingPeriod trainingPeriod;

    public final ListPath<TrainingTestLog, QTrainingTestLog> trainingTestLogs = this.<TrainingTestLog, QTrainingTestLog>createList("trainingTestLogs", TrainingTestLog.class, QTrainingTestLog.class, PathInits.DIRECT2);

    public final NumberPath<Integer> trainingTime = createNumber("trainingTime", Integer.class);

    public final EnumPath<com.cauh.iso.domain.constant.TrainingType> type = createEnum("type", com.cauh.iso.domain.constant.TrainingType.class);

    public final com.cauh.common.entity.QAccount user;

    public QTrainingLog(String variable) {
        this(TrainingLog.class, forVariable(variable), INITS);
    }

    public QTrainingLog(Path<? extends TrainingLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrainingLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrainingLog(PathMetadata metadata, PathInits inits) {
        this(TrainingLog.class, metadata, inits);
    }

    public QTrainingLog(Class<? extends TrainingLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.documentVersion = inits.isInitialized("documentVersion") ? new QDocumentVersion(forProperty("documentVersion"), inits.get("documentVersion")) : null;
        this.offlineTraining = inits.isInitialized("offlineTraining") ? new QOfflineTraining(forProperty("offlineTraining")) : null;
        this.trainingPeriod = inits.isInitialized("trainingPeriod") ? new QTrainingPeriod(forProperty("trainingPeriod"), inits.get("trainingPeriod")) : null;
        this.user = inits.isInitialized("user") ? new com.cauh.common.entity.QAccount(forProperty("user"), inits.get("user")) : null;
    }

}

