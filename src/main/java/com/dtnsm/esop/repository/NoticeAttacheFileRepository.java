package com.dtnsm.esop.repository;

import com.dtnsm.esop.domain.NoticeAttachFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface NoticeAttacheFileRepository extends JpaRepository<NoticeAttachFile, String>, QuerydslPredicateExecutor<NoticeAttachFile> {
}
