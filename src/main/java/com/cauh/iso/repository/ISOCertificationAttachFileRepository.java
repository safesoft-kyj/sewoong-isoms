package com.cauh.iso.repository;

import com.cauh.iso.domain.ISOCertification;
import com.cauh.iso.domain.ISOCertificationAttachFile;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ISOCertificationAttachFileRepository extends PagingAndSortingRepository<ISOCertificationAttachFile, String>, QuerydslPredicateExecutor<ISOCertificationAttachFile> {
}
