package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNonDisclosureAgreement is a Querydsl query type for NonDisclosureAgreement
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QNonDisclosureAgreement extends EntityPathBase<NonDisclosureAgreement> {

    private static final long serialVersionUID = 327807297L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNonDisclosureAgreement nonDisclosureAgreement = new QNonDisclosureAgreement("nonDisclosureAgreement");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    public final StringPath base64signature = createString("base64signature");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final QDocumentVersion documentVersion;

    public final StringPath email = createString("email");

    public final com.cauh.iso.domain.report.QExternalCustomer externalCustomer;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public QNonDisclosureAgreement(String variable) {
        this(NonDisclosureAgreement.class, forVariable(variable), INITS);
    }

    public QNonDisclosureAgreement(Path<? extends NonDisclosureAgreement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNonDisclosureAgreement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNonDisclosureAgreement(PathMetadata metadata, PathInits inits) {
        this(NonDisclosureAgreement.class, metadata, inits);
    }

    public QNonDisclosureAgreement(Class<? extends NonDisclosureAgreement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.documentVersion = inits.isInitialized("documentVersion") ? new QDocumentVersion(forProperty("documentVersion"), inits.get("documentVersion")) : null;
        this.externalCustomer = inits.isInitialized("externalCustomer") ? new com.cauh.iso.domain.report.QExternalCustomer(forProperty("externalCustomer"), inits.get("externalCustomer")) : null;
    }

}

