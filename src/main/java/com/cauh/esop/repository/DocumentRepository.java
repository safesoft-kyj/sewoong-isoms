package com.cauh.esop.repository;

import com.cauh.esop.domain.Document;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DocumentRepository extends PagingAndSortingRepository<Document, String>, QuerydslPredicateExecutor<Document> {
}
