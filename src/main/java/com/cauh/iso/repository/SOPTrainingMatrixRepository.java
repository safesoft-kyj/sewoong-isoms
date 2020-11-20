package com.cauh.iso.repository;

import com.cauh.iso.domain.SOPTrainingMatrix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SOPTrainingMatrixRepository extends JpaRepository<SOPTrainingMatrix, Integer>, QuerydslPredicateExecutor<SOPTrainingMatrix>, SOPTrainingMatrixRepositoryCustom {
}
