package com.cauh.iso.xdocreport.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.cauh.iso.xdocreport.dto.QTrainingDeviationLogDTO is a Querydsl Projection type for TrainingDeviationLogDTO
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QTrainingDeviationLogDTO extends ConstructorExpression<TrainingDeviationLogDTO> {

    private static final long serialVersionUID = -887630286L;

    public QTrainingDeviationLogDTO(com.querydsl.core.types.Expression<? extends com.cauh.iso.domain.Approval> approval, com.querydsl.core.types.Expression<? extends java.util.Date> dueDate, com.querydsl.core.types.Expression<com.cauh.iso.domain.constant.TrainingStatus> trainingStatus, com.querydsl.core.types.Expression<? extends java.util.Date> completionDate) {
        super(TrainingDeviationLogDTO.class, new Class<?>[]{com.cauh.iso.domain.Approval.class, java.util.Date.class, com.cauh.iso.domain.constant.TrainingStatus.class, java.util.Date.class}, approval, dueDate, trainingStatus, completionDate);
    }

}

