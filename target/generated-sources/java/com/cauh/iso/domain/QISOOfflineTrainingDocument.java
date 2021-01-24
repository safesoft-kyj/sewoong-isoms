package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QISOOfflineTrainingDocument is a Querydsl query type for ISOOfflineTrainingDocument
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QISOOfflineTrainingDocument extends EntityPathBase<ISOOfflineTrainingDocument> {

    private static final long serialVersionUID = -1338723324L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QISOOfflineTrainingDocument iSOOfflineTrainingDocument = new QISOOfflineTrainingDocument("iSOOfflineTrainingDocument");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final StringPath hour = createString("hour");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QISO iso;

    public final QISOOfflineTraining isoOfflineTraining;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public QISOOfflineTrainingDocument(String variable) {
        this(ISOOfflineTrainingDocument.class, forVariable(variable), INITS);
    }

    public QISOOfflineTrainingDocument(Path<? extends ISOOfflineTrainingDocument> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QISOOfflineTrainingDocument(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QISOOfflineTrainingDocument(PathMetadata metadata, PathInits inits) {
        this(ISOOfflineTrainingDocument.class, metadata, inits);
    }

    public QISOOfflineTrainingDocument(Class<? extends ISOOfflineTrainingDocument> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.iso = inits.isInitialized("iso") ? new QISO(forProperty("iso")) : null;
        this.isoOfflineTraining = inits.isInitialized("isoOfflineTraining") ? new QISOOfflineTraining(forProperty("isoOfflineTraining")) : null;
    }

}

