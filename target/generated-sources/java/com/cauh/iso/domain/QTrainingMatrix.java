package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrainingMatrix is a Querydsl query type for TrainingMatrix
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTrainingMatrix extends EntityPathBase<TrainingMatrix> {

    private static final long serialVersionUID = 1753532652L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrainingMatrix trainingMatrix = new QTrainingMatrix("trainingMatrix");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final EnumPath<com.cauh.iso.domain.constant.DocumentType> documentType = createEnum("documentType", com.cauh.iso.domain.constant.DocumentType.class);

    public final QDocumentVersion documentVersion;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final com.cauh.common.entity.QJobDescription jobDescription;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final BooleanPath trainingAll = createBoolean("trainingAll");

    public QTrainingMatrix(String variable) {
        this(TrainingMatrix.class, forVariable(variable), INITS);
    }

    public QTrainingMatrix(Path<? extends TrainingMatrix> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrainingMatrix(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrainingMatrix(PathMetadata metadata, PathInits inits) {
        this(TrainingMatrix.class, metadata, inits);
    }

    public QTrainingMatrix(Class<? extends TrainingMatrix> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.documentVersion = inits.isInitialized("documentVersion") ? new QDocumentVersion(forProperty("documentVersion"), inits.get("documentVersion")) : null;
        this.jobDescription = inits.isInitialized("jobDescription") ? new com.cauh.common.entity.QJobDescription(forProperty("jobDescription")) : null;
    }

}

