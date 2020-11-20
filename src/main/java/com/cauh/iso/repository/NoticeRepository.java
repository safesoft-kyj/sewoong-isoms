package com.cauh.iso.repository;

import com.cauh.iso.domain.Notice;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface NoticeRepository extends PagingAndSortingRepository<Notice, Integer>, QuerydslPredicateExecutor<Notice>, RevisionRepository<Notice, Integer, Integer> {
}
