package com.cauh.esop.repository;

import com.cauh.esop.domain.report.RequestedDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestedDocumentRepository extends JpaRepository<RequestedDocument, Integer> {
}
