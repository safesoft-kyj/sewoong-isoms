package com.cauh.iso.domain;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

import com.querydsl.core.types.Expression;

/**
 * com.cauh.iso.domain.QMyTraining is a Querydsl Projection type for MyTraining
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QMyTraining extends ConstructorExpression<MyTraining> {

    private static final long serialVersionUID = -313305385L;

    public QMyTraining(Expression<? extends Document> document, Expression<? extends DocumentVersion> documentVersion, Expression<? extends TrainingPeriod> trainingPeriod, Expression<? extends TrainingLog> trainingLog, Expression<? extends java.util.Date> startDate, Expression<? extends java.util.Date> endDate, Expression<? extends java.util.Date> inDate) {
        super(MyTraining.class, new Class<?>[]{Document.class, DocumentVersion.class, TrainingPeriod.class, TrainingLog.class, java.util.Date.class, java.util.Date.class, java.util.Date.class}, document, documentVersion, trainingPeriod, trainingLog, startDate, endDate, inDate);
    }

    public QMyTraining(Expression<? extends ISO> iso, Expression<? extends ISOAttachFile> isoAttachFile, Expression<? extends ISOTrainingPeriod> isoTrainingPeriod, Expression<? extends ISOTrainingLog> isoTrainingLog, Expression<? extends java.util.Date> startDate, Expression<? extends java.util.Date> endDate) {
        super(MyTraining.class, new Class<?>[]{ISO.class, ISOAttachFile.class, ISOTrainingPeriod.class, ISOTrainingLog.class, java.util.Date.class, java.util.Date.class}, iso, isoAttachFile, isoTrainingPeriod, isoTrainingLog, startDate, endDate);
    }

    public QMyTraining(Expression<? extends Document> document, Expression<? extends DocumentVersion> documentVersion, Expression<? extends TrainingPeriod> trainingPeriod, Expression<? extends TrainingLog> trainingLog) {
        super(MyTraining.class, new Class<?>[]{Document.class, DocumentVersion.class, TrainingPeriod.class, TrainingLog.class}, document, documentVersion, trainingPeriod, trainingLog);
    }

    public QMyTraining(Expression<? extends com.cauh.common.entity.Account> user, Expression<? extends Document> document, Expression<? extends DocumentVersion> documentVersion, Expression<? extends TrainingPeriod> trainingPeriod, Expression<? extends TrainingLog> trainingLog) {
        super(MyTraining.class, new Class<?>[]{com.cauh.common.entity.Account.class, Document.class, DocumentVersion.class, TrainingPeriod.class, TrainingLog.class}, user, document, documentVersion, trainingPeriod, trainingLog);
    }

    public QMyTraining(com.cauh.common.entity.QAccount user, QDocument document, QDocumentVersion documentVersion, QTrainingPeriod trainingPeriod, QTrainingLog trainingLog, TemporalExpression<? extends java.util.Date> startDate, TemporalExpression<? extends java.util.Date> endDate) {
        super(MyTraining.class, new Class<?>[]{com.cauh.common.entity.Account.class, Document.class, DocumentVersion.class, TrainingPeriod.class, TrainingLog.class, java.util.Date.class, java.util.Date.class}, user, document, documentVersion, trainingPeriod, trainingLog, startDate, endDate);
    }

    public QMyTraining(com.cauh.common.entity.QAccount user, QISO iso, QISOTrainingPeriod isoTrainingPeriod, QISOTrainingLog isoTrainingLog, TemporalExpression<? extends java.util.Date> startDate, TemporalExpression<? extends java.util.Date> endDate) {
        super(MyTraining.class, new Class<?>[]{com.cauh.common.entity.Account.class, ISO.class, ISOTrainingPeriod.class, ISOTrainingLog.class, java.util.Date.class, java.util.Date.class}, user, iso, isoTrainingPeriod, isoTrainingLog, startDate, endDate);
    }

    public QMyTraining(Expression<? extends com.cauh.common.entity.Account> user, Expression<? extends Document> document, Expression<? extends DocumentVersion> documentVersion, Expression<? extends TrainingPeriod> trainingPeriod, Expression<? extends TrainingLog> trainingLog, Expression<? extends java.util.Date> startDate, Expression<? extends java.util.Date> endDate, Expression<? extends java.util.Date> jobAssignDate) {
        super(MyTraining.class, new Class<?>[]{com.cauh.common.entity.Account.class, Document.class, DocumentVersion.class, TrainingPeriod.class, TrainingLog.class, java.util.Date.class, java.util.Date.class, java.util.Date.class}, user, document, documentVersion, trainingPeriod, trainingLog, startDate, endDate, jobAssignDate);
    }

    public QMyTraining(QDocument document, QDocumentVersion documentVersion, QTrainingPeriod trainingPeriod, QTrainingLog trainingLog, TemporalExpression<? extends java.util.Date> startDate, TemporalExpression<? extends java.util.Date> endDate) {
        super(MyTraining.class, new Class<?>[]{Document.class, DocumentVersion.class, TrainingPeriod.class, TrainingLog.class, java.util.Date.class, java.util.Date.class}, document, documentVersion, trainingPeriod, trainingLog, startDate, endDate);
    }

}

