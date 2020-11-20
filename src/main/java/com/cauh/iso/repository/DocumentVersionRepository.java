package com.cauh.iso.repository;

import com.cauh.iso.domain.DocumentVersion;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DocumentVersionRepository extends PagingAndSortingRepository<DocumentVersion, String>, QuerydslPredicateExecutor<DocumentVersion>
, DocumentVersionRepositoryCustomer {
}
