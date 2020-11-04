package com.dtnsm.esop.repository;

import com.dtnsm.common.entity.Account;
import com.dtnsm.common.entity.UserJobDescription;
import com.dtnsm.esop.domain.MyTraining;
import com.dtnsm.esop.domain.MyTrainingMatrix;
import com.dtnsm.esop.domain.constant.TrainingRequirement;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SOPTrainingMatrixRepositoryCustom {
    Page<MyTrainingMatrix> getMyTrainingMatrix(Pageable pageable, List<UserJobDescription> userJobDescriptions);

    List<MyTrainingMatrix> getMyTrainingMatrix(List<UserJobDescription> userJobDescriptions);

    Page<MyTraining> getMyTraining(TrainingRequirement requirement, Pageable pageable, Account user);

    Page<MyTraining> getTrainingList(String deptCode, String teamCode, Integer userId, String docId, Account user, Pageable pageable, BooleanBuilder docStatus);

    List<MyTraining> getDownloadTrainingList(String deptCode, String teamCode, Integer userId, String docId, Account user);
}
