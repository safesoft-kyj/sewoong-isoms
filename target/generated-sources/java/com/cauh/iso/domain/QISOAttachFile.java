package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QISOAttachFile is a Querydsl query type for ISOAttachFile
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QISOAttachFile extends EntityPathBase<ISOAttachFile> {

    private static final long serialVersionUID = 164035061L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QISOAttachFile iSOAttachFile = new QISOAttachFile("iSOAttachFile");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> fileSize = createNumber("fileSize", Long.class);

    public final StringPath fileType = createString("fileType");

    public final StringPath id = createString("id");

    public final QISO iso;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath originalFileName = createString("originalFileName");

    public final NumberPath<Integer> totalPage = createNumber("totalPage", Integer.class);

    public QISOAttachFile(String variable) {
        this(ISOAttachFile.class, forVariable(variable), INITS);
    }

    public QISOAttachFile(Path<? extends ISOAttachFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QISOAttachFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QISOAttachFile(PathMetadata metadata, PathInits inits) {
        this(ISOAttachFile.class, metadata, inits);
    }

    public QISOAttachFile(Class<? extends ISOAttachFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.iso = inits.isInitialized("iso") ? new QISO(forProperty("iso")) : null;
    }

}

