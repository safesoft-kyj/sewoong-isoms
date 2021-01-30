package com.cauh.iso.domain.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRetirementApprovalForm is a Querydsl query type for RetirementApprovalForm
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRetirementApprovalForm extends EntityPathBase<RetirementApprovalForm> {

    private static final long serialVersionUID = 2051893929L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRetirementApprovalForm retirementApprovalForm = new QRetirementApprovalForm("retirementApprovalForm");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    public final com.cauh.iso.domain.QApproval approval;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath reason = createString("reason");

    public final ListPath<RetirementDocument, QRetirementDocument> retirementDocumentRFs = this.<RetirementDocument, QRetirementDocument>createList("retirementDocumentRFs", RetirementDocument.class, QRetirementDocument.class, PathInits.DIRECT2);

    public final ListPath<RetirementDocument, QRetirementDocument> retirementDocumentSOPs = this.<RetirementDocument, QRetirementDocument>createList("retirementDocumentSOPs", RetirementDocument.class, QRetirementDocument.class, PathInits.DIRECT2);

    public QRetirementApprovalForm(String variable) {
        this(RetirementApprovalForm.class, forVariable(variable), INITS);
    }

    public QRetirementApprovalForm(Path<? extends RetirementApprovalForm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRetirementApprovalForm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRetirementApprovalForm(PathMetadata metadata, PathInits inits) {
        this(RetirementApprovalForm.class, metadata, inits);
    }

    public QRetirementApprovalForm(Class<? extends RetirementApprovalForm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.approval = inits.isInitialized("approval") ? new com.cauh.iso.domain.QApproval(forProperty("approval"), inits.get("approval")) : null;
    }

}

