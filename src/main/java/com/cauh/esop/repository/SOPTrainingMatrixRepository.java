package com.cauh.esop.repository;

import com.cauh.esop.domain.SOPTrainingMatrix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SOPTrainingMatrixRepository extends JpaRepository<SOPTrainingMatrix, Integer>, QuerydslPredicateExecutor<SOPTrainingMatrix>, SOPTrainingMatrixRepositoryCustom {
}
