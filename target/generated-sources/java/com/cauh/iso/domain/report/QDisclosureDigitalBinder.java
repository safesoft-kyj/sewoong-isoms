package com.cauh.iso.domain.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDisclosureDigitalBinder is a Querydsl query type for DisclosureDigitalBinder
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDisclosureDigitalBinder extends EntityPathBase<DisclosureDigitalBinder> {

    private static final long serialVersionUID = -2018884692L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDisclosureDigitalBinder disclosureDigitalBinder = new QDisclosureDigitalBinder("disclosureDigitalBinder");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final QSOPDisclosureRequestForm sopDisclosureRequestForm;

    public final com.cauh.common.entity.QAccount user;

    public QDisclosureDigitalBinder(String variable) {
        this(DisclosureDigitalBinder.class, forVariable(variable), INITS);
    }

    public QDisclosureDigitalBinder(Path<? extends DisclosureDigitalBinder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDisclosureDigitalBinder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDisclosureDigitalBinder(PathMetadata metadata, PathInits inits) {
        this(DisclosureDigitalBinder.class, metadata, inits);
    }

    public QDisclosureDigitalBinder(Class<? extends DisclosureDigitalBinder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sopDisclosureRequestForm = inits.isInitialized("sopDisclosureRequestForm") ? new QSOPDisclosureRequestForm(forProperty("sopDisclosureRequestForm"), inits.get("sopDisclosureRequestForm")) : null;
        this.user = inits.isInitialized("user") ? new com.cauh.common.entity.QAccount(forProperty("user"), inits.get("user")) : null;
    }

}

