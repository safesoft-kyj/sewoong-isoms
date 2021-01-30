package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTrainingMatrixFile is a Querydsl query type for TrainingMatrixFile
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTrainingMatrixFile extends EntityPathBase<TrainingMatrixFile> {

    private static final long serialVersionUID = -1778393976L;

    public static final QTrainingMatrixFile trainingMatrixFile = new QTrainingMatrixFile("trainingMatrixFile");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final EnumPath<com.cauh.iso.domain.constant.DocumentType> documentType = createEnum("documentType", com.cauh.iso.domain.constant.DocumentType.class);

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> fileSize = createNumber("fileSize", Long.class);

    public final StringPath fileType = createString("fileType");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath originalFileName = createString("originalFileName");

    public final StringPath title = createString("title");

    public QTrainingMatrixFile(String variable) {
        super(TrainingMatrixFile.class, forVariable(variable));
    }

    public QTrainingMatrixFile(Path<? extends TrainingMatrixFile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTrainingMatrixFile(PathMetadata metadata) {
        super(TrainingMatrixFile.class, metadata);
    }

}

