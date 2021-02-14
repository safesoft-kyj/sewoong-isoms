package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QISOTrainingMatrix is a Querydsl query type for ISOTrainingMatrix
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QISOTrainingMatrix extends EntityPathBase<ISOTrainingMatrix> {

    private static final long serialVersionUID = 611050447L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QISOTrainingMatrix iSOTrainingMatrix = new QISOTrainingMatrix("iSOTrainingMatrix");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QISO iso;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final BooleanPath trainingAll = createBoolean("trainingAll");

    public final com.cauh.common.entity.QAccount user;

    public QISOTrainingMatrix(String variable) {
        this(ISOTrainingMatrix.class, forVariable(variable), INITS);
    }

    public QISOTrainingMatrix(Path<? extends ISOTrainingMatrix> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QISOTrainingMatrix(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QISOTrainingMatrix(PathMetadata metadata, PathInits inits) {
        this(ISOTrainingMatrix.class, metadata, inits);
    }

    public QISOTrainingMatrix(Class<? extends ISOTrainingMatrix> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.iso = inits.isInitialized("iso") ? new QISO(forProperty("iso")) : null;
        this.user = inits.isInitialized("user") ? new com.cauh.common.entity.QAccount(forProperty("user"), inits.get("user")) : null;
    }

}

