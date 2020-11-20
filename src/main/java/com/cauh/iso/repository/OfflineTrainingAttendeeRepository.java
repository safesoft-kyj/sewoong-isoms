package com.cauh.iso.repository;

import com.cauh.iso.domain.OfflineTrainingAttendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OfflineTrainingAttendeeRepository extends JpaRepository<OfflineTrainingAttendee, Integer>, QuerydslPredicateExecutor<OfflineTrainingAttendee> {
}
