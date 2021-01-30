package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QISOCertification is a Querydsl query type for ISOCertification
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QISOCertification extends EntityPathBase<ISOCertification> {

    private static final long serialVersionUID = 98815910L;

    public static final QISOCertification iSOCertification = new QISOCertification("iSOCertification");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    public final ListPath<ISOCertificationAttachFile, QISOCertificationAttachFile> attachFiles = this.<ISOCertificationAttachFile, QISOCertificationAttachFile>createList("attachFiles", ISOCertificationAttachFile.class, QISOCertificationAttachFile.class, PathInits.DIRECT2);

    public final DateTimePath<java.util.Date> certDate = createDateTime("certDate", java.util.Date.class);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final EnumPath<com.cauh.iso.domain.constant.PostStatus> postStatus = createEnum("postStatus", com.cauh.iso.domain.constant.PostStatus.class);

    public final StringPath title = createString("title");

    public QISOCertification(String variable) {
        super(ISOCertification.class, forVariable(variable));
    }

    public QISOCertification(Path<? extends ISOCertification> path) {
        super(path.getType(), path.getMetadata());
    }

    public QISOCertification(PathMetadata metadata) {
        super(ISOCertification.class, metadata);
    }

}

