package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOfflineTrainingAttendee is a Querydsl query type for OfflineTrainingAttendee
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOfflineTrainingAttendee extends EntityPathBase<OfflineTrainingAttendee> {

    private static final long serialVersionUID = -761513178L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOfflineTrainingAttendee offlineTrainingAttendee = new QOfflineTrainingAttendee("offlineTrainingAttendee");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    public final com.cauh.common.entity.QAccount account;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final QOfflineTraining offlineTraining;

    public QOfflineTrainingAttendee(String variable) {
        this(OfflineTrainingAttendee.class, forVariable(variable), INITS);
    }

    public QOfflineTrainingAttendee(Path<? extends OfflineTrainingAttendee> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOfflineTrainingAttendee(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOfflineTrainingAttendee(PathMetadata metadata, PathInits inits) {
        this(OfflineTrainingAttendee.class, metadata, inits);
    }

    public QOfflineTrainingAttendee(Class<? extends OfflineTrainingAttendee> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new com.cauh.common.entity.QAccount(forProperty("account"), inits.get("account")) : null;
        this.offlineTraining = inits.isInitialized("offlineTraining") ? new QOfflineTraining(forProperty("offlineTraining")) : null;
    }

}

