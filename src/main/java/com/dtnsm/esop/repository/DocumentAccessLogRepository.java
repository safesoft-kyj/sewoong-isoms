package com.dtnsm.esop.repository;

import com.dtnsm.esop.domain.DocumentAccessLog;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DocumentAccessLogRepository extends PagingAndSortingRepository<DocumentAccessLog, Integer> {
}
