package com.dtnsm.esop.repository;

import com.dtnsm.esop.domain.OfflineTrainingDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OfflineTrainingDocumentRepository extends JpaRepository<OfflineTrainingDocument, Integer>, QuerydslPredicateExecutor<OfflineTrainingDocument> {
}
