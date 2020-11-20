package com.cauh.iso.repository;

import com.cauh.iso.domain.DocumentVersion;
import com.cauh.iso.domain.constant.DocumentStatus;

import java.util.List;

public interface DocumentVersionRepositoryCustomer {
    List<DocumentVersion> getSOPFoldersByStatus(DocumentStatus status, String categoryId);
}
