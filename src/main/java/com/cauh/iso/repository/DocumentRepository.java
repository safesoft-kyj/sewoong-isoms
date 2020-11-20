package com.cauh.iso.repository;

import com.cauh.iso.domain.Document;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DocumentRepository extends PagingAndSortingRepository<Document, String>, QuerydslPredicateExecutor<Document> {
}
