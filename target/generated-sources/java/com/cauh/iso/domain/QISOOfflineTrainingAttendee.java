package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QISOOfflineTrainingAttendee is a Querydsl query type for ISOOfflineTrainingAttendee
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QISOOfflineTrainingAttendee extends EntityPathBase<ISOOfflineTrainingAttendee> {

    private static final long serialVersionUID = -1657688157L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QISOOfflineTrainingAttendee iSOOfflineTrainingAttendee = new QISOOfflineTrainingAttendee("iSOOfflineTrainingAttendee");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    public final com.cauh.common.entity.QAccount account;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QISOOfflineTraining ISOOfflineTraining;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public QISOOfflineTrainingAttendee(String variable) {
        this(ISOOfflineTrainingAttendee.class, forVariable(variable), INITS);
    }

    public QISOOfflineTrainingAttendee(Path<? extends ISOOfflineTrainingAttendee> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QISOOfflineTrainingAttendee(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QISOOfflineTrainingAttendee(PathMetadata metadata, PathInits inits) {
        this(ISOOfflineTrainingAttendee.class, metadata, inits);
    }

    public QISOOfflineTrainingAttendee(Class<? extends ISOOfflineTrainingAttendee> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new com.cauh.common.entity.QAccount(forProperty("account"), inits.get("account")) : null;
        this.ISOOfflineTraining = inits.isInitialized("ISOOfflineTraining") ? new QISOOfflineTraining(forProperty("ISOOfflineTraining")) : null;
    }

}

