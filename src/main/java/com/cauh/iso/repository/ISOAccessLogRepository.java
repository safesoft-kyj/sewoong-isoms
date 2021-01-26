package com.cauh.iso.repository;

import com.cauh.iso.domain.ISOAccessLog;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ISOAccessLogRepository  extends PagingAndSortingRepository<ISOAccessLog, String> {
}
