package com.cauh.esop.repository;

import com.cauh.esop.domain.TrainingTestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TrainingTestLogRepository extends JpaRepository<TrainingTestLog, Integer>, QuerydslPredicateExecutor<TrainingTestLog> {
}
