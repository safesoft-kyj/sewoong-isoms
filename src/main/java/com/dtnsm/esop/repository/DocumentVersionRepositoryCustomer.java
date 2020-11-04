package com.dtnsm.esop.repository;

import com.dtnsm.esop.domain.DocumentVersion;
import com.dtnsm.esop.domain.constant.DocumentStatus;

import java.util.List;

public interface DocumentVersionRepositoryCustomer {
    List<DocumentVersion> getSOPFoldersByStatus(DocumentStatus status, String categoryId);
}
