package com.dtnsm.esop.repository;

import com.dtnsm.esop.domain.Document;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DocumentRepository extends PagingAndSortingRepository<Document, String>, QuerydslPredicateExecutor<Document> {
}
