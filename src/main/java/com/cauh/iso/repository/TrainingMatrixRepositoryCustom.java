package com.cauh.iso.repository;

import com.cauh.common.entity.*;
import com.cauh.iso.domain.MyTraining;
import com.cauh.iso.domain.MyTrainingMatrix;
import com.cauh.iso.domain.constant.DocumentType;
import com.cauh.iso.domain.constant.ISOType;
import com.cauh.iso.domain.constant.TrainingRequirement;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TrainingMatrixRepositoryCustom {

    //SOP Training
    Page<MyTrainingMatrix> getMyTrainingMatrix(Pageable pageable, List<UserJobDescription> userJobDescriptions);
    List<MyTrainingMatrix> getMyTrainingMatrix(List<UserJobDescription> userJobDescriptions);

    Page<MyTraining> getMyTraining(TrainingRequirement requirement, Pageable pageable, Account user);
    Page<MyTraining> getTrainingList(Department department, Integer userId, String docId, Account user, Pageable pageable, BooleanBuilder docStatus, BooleanBuilder completeStatus);
    List<MyTraining> getDownloadTrainingList(Department department, Integer userId, String docId, Account user, BooleanBuilder completeStatus);


    //ISO Training
    Page<MyTraining> getISOMyTraining(Pageable pageable, Account user);
    Page<MyTraining> getISOTrainingList(Department department, Integer userId, ISOType isoType, Pageable pageable, BooleanBuilder completeStatus);
    List<MyTraining> getDownloadISOTrainingList(Department department, Integer userId, ISOType isoType, BooleanBuilder completeStatus);
}
