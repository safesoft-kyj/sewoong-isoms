package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QApprovalLine is a Querydsl query type for ApprovalLine
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QApprovalLine extends EntityPathBase<ApprovalLine> {

    private static final long serialVersionUID = 1299230920L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QApprovalLine approvalLine = new QApprovalLine("approvalLine");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    public final QApproval approval;

    public final StringPath base64signature = createString("base64signature");

    public final StringPath comments = createString("comments");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final StringPath displayName = createString("displayName");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final EnumPath<com.cauh.iso.domain.constant.ApprovalLineType> lineType = createEnum("lineType", com.cauh.iso.domain.constant.ApprovalLineType.class);

    public final EnumPath<com.cauh.iso.domain.constant.ApprovalStatus> status = createEnum("status", com.cauh.iso.domain.constant.ApprovalStatus.class);

    public final StringPath username = createString("username");

    public QApprovalLine(String variable) {
        this(ApprovalLine.class, forVariable(variable), INITS);
    }

    public QApprovalLine(Path<? extends ApprovalLine> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QApprovalLine(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QApprovalLine(PathMetadata metadata, PathInits inits) {
        this(ApprovalLine.class, metadata, inits);
    }

    public QApprovalLine(Class<? extends ApprovalLine> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.approval = inits.isInitialized("approval") ? new QApproval(forProperty("approval"), inits.get("approval")) : null;
    }

}

