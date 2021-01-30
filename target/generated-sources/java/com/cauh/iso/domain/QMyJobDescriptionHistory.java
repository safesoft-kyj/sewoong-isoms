package com.cauh.iso.domain;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.cauh.iso.domain.QMyJobDescriptionHistory is a Querydsl Projection type for MyJobDescriptionHistory
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QMyJobDescriptionHistory extends ConstructorExpression<MyJobDescriptionHistory> {

    private static final long serialVersionUID = 674405560L;

    public QMyJobDescriptionHistory(com.querydsl.core.types.Expression<? extends com.cauh.common.entity.Account> user, com.querydsl.core.types.Expression<? extends java.util.List<com.cauh.common.entity.UserJobDescription>> prevJobDescriptions, com.querydsl.core.types.Expression<? extends java.util.List<com.cauh.common.entity.UserJobDescription>> nextJobDescriptions) {
        super(MyJobDescriptionHistory.class, new Class<?>[]{com.cauh.common.entity.Account.class, java.util.List.class, java.util.List.class}, user, prevJobDescriptions, nextJobDescriptions);
    }

}

