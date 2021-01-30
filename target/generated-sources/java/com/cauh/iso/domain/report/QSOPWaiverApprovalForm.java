package com.cauh.iso.domain.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSOPWaiverApprovalForm is a Querydsl query type for SOPWaiverApprovalForm
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSOPWaiverApprovalForm extends EntityPathBase<SOPWaiverApprovalForm> {

    private static final long serialVersionUID = -1221052074L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSOPWaiverApprovalForm sOPWaiverApprovalForm = new QSOPWaiverApprovalForm("sOPWaiverApprovalForm");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    public final com.cauh.iso.domain.QApproval approval;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final com.cauh.iso.domain.QDocumentVersion deviatedSOPDocument;

    public final StringPath deviationDetails = createString("deviationDetails");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath projectNo = createString("projectNo");

    public final StringPath protocolNo = createString("protocolNo");

    public final DateTimePath<java.util.Date> waiverEndDate = createDateTime("waiverEndDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> waiverStartDate = createDateTime("waiverStartDate", java.util.Date.class);

    public QSOPWaiverApprovalForm(String variable) {
        this(SOPWaiverApprovalForm.class, forVariable(variable), INITS);
    }

    public QSOPWaiverApprovalForm(Path<? extends SOPWaiverApprovalForm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSOPWaiverApprovalForm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSOPWaiverApprovalForm(PathMetadata metadata, PathInits inits) {
        this(SOPWaiverApprovalForm.class, metadata, inits);
    }

    public QSOPWaiverApprovalForm(Class<? extends SOPWaiverApprovalForm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.approval = inits.isInitialized("approval") ? new com.cauh.iso.domain.QApproval(forProperty("approval"), inits.get("approval")) : null;
        this.deviatedSOPDocument = inits.isInitialized("deviatedSOPDocument") ? new com.cauh.iso.domain.QDocumentVersion(forProperty("deviatedSOPDocument"), inits.get("deviatedSOPDocument")) : null;
    }

}

