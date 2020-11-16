package com.cauh.esop.repository;

import com.cauh.esop.domain.report.RetirementDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RetirementDocumentRepository extends JpaRepository<RetirementDocument, Integer>, QuerydslPredicateExecutor<RetirementDocument> {
}
