package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QISO is a Querydsl query type for ISO
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QISO extends EntityPathBase<ISO> {

    private static final long serialVersionUID = 1100460404L;

    public static final QISO iSO = new QISO("iSO");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final ListPath<ISOAttachFile, QISOAttachFile> attachFiles = this.<ISOAttachFile, QISOAttachFile>createList("attachFiles", ISOAttachFile.class, QISOAttachFile.class, PathInits.DIRECT2);

    public final BooleanPath certification = createBoolean("certification");

    public final StringPath certificationHead = createString("certificationHead");

    public final StringPath content = createString("content");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath id = createString("id");

    public final ListPath<ISOTrainingMatrix, QISOTrainingMatrix> isoTrainingMatrix = this.<ISOTrainingMatrix, QISOTrainingMatrix>createList("isoTrainingMatrix", ISOTrainingMatrix.class, QISOTrainingMatrix.class, PathInits.DIRECT2);

    public final ListPath<ISOTrainingPeriod, QISOTrainingPeriod> isoTrainingPeriods = this.<ISOTrainingPeriod, QISOTrainingPeriod>createList("isoTrainingPeriods", ISOTrainingPeriod.class, QISOTrainingPeriod.class, PathInits.DIRECT2);

    public final EnumPath<com.cauh.iso.domain.constant.ISOType> isoType = createEnum("isoType", com.cauh.iso.domain.constant.ISOType.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final EnumPath<com.cauh.iso.domain.constant.PostStatus> postStatus = createEnum("postStatus", com.cauh.iso.domain.constant.PostStatus.class);

    public final StringPath quiz = createString("quiz");

    public final StringPath title = createString("title");

    public final DateTimePath<java.util.Date> topViewEndDate = createDateTime("topViewEndDate", java.util.Date.class);

    public final BooleanPath training = createBoolean("training");

    public QISO(String variable) {
        super(ISO.class, forVariable(variable));
    }

    public QISO(Path<? extends ISO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QISO(PathMetadata metadata) {
        super(ISO.class, metadata);
    }

}

