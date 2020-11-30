package com.cauh.iso.repository;

import com.cauh.iso.domain.TrainingMatrix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SOPTrainingMatrixRepository extends JpaRepository<TrainingMatrix, Integer>, QuerydslPredicateExecutor<TrainingMatrix>, SOPTrainingMatrixRepositoryCustom {
}
