package com.cauh.iso.repository;

import com.cauh.iso.domain.ISOOfflineTraining;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ISOOfflineTrainingRepository extends JpaRepository<ISOOfflineTraining, Integer>, QuerydslPredicateExecutor<ISOOfflineTraining> {

}
