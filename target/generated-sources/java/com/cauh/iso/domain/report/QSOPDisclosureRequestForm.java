package com.cauh.iso.domain.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSOPDisclosureRequestForm is a Querydsl query type for SOPDisclosureRequestForm
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSOPDisclosureRequestForm extends EntityPathBase<SOPDisclosureRequestForm> {

    private static final long serialVersionUID = 2041185979L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSOPDisclosureRequestForm sOPDisclosureRequestForm = new QSOPDisclosureRequestForm("sOPDisclosureRequestForm");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    public final com.cauh.iso.domain.QApproval approval;

    public final StringPath companyNameOrInstituteName = createString("companyNameOrInstituteName");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final ListPath<DisclosureDigitalBinder, QDisclosureDigitalBinder> disclosureDigitalBinders = this.<DisclosureDigitalBinder, QDisclosureDigitalBinder>createList("disclosureDigitalBinders", DisclosureDigitalBinder.class, QDisclosureDigitalBinder.class, PathInits.DIRECT2);

    public final EnumPath<DocumentAccess> documentAccess = createEnum("documentAccess", DocumentAccess.class);

    public final StringPath documentAccessOther = createString("documentAccessOther");

    public final ListPath<ExternalCustomer, QExternalCustomer> externalCustomers = this.<ExternalCustomer, QExternalCustomer>createList("externalCustomers", ExternalCustomer.class, QExternalCustomer.class, PathInits.DIRECT2);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath nameOfRequester = createString("nameOfRequester");

    public final StringPath projectOrTeamManager = createString("projectOrTeamManager");

    public final StringPath protocolTitleOrNo = createString("protocolTitleOrNo");

    public final EnumPath<PurposeOfDisclosure> purposeOfDisclosure = createEnum("purposeOfDisclosure", PurposeOfDisclosure.class);

    public final StringPath purposeOfDisclosureOther = createString("purposeOfDisclosureOther");

    public final ListPath<RequestedDocument, QRequestedDocument> requestedDocumentRDs = this.<RequestedDocument, QRequestedDocument>createList("requestedDocumentRDs", RequestedDocument.class, QRequestedDocument.class, PathInits.DIRECT2);

    public final ListPath<RequestedDocument, QRequestedDocument> requestedDocumentSOPs = this.<RequestedDocument, QRequestedDocument>createList("requestedDocumentSOPs", RequestedDocument.class, QRequestedDocument.class, PathInits.DIRECT2);

    public final DateTimePath<java.util.Date> requestEndDate = createDateTime("requestEndDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> requestStartDate = createDateTime("requestStartDate", java.util.Date.class);

    public final StringPath teamDept = createString("teamDept");

    public QSOPDisclosureRequestForm(String variable) {
        this(SOPDisclosureRequestForm.class, forVariable(variable), INITS);
    }

    public QSOPDisclosureRequestForm(Path<? extends SOPDisclosureRequestForm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSOPDisclosureRequestForm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSOPDisclosureRequestForm(PathMetadata metadata, PathInits inits) {
        this(SOPDisclosureRequestForm.class, metadata, inits);
    }

    public QSOPDisclosureRequestForm(Class<? extends SOPDisclosureRequestForm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.approval = inits.isInitialized("approval") ? new com.cauh.iso.domain.QApproval(forProperty("approval"), inits.get("approval")) : null;
    }

}

