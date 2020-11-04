package com.dtnsm.esop.repository;

import com.dtnsm.esop.domain.Notice;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NoticeRepository extends PagingAndSortingRepository<Notice, Integer>, QuerydslPredicateExecutor<Notice> {
}
