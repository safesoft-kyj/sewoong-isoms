package com.cauh.iso.domain;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.cauh.iso.domain.QMyTrainingMatrix is a Querydsl Projection type for MyTrainingMatrix
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QMyTrainingMatrix extends ConstructorExpression<MyTrainingMatrix> {

    private static final long serialVersionUID = -1320608392L;

    public QMyTrainingMatrix(com.querydsl.core.types.Expression<? extends Document> document, com.querydsl.core.types.Expression<? extends DocumentVersion> documentVersion) {
        super(MyTrainingMatrix.class, new Class<?>[]{Document.class, DocumentVersion.class}, document, documentVersion);
    }

}

