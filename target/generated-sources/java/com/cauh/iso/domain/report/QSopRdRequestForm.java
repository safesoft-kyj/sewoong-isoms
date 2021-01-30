package com.cauh.iso.domain.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSopRdRequestForm is a Querydsl query type for SopRdRequestForm
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSopRdRequestForm extends EntityPathBase<SopRdRequestForm> {

    private static final long serialVersionUID = -1935428010L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSopRdRequestForm sopRdRequestForm = new QSopRdRequestForm("sopRdRequestForm");

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

    public final StringPath nameOfRequester = createString("nameOfRequester");

    public final StringPath nameOfTeamDept = createString("nameOfTeamDept");

    public final BooleanPath newRDDevelopment = createBoolean("newRDDevelopment");

    public final BooleanPath newSOPDevelopment = createBoolean("newSOPDevelopment");

    public final ListPath<SopRdDevelopmentDoc, QSopRdDevelopmentDoc> rdDevelopmentDocs = this.<SopRdDevelopmentDoc, QSopRdDevelopmentDoc>createList("rdDevelopmentDocs", SopRdDevelopmentDoc.class, QSopRdDevelopmentDoc.class, PathInits.DIRECT2);

    public final BooleanPath rdRevision = createBoolean("rdRevision");

    public final ListPath<SopRdRevisionDoc, QSopRdRevisionDoc> rdRevisionDocs = this.<SopRdRevisionDoc, QSopRdRevisionDoc>createList("rdRevisionDocs", SopRdRevisionDoc.class, QSopRdRevisionDoc.class, PathInits.DIRECT2);

    public final StringPath reasonForTheRequest = createString("reasonForTheRequest");

    public final StringPath requestedByComments = createString("requestedByComments");

    public final ListPath<SopRdDevelopmentDoc, QSopRdDevelopmentDoc> sopDevelopmentDocs = this.<SopRdDevelopmentDoc, QSopRdDevelopmentDoc>createList("sopDevelopmentDocs", SopRdDevelopmentDoc.class, QSopRdDevelopmentDoc.class, PathInits.DIRECT2);

    public final BooleanPath sopRevision = createBoolean("sopRevision");

    public final ListPath<SopRdRevisionDoc, QSopRdRevisionDoc> sopRevisionDocs = this.<SopRdRevisionDoc, QSopRdRevisionDoc>createList("sopRevisionDocs", SopRdRevisionDoc.class, QSopRdRevisionDoc.class, PathInits.DIRECT2);

    public QSopRdRequestForm(String variable) {
        this(SopRdRequestForm.class, forVariable(variable), INITS);
    }

    public QSopRdRequestForm(Path<? extends SopRdRequestForm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSopRdRequestForm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSopRdRequestForm(PathMetadata metadata, PathInits inits) {
        this(SopRdRequestForm.class, metadata, inits);
    }

    public QSopRdRequestForm(Class<? extends SopRdRequestForm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.approval = inits.isInitialized("approval") ? new com.cauh.iso.domain.QApproval(forProperty("approval"), inits.get("approval")) : null;
    }

}

