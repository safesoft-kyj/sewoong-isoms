package com.cauh.iso.repository;

import com.cauh.iso.domain.ISO;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ISORepository extends PagingAndSortingRepository<ISO, String>, QuerydslPredicateExecutor<ISO> {
}
