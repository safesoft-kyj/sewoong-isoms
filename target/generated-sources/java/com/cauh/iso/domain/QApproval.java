package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QApproval is a Querydsl query type for Approval
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QApproval extends EntityPathBase<Approval> {

    private static final long serialVersionUID = -229177676L;

    public static ConstructorExpression<Approval> create(Expression<Integer> id, Expression<com.cauh.iso.domain.constant.ReportType> type, Expression<com.cauh.iso.domain.constant.ApprovalStatus> status, Expression<String> createdBy, Expression<? extends java.sql.Timestamp> createdDate, Expression<String> lastModifiedBy, Expression<? extends java.sql.Timestamp> lastModifiedDate, Expression<String> keyword) {
        return Projections.constructor(Approval.class, new Class<?>[]{int.class, com.cauh.iso.domain.constant.ReportType.class, com.cauh.iso.domain.constant.ApprovalStatus.class, String.class, java.sql.Timestamp.class, String.class, java.sql.Timestamp.class, String.class}, id, type, status, createdBy, createdDate, lastModifiedBy, lastModifiedDate, keyword);
    }

    public static ConstructorExpression<Approval> create(Expression<Integer> id, Expression<com.cauh.iso.domain.constant.ReportType> type, Expression<com.cauh.iso.domain.constant.ApprovalStatus> status, Expression<String> createdBy, Expression<? extends java.sql.Timestamp> createdDate, Expression<String> lastModifiedBy, Expression<? extends java.sql.Timestamp> lastModifiedDate) {
        return Projections.constructor(Approval.class, new Class<?>[]{int.class, com.cauh.iso.domain.constant.ReportType.class, com.cauh.iso.domain.constant.ApprovalStatus.class, String.class, java.sql.Timestamp.class, String.class, java.sql.Timestamp.class}, id, type, status, createdBy, createdDate, lastModifiedBy, lastModifiedDate);
    }

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QApproval approval = new QApproval("approval");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    public final ListPath<ApprovalLine, QApprovalLine> approvalLines = this.<ApprovalLine, QApprovalLine>createList("approvalLines", ApprovalLine.class, QApprovalLine.class, PathInits.DIRECT2);

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

    public final com.cauh.iso.domain.report.QRetirementApprovalForm retirementApprovalForm;

    public final com.cauh.iso.domain.report.QSOPDeviationReport sopDeviationReport;

    public final com.cauh.iso.domain.report.QSOPDisclosureRequestForm sopDisclosureRequestForm;

    public final com.cauh.iso.domain.report.QSopRdRequestForm sopRfRequestForm;

    public final com.cauh.iso.domain.report.QSOPWaiverApprovalForm sopWaiverApprovalForm;

    public final EnumPath<com.cauh.iso.domain.constant.ApprovalStatus> status = createEnum("status", com.cauh.iso.domain.constant.ApprovalStatus.class);

    public final EnumPath<com.cauh.iso.domain.constant.ReportType> type = createEnum("type", com.cauh.iso.domain.constant.ReportType.class);

    public final StringPath username = createString("username");

    public QApproval(String variable) {
        this(Approval.class, forVariable(variable), INITS);
    }

    public QApproval(Path<? extends Approval> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QApproval(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QApproval(PathMetadata metadata, PathInits inits) {
        this(Approval.class, metadata, inits);
    }

    public QApproval(Class<? extends Approval> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.retirementApprovalForm = inits.isInitialized("retirementApprovalForm") ? new com.cauh.iso.domain.report.QRetirementApprovalForm(forProperty("retirementApprovalForm"), inits.get("retirementApprovalForm")) : null;
        this.sopDeviationReport = inits.isInitialized("sopDeviationReport") ? new com.cauh.iso.domain.report.QSOPDeviationReport(forProperty("sopDeviationReport"), inits.get("sopDeviationReport")) : null;
        this.sopDisclosureRequestForm = inits.isInitialized("sopDisclosureRequestForm") ? new com.cauh.iso.domain.report.QSOPDisclosureRequestForm(forProperty("sopDisclosureRequestForm"), inits.get("sopDisclosureRequestForm")) : null;
        this.sopRfRequestForm = inits.isInitialized("sopRfRequestForm") ? new com.cauh.iso.domain.report.QSopRdRequestForm(forProperty("sopRfRequestForm"), inits.get("sopRfRequestForm")) : null;
        this.sopWaiverApprovalForm = inits.isInitialized("sopWaiverApprovalForm") ? new com.cauh.iso.domain.report.QSOPWaiverApprovalForm(forProperty("sopWaiverApprovalForm"), inits.get("sopWaiverApprovalForm")) : null;
    }

}

