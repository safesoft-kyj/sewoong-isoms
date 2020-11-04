package com.dtnsm.esop.repository;

import com.dtnsm.esop.domain.report.RequestedDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestedDocumentRepository extends JpaRepository<RequestedDocument, Integer> {
}
