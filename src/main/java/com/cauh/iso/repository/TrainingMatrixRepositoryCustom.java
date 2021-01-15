package com.cauh.iso.repository;

import com.cauh.common.entity.*;
import com.cauh.iso.domain.MyTraining;
import com.cauh.iso.domain.MyTrainingMatrix;
import com.cauh.iso.domain.constant.DocumentType;
import com.cauh.iso.domain.constant.TrainingRequirement;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TrainingMatrixRepositoryCustom {
//    Page<MyTrainingMatrix> getMyTrainingMatrix(Pageable pageable, List<UserJobDescription> userJobDescriptions);
//
//    List<MyTrainingMatrix> getMyTrainingMatrix(List<UserJobDescription> userJobDescriptions);

    //Page<MyTrainingMatrix> getMyTrainingMatrix(Pageable pageable, List<UserJobDescription> userJobDescriptions);
    Page<MyTrainingMatrix> getMyTrainingMatrix(Pageable pageable, DocumentType documentType, List<UserJobDescription> userJobDescriptions);

    List<MyTrainingMatrix> getMyTrainingMatrix(List<UserJobDescription> userJobDescriptions);

    //Page<MyTraining> getMyTraining(TrainingRequirement requirement,Pageable pageable, Account user);
    Page<MyTraining> getMyTraining(TrainingRequirement requirement, DocumentType documentType, Pageable pageable, Account user);

    Page<MyTraining> getTrainingList(Department department, Integer userId, String docId, Account user, Pageable pageable, BooleanBuilder docStatus);

    List<MyTraining> getDownloadTrainingList(Department department, Integer userId, String docId, Account user, DocumentType documentType);
}
