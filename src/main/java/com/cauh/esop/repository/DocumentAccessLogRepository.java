package com.cauh.esop.repository;

import com.cauh.esop.domain.DocumentAccessLog;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DocumentAccessLogRepository extends PagingAndSortingRepository<DocumentAccessLog, Integer> {
}
