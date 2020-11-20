package com.cauh.iso.repository;

import com.cauh.iso.domain.TrainingTestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TrainingTestLogRepository extends JpaRepository<TrainingTestLog, Integer>, QuerydslPredicateExecutor<TrainingTestLog> {
}
