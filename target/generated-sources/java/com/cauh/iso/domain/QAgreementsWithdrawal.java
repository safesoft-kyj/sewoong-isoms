package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAgreementsWithdrawal is a Querydsl query type for AgreementsWithdrawal
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAgreementsWithdrawal extends EntityPathBase<AgreementsWithdrawal> {

    private static final long serialVersionUID = 77858863L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAgreementsWithdrawal agreementsWithdrawal = new QAgreementsWithdrawal("agreementsWithdrawal");

    public final com.cauh.common.entity.QBaseEntity _super = new com.cauh.common.entity.QBaseEntity(this);

    public final BooleanPath apply = createBoolean("apply");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdDate = _super.createdDate;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastModifiedDate = _super.lastModifiedDate;

    public final com.cauh.common.entity.QAccount user;

    public final DateTimePath<java.util.Date> withdrawalDate = createDateTime("withdrawalDate", java.util.Date.class);

    public QAgreementsWithdrawal(String variable) {
        this(AgreementsWithdrawal.class, forVariable(variable), INITS);
    }

    public QAgreementsWithdrawal(Path<? extends AgreementsWithdrawal> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAgreementsWithdrawal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAgreementsWithdrawal(PathMetadata metadata, PathInits inits) {
        this(AgreementsWithdrawal.class, metadata, inits);
    }

    public QAgreementsWithdrawal(Class<? extends AgreementsWithdrawal> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.cauh.common.entity.QAccount(forProperty("user"), inits.get("user")) : null;
    }

}

