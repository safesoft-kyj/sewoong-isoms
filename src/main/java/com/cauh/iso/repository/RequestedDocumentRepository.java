package com.cauh.iso.repository;

import com.cauh.iso.domain.report.RequestedDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestedDocumentRepository extends JpaRepository<RequestedDocument, Integer> {
}
