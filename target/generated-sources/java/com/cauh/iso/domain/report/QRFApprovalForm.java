package com.cauh.iso.domain.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRFApprovalForm is a Querydsl query type for RFApprovalForm
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRFApprovalForm extends EntityPathBase<RFApprovalForm> {

    private static final long serialVersionUID = -1545469628L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRFApprovalForm rFApprovalForm = new QRFApprovalForm("rFApprovalForm");

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

    public QRFApprovalForm(String variable) {
        this(RFApprovalForm.class, forVariable(variable), INITS);
    }

    public QRFApprovalForm(Path<? extends RFApprovalForm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRFApprovalForm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRFApprovalForm(PathMetadata metadata, PathInits inits) {
        this(RFApprovalForm.class, metadata, inits);
    }

    public QRFApprovalForm(Class<? extends RFApprovalForm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.approval = inits.isInitialized("approval") ? new com.cauh.iso.domain.QApproval(forProperty("approval"), inits.get("approval")) : null;
        this.supersededVersion = inits.isInitialized("supersededVersion") ? new com.cauh.iso.domain.QDocumentVersion(forProperty("supersededVersion"), inits.get("supersededVersion")) : null;
    }

}

