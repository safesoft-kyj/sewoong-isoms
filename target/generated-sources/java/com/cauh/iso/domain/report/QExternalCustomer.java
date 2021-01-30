package com.cauh.iso.domain.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExternalCustomer is a Querydsl query type for ExternalCustomer
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QExternalCustomer extends EntityPathBase<ExternalCustomer> {

    private static final long serialVersionUID = -1656019470L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExternalCustomer externalCustomer = new QExternalCustomer("externalCustomer");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final StringPath email = createString("email");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath jobTitle = createString("jobTitle");

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final QSOPDisclosureRequestForm sopDisclosureRequestForm;

    public QExternalCustomer(String variable) {
        this(ExternalCustomer.class, forVariable(variable), INITS);
    }

    public QExternalCustomer(Path<? extends ExternalCustomer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExternalCustomer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExternalCustomer(PathMetadata metadata, PathInits inits) {
        this(ExternalCustomer.class, metadata, inits);
    }

    public QExternalCustomer(Class<? extends ExternalCustomer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sopDisclosureRequestForm = inits.isInitialized("sopDisclosureRequestForm") ? new QSOPDisclosureRequestForm(forProperty("sopDisclosureRequestForm"), inits.get("sopDisclosureRequestForm")) : null;
    }

}

