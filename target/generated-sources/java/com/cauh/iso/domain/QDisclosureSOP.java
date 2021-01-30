package com.cauh.iso.domain;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.cauh.iso.domain.QDisclosureSOP is a Querydsl Projection type for DisclosureSOP
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QDisclosureSOP extends ConstructorExpression<DisclosureSOP> {

    private static final long serialVersionUID = -1705053546L;

    public QDisclosureSOP(com.querydsl.core.types.Expression<? extends DocumentVersion> documentVersion, com.querydsl.core.types.Expression<? extends Document> document, com.querydsl.core.types.Expression<? extends Document> sopDocument) {
        super(DisclosureSOP.class, new Class<?>[]{DocumentVersion.class, Document.class, Document.class}, documentVersion, document, sopDocument);
    }

}

