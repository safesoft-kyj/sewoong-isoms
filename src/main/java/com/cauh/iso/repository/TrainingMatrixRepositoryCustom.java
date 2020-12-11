package com.cauh.iso.repository;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.JobDescription;
import com.cauh.common.entity.RoleAccount;
import com.cauh.common.entity.UserJobDescription;
import com.cauh.iso.domain.MyTraining;
import com.cauh.iso.domain.MyTrainingMatrix;
import com.cauh.iso.domain.constant.TrainingRequirement;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TrainingMatrixRepositoryCustom {
//    Page<MyTrainingMatrix> getMyTrainingMatrix(Pageable pageable, List<UserJobDescription> userJobDescriptions);
//
//    List<MyTrainingMatrix> getMyTrainingMatrix(List<UserJobDescription> userJobDescriptions);

    Page<MyTrainingMatrix> getMyTrainingMatrix(Pageable pageable, List<UserJobDescription> userJobDescriptions);

    List<MyTrainingMatrix> getMyTrainingMatrix(List<UserJobDescription> userJobDescriptions);

    Page<MyTraining> getMyTraining(TrainingRequirement requirement, Pageable pageable, Account user);

    Page<MyTraining> getTrainingList(String deptCode, String teamCode, Integer userId, String docId, Account user, Pageable pageable, BooleanBuilder docStatus);

    List<MyTraining> getDownloadTrainingList(String deptCode, String teamCode, Integer userId, String docId, Account user);
}
