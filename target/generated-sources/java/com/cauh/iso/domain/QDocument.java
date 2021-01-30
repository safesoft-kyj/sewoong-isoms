package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDocument is a Querydsl query type for Document
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDocument extends EntityPathBase<Document> {

    private static final long serialVersionUID = -552701556L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDocument document = new QDocument("document");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    public final QCategory category;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final StringPath docId = createString("docId");

    public final StringPath documentNo = createString("documentNo");

    public final ListPath<DocumentVersion, QDocumentVersion> documentVersionList = this.<DocumentVersion, QDocumentVersion>createList("documentVersionList", DocumentVersion.class, QDocumentVersion.class, PathInits.DIRECT2);

    public final StringPath id = createString("id");

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final ListPath<Document, QDocument> rfList = this.<Document, QDocument>createList("rfList", Document.class, QDocument.class, PathInits.DIRECT2);

    public final QDocument sop;

    public final StringPath title = createString("title");

    public final EnumPath<com.cauh.iso.domain.constant.DocumentType> type = createEnum("type", com.cauh.iso.domain.constant.DocumentType.class);

    public QDocument(String variable) {
        this(Document.class, forVariable(variable), INITS);
    }

    public QDocument(Path<? extends Document> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDocument(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDocument(PathMetadata metadata, PathInits inits) {
        this(Document.class, metadata, inits);
    }

    public QDocument(Class<? extends Document> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.sop = inits.isInitialized("sop") ? new QDocument(forProperty("sop"), inits.get("sop")) : null;
    }

}

