package com.dtnsm.esop.repository;

import com.dtnsm.esop.domain.report.RetirementDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RetirementDocumentRepository extends JpaRepository<RetirementDocument, Integer>, QuerydslPredicateExecutor<RetirementDocument> {
}
