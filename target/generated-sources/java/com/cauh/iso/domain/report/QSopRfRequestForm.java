package com.cauh.iso.domain.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSopRfRequestForm is a Querydsl query type for SopRfRequestForm
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSopRfRequestForm extends EntityPathBase<SopRfRequestForm> {

    private static final long serialVersionUID = -1677262572L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSopRfRequestForm sopRfRequestForm = new QSopRfRequestForm("sopRfRequestForm");

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

    public final BooleanPath newRFDevelopment = createBoolean("newRFDevelopment");

    public final BooleanPath newSOPDevelopment = createBoolean("newSOPDevelopment");

    public final StringPath reasonForTheRequest = createString("reasonForTheRequest");

    public final StringPath requestedByComments = createString("requestedByComments");

    public final ListPath<SopRfDevelopmentDoc, QSopRfDevelopmentDoc> rfDevelopmentDocs = this.<SopRfDevelopmentDoc, QSopRfDevelopmentDoc>createList("rfDevelopmentDocs", SopRfDevelopmentDoc.class, QSopRfDevelopmentDoc.class, PathInits.DIRECT2);

    public final BooleanPath rfRevision = createBoolean("rfRevision");

    public final ListPath<SopRfRevisionDoc, QSopRfRevisionDoc> rfRevisionDocs = this.<SopRfRevisionDoc, QSopRfRevisionDoc>createList("rfRevisionDocs", SopRfRevisionDoc.class, QSopRfRevisionDoc.class, PathInits.DIRECT2);

    public final ListPath<SopRfDevelopmentDoc, QSopRfDevelopmentDoc> sopDevelopmentDocs = this.<SopRfDevelopmentDoc, QSopRfDevelopmentDoc>createList("sopDevelopmentDocs", SopRfDevelopmentDoc.class, QSopRfDevelopmentDoc.class, PathInits.DIRECT2);

    public final BooleanPath sopRevision = createBoolean("sopRevision");

    public final ListPath<SopRfRevisionDoc, QSopRfRevisionDoc> sopRevisionDocs = this.<SopRfRevisionDoc, QSopRfRevisionDoc>createList("sopRevisionDocs", SopRfRevisionDoc.class, QSopRfRevisionDoc.class, PathInits.DIRECT2);

    public QSopRfRequestForm(String variable) {
        this(SopRfRequestForm.class, forVariable(variable), INITS);
    }

    public QSopRfRequestForm(Path<? extends SopRfRequestForm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSopRfRequestForm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSopRfRequestForm(PathMetadata metadata, PathInits inits) {
        this(SopRfRequestForm.class, metadata, inits);
    }

    public QSopRfRequestForm(Class<? extends SopRfRequestForm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.approval = inits.isInitialized("approval") ? new com.cauh.iso.domain.QApproval(forProperty("approval"), inits.get("approval")) : null;
    }

}

