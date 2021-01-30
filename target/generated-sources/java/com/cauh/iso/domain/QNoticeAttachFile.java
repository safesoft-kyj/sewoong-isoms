package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNoticeAttachFile is a Querydsl query type for NoticeAttachFile
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QNoticeAttachFile extends EntityPathBase<NoticeAttachFile> {

    private static final long serialVersionUID = 1794707914L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNoticeAttachFile noticeAttachFile = new QNoticeAttachFile("noticeAttachFile");

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

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final QNotice notice;

    public final StringPath originalFileName = createString("originalFileName");

    public QNoticeAttachFile(String variable) {
        this(NoticeAttachFile.class, forVariable(variable), INITS);
    }

    public QNoticeAttachFile(Path<? extends NoticeAttachFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNoticeAttachFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNoticeAttachFile(PathMetadata metadata, PathInits inits) {
        this(NoticeAttachFile.class, metadata, inits);
    }

    public QNoticeAttachFile(Class<? extends NoticeAttachFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.notice = inits.isInitialized("notice") ? new QNotice(forProperty("notice")) : null;
    }

}

