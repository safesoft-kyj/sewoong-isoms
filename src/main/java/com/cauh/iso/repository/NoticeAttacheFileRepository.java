package com.cauh.iso.repository;

import com.cauh.iso.domain.NoticeAttachFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.history.RevisionRepository;

public interface NoticeAttacheFileRepository extends JpaRepository<NoticeAttachFile, String>, QuerydslPredicateExecutor<NoticeAttachFile>, RevisionRepository<NoticeAttachFile, String, Integer>{
}
