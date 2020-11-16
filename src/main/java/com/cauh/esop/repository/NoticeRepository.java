package com.cauh.esop.repository;

import com.cauh.esop.domain.Notice;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NoticeRepository extends PagingAndSortingRepository<Notice, Integer>, QuerydslPredicateExecutor<Notice> {
}
