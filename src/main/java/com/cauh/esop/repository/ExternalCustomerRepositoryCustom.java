package com.cauh.esop.repository;

import com.cauh.esop.domain.DisclosureSOP;
import com.cauh.esop.domain.constant.DocumentStatus;

import java.util.List;

public interface ExternalCustomerRepositoryCustom {
    List<DisclosureSOP> getDocumentList(Integer requestFormId, DocumentStatus status, String categoryId, String sopId);
}
