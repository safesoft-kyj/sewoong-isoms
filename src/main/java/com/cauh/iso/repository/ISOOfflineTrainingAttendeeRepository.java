package com.cauh.iso.repository;

import com.cauh.iso.domain.ISOOfflineTrainingAttendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ISOOfflineTrainingAttendeeRepository extends JpaRepository<ISOOfflineTrainingAttendee, Integer>, QuerydslPredicateExecutor<ISOOfflineTrainingAttendee> {

}
