package com.cauh.iso.repository;

import com.cauh.iso.domain.OfflineTrainingDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OfflineTrainingDocumentRepository extends JpaRepository<OfflineTrainingDocument, Integer>, QuerydslPredicateExecutor<OfflineTrainingDocument> {
}
