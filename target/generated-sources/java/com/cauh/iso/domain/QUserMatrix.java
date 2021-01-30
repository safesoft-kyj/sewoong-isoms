package com.cauh.iso.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserMatrix is a Querydsl query type for UserMatrix
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserMatrix extends EntityPathBase<UserMatrix> {

    private static final long serialVersionUID = -647400547L;

    public static final QUserMatrix userMatrix = new QUserMatrix("userMatrix");

    public final NumberPath<Integer> trainingPeriodId = createNumber("trainingPeriodId", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QUserMatrix(String variable) {
        super(UserMatrix.class, forVariable(variable));
    }

    public QUserMatrix(Path<? extends UserMatrix> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserMatrix(PathMetadata metadata) {
        super(UserMatrix.class, metadata);
    }

}

