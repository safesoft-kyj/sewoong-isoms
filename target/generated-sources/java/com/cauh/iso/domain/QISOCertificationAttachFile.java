package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QISOCertificationAttachFile is a Querydsl query type for ISOCertificationAttachFile
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QISOCertificationAttachFile extends EntityPathBase<ISOCertificationAttachFile> {

    private static final long serialVersionUID = -1062174297L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QISOCertificationAttachFile iSOCertificationAttachFile = new QISOCertificationAttachFile("iSOCertificationAttachFile");

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

    public final QISOCertification isoCertification;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath originalFileName = createString("originalFileName");

    public QISOCertificationAttachFile(String variable) {
        this(ISOCertificationAttachFile.class, forVariable(variable), INITS);
    }

    public QISOCertificationAttachFile(Path<? extends ISOCertificationAttachFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QISOCertificationAttachFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QISOCertificationAttachFile(PathMetadata metadata, PathInits inits) {
        this(ISOCertificationAttachFile.class, metadata, inits);
    }

    public QISOCertificationAttachFile(Class<? extends ISOCertificationAttachFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.isoCertification = inits.isInitialized("isoCertification") ? new QISOCertification(forProperty("isoCertification")) : null;
    }

}

