package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QISOOfflineTraining is a Querydsl query type for ISOOfflineTraining
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QISOOfflineTraining extends EntityPathBase<ISOOfflineTraining> {

    private static final long serialVersionUID = 1720276937L;

    public static final QISOOfflineTraining iSOOfflineTraining = new QISOOfflineTraining("iSOOfflineTraining");

    public final StringPath empNo = createString("empNo");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

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

