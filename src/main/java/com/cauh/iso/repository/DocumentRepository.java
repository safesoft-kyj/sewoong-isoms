package com.cauh.iso.repository;

import com.cauh.common.entity.Account;
import com.cauh.iso.domain.Document;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface DocumentRepository extends PagingAndSortingRepository<Document, String>, QuerydslPredicateExecutor<Document>, RevisionRepository<Document, String, Long> {
}
