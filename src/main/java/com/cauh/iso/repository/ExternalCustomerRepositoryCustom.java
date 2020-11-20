package com.cauh.iso.repository;

import com.cauh.iso.domain.DisclosureSOP;
import com.cauh.iso.domain.constant.DocumentStatus;

import java.util.List;

public interface ExternalCustomerRepositoryCustom {
    List<DisclosureSOP> getDocumentList(Integer requestFormId, DocumentStatus status, String categoryId, String sopId);
}
