package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAgreementPersonalInformation is a Querydsl query type for AgreementPersonalInformation
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAgreementPersonalInformation extends EntityPathBase<AgreementPersonalInformation> {

    private static final long serialVersionUID = 1578705491L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAgreementPersonalInformation agreementPersonalInformation = new QAgreementPersonalInformation("agreementPersonalInformation");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    public final BooleanPath agree = createBoolean("agree");

    public final StringPath base64signature = createString("base64signature");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final QDocumentVersion documentVersion;

    public final StringPath email = createString("email");

    public final com.cauh.iso.domain.report.QExternalCustomer externalCustomer;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final com.cauh.common.entity.QAccount internalUser;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public QAgreementPersonalInformation(String variable) {
        this(AgreementPersonalInformation.class, forVariable(variable), INITS);
    }

    public QAgreementPersonalInformation(Path<? extends AgreementPersonalInformation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAgreementPersonalInformation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAgreementPersonalInformation(PathMetadata metadata, PathInits inits) {
        this(AgreementPersonalInformation.class, metadata, inits);
    }

    public QAgreementPersonalInformation(Class<? extends AgreementPersonalInformation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.documentVersion = inits.isInitialized("documentVersion") ? new QDocumentVersion(forProperty("documentVersion"), inits.get("documentVersion")) : null;
        this.externalCustomer = inits.isInitialized("externalCustomer") ? new com.cauh.iso.domain.report.QExternalCustomer(forProperty("externalCustomer"), inits.get("externalCustomer")) : null;
        this.internalUser = inits.isInitialized("internalUser") ? new com.cauh.common.entity.QAccount(forProperty("internalUser"), inits.get("internalUser")) : null;
    }

}

