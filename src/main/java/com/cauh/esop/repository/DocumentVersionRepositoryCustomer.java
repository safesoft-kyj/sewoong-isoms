package com.cauh.esop.repository;

import com.cauh.esop.domain.DocumentVersion;
import com.cauh.esop.domain.constant.DocumentStatus;

import java.util.List;

public interface DocumentVersionRepositoryCustomer {
    List<DocumentVersion> getSOPFoldersByStatus(DocumentStatus status, String categoryId);
}
