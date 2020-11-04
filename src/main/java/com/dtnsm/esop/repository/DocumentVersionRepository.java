package com.dtnsm.esop.repository;

import com.dtnsm.esop.domain.DocumentVersion;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DocumentVersionRepository extends PagingAndSortingRepository<DocumentVersion, String>, QuerydslPredicateExecutor<DocumentVersion>
, DocumentVersionRepositoryCustomer {
}
