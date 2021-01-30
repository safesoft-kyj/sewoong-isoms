package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOfflineTraining is a Querydsl query type for OfflineTraining
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOfflineTraining extends EntityPathBase<OfflineTraining> {

    private static final long serialVersionUID = -1517480372L;

    public static final QOfflineTraining offlineTraining = new QOfflineTraining("offlineTraining");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final EnumPath<com.cauh.iso.domain.constant.DocumentType> documentType = createEnum("documentType", com.cauh.iso.domain.constant.DocumentType.class);

    public final StringPath empNo = createString("empNo");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final ListPath<OfflineTrainingAttendee, QOfflineTrainingAttendee> offlineTrainingAttendees = this.<OfflineTrainingAttendee, QOfflineTrainingAttendee>createList("offlineTrainingAttendees", OfflineTrainingAttendee.class, QOfflineTrainingAttendee.class, PathInits.DIRECT2);

    public final ListPath<OfflineTrainingDocument, QOfflineTrainingDocument> offlineTrainingDocuments = this.<OfflineTrainingDocument, QOfflineTrainingDocument>createList("offlineTrainingDocuments", OfflineTrainingDocument.class, QOfflineTrainingDocument.class, PathInits.DIRECT2);

    public final StringPath organization = createString("organization");

    public final EnumPath<com.cauh.iso.domain.constant.OfflineTrainingStatus> status = createEnum("status", com.cauh.iso.domain.constant.OfflineTrainingStatus.class);

    public final DateTimePath<java.util.Date> trainingDate = createDateTime("trainingDate", java.util.Date.class);

    public QOfflineTraining(String variable) {
        super(OfflineTraining.class, forVariable(variable));
    }

    public QOfflineTraining(Path<? extends OfflineTraining> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOfflineTraining(PathMetadata metadata) {
        super(OfflineTraining.class, metadata);
    }

}

