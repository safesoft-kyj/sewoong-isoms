package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOfflineTrainingDocument is a Querydsl query type for OfflineTrainingDocument
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOfflineTrainingDocument extends EntityPathBase<OfflineTrainingDocument> {

    private static final long serialVersionUID = -442548345L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOfflineTrainingDocument offlineTrainingDocument = new QOfflineTrainingDocument("offlineTrainingDocument");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final QDocumentVersion documentVersion;

    public final StringPath hour = createString("hour");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final QOfflineTraining offlineTraining;

    public QOfflineTrainingDocument(String variable) {
        this(OfflineTrainingDocument.class, forVariable(variable), INITS);
    }

    public QOfflineTrainingDocument(Path<? extends OfflineTrainingDocument> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOfflineTrainingDocument(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOfflineTrainingDocument(PathMetadata metadata, PathInits inits) {
        this(OfflineTrainingDocument.class, metadata, inits);
    }

    public QOfflineTrainingDocument(Class<? extends OfflineTrainingDocument> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.documentVersion = inits.isInitialized("documentVersion") ? new QDocumentVersion(forProperty("documentVersion"), inits.get("documentVersion")) : null;
        this.offlineTraining = inits.isInitialized("offlineTraining") ? new QOfflineTraining(forProperty("offlineTraining")) : null;
    }

}

