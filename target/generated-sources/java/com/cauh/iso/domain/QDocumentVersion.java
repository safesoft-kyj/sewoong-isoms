package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDocumentVersion is a Querydsl query type for DocumentVersion
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDocumentVersion extends EntityPathBase<DocumentVersion> {

    private static final long serialVersionUID = 1632808364L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDocumentVersion documentVersion = new QDocumentVersion("documentVersion");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    public final DateTimePath<java.util.Date> approvedDate = createDateTime("approvedDate", java.util.Date.class);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final QDocument document;

    public final DateTimePath<java.util.Date> effectiveDate = createDateTime("effectiveDate", java.util.Date.class);

    public final StringPath ext = createString("ext");

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> fileSize = createNumber("fileSize", Long.class);

    public final StringPath fileType = createString("fileType");

    public final StringPath id = createString("id");

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath originalFileName = createString("originalFileName");

    public final QDocumentVersion parentVersion;

    public final StringPath quiz = createString("quiz");

    public final BooleanPath retirement = createBoolean("retirement");

    public final DateTimePath<java.util.Date> retirementDate = createDateTime("retirementDate", java.util.Date.class);

    public final StringPath rfEngExt = createString("rfEngExt");

    public final StringPath rfEngFileName = createString("rfEngFileName");

    public final NumberPath<Long> rfEngFileSize = createNumber("rfEngFileSize", Long.class);

    public final StringPath rfEngFileType = createString("rfEngFileType");

    public final StringPath rfEngOriginalFileName = createString("rfEngOriginalFileName");

    public final StringPath rfKorExt = createString("rfKorExt");

    public final StringPath rfKorFileName = createString("rfKorFileName");

    public final NumberPath<Long> rfKorFileSize = createNumber("rfKorFileSize", Long.class);

    public final StringPath rfKorFileType = createString("rfKorFileType");

    public final StringPath rfKorOriginalFileName = createString("rfKorOriginalFileName");

    public final EnumPath<com.cauh.iso.domain.constant.DocumentStatus> status = createEnum("status", com.cauh.iso.domain.constant.DocumentStatus.class);

    public final NumberPath<Integer> totalPage = createNumber("totalPage", Integer.class);

    public final ListPath<TrainingMatrix, QTrainingMatrix> trainingMatrixList = this.<TrainingMatrix, QTrainingMatrix>createList("trainingMatrixList", TrainingMatrix.class, QTrainingMatrix.class, PathInits.DIRECT2);

    public final StringPath version = createString("version");

    public QDocumentVersion(String variable) {
        this(DocumentVersion.class, forVariable(variable), INITS);
    }

    public QDocumentVersion(Path<? extends DocumentVersion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDocumentVersion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDocumentVersion(PathMetadata metadata, PathInits inits) {
        this(DocumentVersion.class, metadata, inits);
    }

    public QDocumentVersion(Class<? extends DocumentVersion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.document = inits.isInitialized("document") ? new QDocument(forProperty("document"), inits.get("document")) : null;
        this.parentVersion = inits.isInitialized("parentVersion") ? new QDocumentVersion(forProperty("parentVersion"), inits.get("parentVersion")) : null;
    }

}

