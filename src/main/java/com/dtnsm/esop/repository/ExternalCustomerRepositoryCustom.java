package com.dtnsm.esop.repository;

import com.dtnsm.esop.domain.DisclosureSOP;
import com.dtnsm.esop.domain.constant.DocumentStatus;

import java.util.List;

public interface ExternalCustomerRepositoryCustom {
    List<DisclosureSOP> getDocumentList(Integer requestFormId, DocumentStatus status, String categoryId, String sopId);
}
