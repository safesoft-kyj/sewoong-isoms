package com.cauh.esop.repository;

import com.cauh.esop.domain.OfflineTrainingDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OfflineTrainingDocumentRepository extends JpaRepository<OfflineTrainingDocument, Integer>, QuerydslPredicateExecutor<OfflineTrainingDocument> {
}
