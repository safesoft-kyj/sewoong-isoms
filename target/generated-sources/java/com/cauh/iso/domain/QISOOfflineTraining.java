package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QISOOfflineTraining is a Querydsl query type for ISOOfflineTraining
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QISOOfflineTraining extends EntityPathBase<ISOOfflineTraining> {

    private static final long serialVersionUID = 1720276937L;

    public static final QISOOfflineTraining iSOOfflineTraining = new QISOOfflineTraining("iSOOfflineTraining");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final StringPath empNo = createString("empNo");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final ListPath<ISOOfflineTrainingAttendee, QISOOfflineTrainingAttendee> isoOfflineTrainingAttendees = this.<ISOOfflineTrainingAttendee, QISOOfflineTrainingAttendee>createList("isoOfflineTrainingAttendees", ISOOfflineTrainingAttendee.class, QISOOfflineTrainingAttendee.class, PathInits.DIRECT2);

    public final ListPath<ISOOfflineTrainingDocument, QISOOfflineTrainingDocument> isoOfflineTrainingDocuments = this.<ISOOfflineTrainingDocument, QISOOfflineTrainingDocument>createList("isoOfflineTrainingDocuments", ISOOfflineTrainingDocument.class, QISOOfflineTrainingDocument.class, PathInits.DIRECT2);

    public final EnumPath<com.cauh.iso.domain.constant.ISOType> isoType = createEnum("isoType", com.cauh.iso.domain.constant.ISOType.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath organization = createString("organization");

    public final EnumPath<com.cauh.iso.domain.constant.OfflineTrainingStatus> status = createEnum("status", com.cauh.iso.domain.constant.OfflineTrainingStatus.class);

    public final DateTimePath<java.util.Date> trainingDate = createDateTime("trainingDate", java.util.Date.class);

    public QISOOfflineTraining(String variable) {
        super(ISOOfflineTraining.class, forVariable(variable));
    }

    public QISOOfflineTraining(Path<? extends ISOOfflineTraining> path) {
        super(path.getType(), path.getMetadata());
    }

    public QISOOfflineTraining(PathMetadata metadata) {
        super(ISOOfflineTraining.class, metadata);
    }

}

