package com.cauh.iso.repository;

import com.cauh.iso.domain.DocumentAccessLog;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DocumentAccessLogRepository extends PagingAndSortingRepository<DocumentAccessLog, String> {
}
