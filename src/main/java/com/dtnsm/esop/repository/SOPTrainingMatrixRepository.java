package com.dtnsm.esop.repository;

import com.dtnsm.esop.domain.SOPTrainingMatrix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SOPTrainingMatrixRepository extends JpaRepository<SOPTrainingMatrix, Integer>, QuerydslPredicateExecutor<SOPTrainingMatrix>, SOPTrainingMatrixRepositoryCustom {
}
