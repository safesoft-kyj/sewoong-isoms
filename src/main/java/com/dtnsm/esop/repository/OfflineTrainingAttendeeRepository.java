package com.dtnsm.esop.repository;

import com.dtnsm.esop.domain.OfflineTrainingAttendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OfflineTrainingAttendeeRepository extends JpaRepository<OfflineTrainingAttendee, Integer>, QuerydslPredicateExecutor<OfflineTrainingAttendee> {
}
