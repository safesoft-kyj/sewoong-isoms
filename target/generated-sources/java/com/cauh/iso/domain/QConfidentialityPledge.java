package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConfidentialityPledge is a Querydsl query type for ConfidentialityPledge
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QConfidentialityPledge extends EntityPathBase<ConfidentialityPledge> {

    private static final long serialVersionUID = -965359954L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConfidentialityPledge confidentialityPledge = new QConfidentialityPledge("confidentialityPledge");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    public final BooleanPath agree = createBoolean("agree");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final QDocumentVersion documentVersion;

    public final StringPath email = createString("email");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final com.cauh.common.entity.QAccount internalUser;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public QConfidentialityPledge(String variable) {
        this(ConfidentialityPledge.class, forVariable(variable), INITS);
    }

    public QConfidentialityPledge(Path<? extends ConfidentialityPledge> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConfidentialityPledge(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConfidentialityPledge(PathMetadata metadata, PathInits inits) {
        this(ConfidentialityPledge.class, metadata, inits);
    }

    public QConfidentialityPledge(Class<? extends ConfidentialityPledge> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.documentVersion = inits.isInitialized("documentVersion") ? new QDocumentVersion(forProperty("documentVersion"), inits.get("documentVersion")) : null;
        this.internalUser = inits.isInitialized("internalUser") ? new com.cauh.common.entity.QAccount(forProperty("internalUser"), inits.get("internalUser")) : null;
    }

}

