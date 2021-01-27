package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QISOTrainingMatrixFile is a Querydsl query type for ISOTrainingMatrixFile
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QISOTrainingMatrixFile extends EntityPathBase<ISOTrainingMatrixFile> {

    private static final long serialVersionUID = -2125935125L;

    public static final QISOTrainingMatrixFile iSOTrainingMatrixFile = new QISOTrainingMatrixFile("iSOTrainingMatrixFile");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> fileSize = createNumber("fileSize", Long.class);

    public final StringPath fileType = createString("fileType");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final EnumPath<com.cauh.iso.domain.constant.ISOType> isoType = createEnum("isoType", com.cauh.iso.domain.constant.ISOType.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath originalFileName = createString("originalFileName");

    public final StringPath title = createString("title");

    public QISOTrainingMatrixFile(String variable) {
        super(ISOTrainingMatrixFile.class, forVariable(variable));
    }

    public QISOTrainingMatrixFile(Path<? extends ISOTrainingMatrixFile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QISOTrainingMatrixFile(PathMetadata metadata) {
        super(ISOTrainingMatrixFile.class, metadata);
    }

}

