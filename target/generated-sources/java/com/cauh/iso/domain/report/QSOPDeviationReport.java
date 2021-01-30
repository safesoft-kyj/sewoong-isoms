package com.cauh.iso.domain.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSOPDeviationReport is a Querydsl query type for SOPDeviationReport
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSOPDeviationReport extends EntityPathBase<SOPDeviationReport> {

    private static final long serialVersionUID = -1851201590L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSOPDeviationReport sOPDeviationReport = new QSOPDeviationReport("sOPDeviationReport");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    public final com.cauh.iso.domain.QApproval approval;

    public final StringPath correctiveAction = createString("correctiveAction");

    public final DateTimePath<java.util.Date> correctiveCompletionDate = createDateTime("correctiveCompletionDate", java.util.Date.class);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final DateTimePath<java.util.Date> dateOfDiscovery = createDateTime("dateOfDiscovery", java.util.Date.class);

    public final DateTimePath<java.util.Date> dateOfOccurrence = createDateTime("dateOfOccurrence", java.util.Date.class);

    public final com.cauh.iso.domain.QDocumentVersion deviatedSOPDocument;

    public final StringPath deviationDetails = createString("deviationDetails");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath preventiveAction = createString("preventiveAction");

    public final DateTimePath<java.util.Date> preventiveCompletionDate = createDateTime("preventiveCompletionDate", java.util.Date.class);

    public final StringPath projectNo = createString("projectNo");

    public final StringPath protocolNo = createString("protocolNo");

    public final NumberPath<Integer> trainingLogId = createNumber("trainingLogId", Integer.class);

    public final NumberPath<Integer> trainingPeriodId = createNumber("trainingPeriodId", Integer.class);

    public QSOPDeviationReport(String variable) {
        this(SOPDeviationReport.class, forVariable(variable), INITS);
    }

    public QSOPDeviationReport(Path<? extends SOPDeviationReport> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSOPDeviationReport(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSOPDeviationReport(PathMetadata metadata, PathInits inits) {
        this(SOPDeviationReport.class, metadata, inits);
    }

    public QSOPDeviationReport(Class<? extends SOPDeviationReport> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.approval = inits.isInitialized("approval") ? new com.cauh.iso.domain.QApproval(forProperty("approval"), inits.get("approval")) : null;
        this.deviatedSOPDocument = inits.isInitialized("deviatedSOPDocument") ? new com.cauh.iso.domain.QDocumentVersion(forProperty("deviatedSOPDocument"), inits.get("deviatedSOPDocument")) : null;
    }

}

