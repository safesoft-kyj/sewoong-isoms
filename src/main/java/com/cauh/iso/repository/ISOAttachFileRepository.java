package com.cauh.iso.repository;

import com.cauh.iso.domain.ISOAttachFile;
import com.cauh.iso.domain.NoticeAttachFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.history.RevisionRepository;

public interface ISOAttachFileRepository extends JpaRepository<ISOAttachFile, String>, QuerydslPredicateExecutor<ISOAttachFile>, RevisionRepository<ISOAttachFile, String, Integer> {
}
