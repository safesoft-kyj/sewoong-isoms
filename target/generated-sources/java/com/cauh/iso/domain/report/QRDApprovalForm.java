package com.cauh.iso.domain.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRDApprovalForm is a Querydsl query type for RDApprovalForm
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRDApprovalForm extends EntityPathBase<RDApprovalForm> {

    private static final long serialVersionUID = -958663614L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRDApprovalForm rDApprovalForm = new QRDApprovalForm("rDApprovalForm");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    public final com.cauh.iso.domain.QApproval approval;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> effectiveDate = createDateTime("effectiveDate", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final com.cauh.iso.domain.QDocumentVersion supersededVersion;

    public final StringPath version = createString("version");

    public QRDApprovalForm(String variable) {
        this(RDApprovalForm.class, forVariable(variable), INITS);
    }

    public QRDApprovalForm(Path<? extends RDApprovalForm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRDApprovalForm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRDApprovalForm(PathMetadata metadata, PathInits inits) {
        this(RDApprovalForm.class, metadata, inits);
    }

    public QRDApprovalForm(Class<? extends RDApprovalForm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.approval = inits.isInitialized("approval") ? new com.cauh.iso.domain.QApproval(forProperty("approval"), inits.get("approval")) : null;
        this.supersededVersion = inits.isInitialized("supersededVersion") ? new com.cauh.iso.domain.QDocumentVersion(forProperty("supersededVersion"), inits.get("supersededVersion")) : null;
    }

}

