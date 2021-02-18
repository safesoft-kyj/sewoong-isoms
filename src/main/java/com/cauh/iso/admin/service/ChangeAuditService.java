package com.cauh.iso.admin.service;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.CustomRevisionEntity;
import com.cauh.common.entity.RevisionAudit;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ChangeAuditService {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<RevisionAudit> getRevisionAuditList(Class<?> clazz, Pageable pageable) {
        final AuditReader auditReader = AuditReaderFactory.get(entityManager);

        /**
         * size : Page Size - 5, 10, 15, 20 ...
         * sort : sort Type - AuditEntity.revisionNumber().desc() / asc() etc...
         */
        AuditQuery auditQuery = auditReader.createQuery().forRevisionsOfEntityWithChanges(clazz,true);

        List<Object[]> histories = auditQuery.getResultList();

        List<RevisionAudit> revisionAuditList = histories.stream().map(history -> RevisionAudit.builder()
                .entity(clazz.cast(history[0]))
                .revisionEntity((CustomRevisionEntity) history[1])
                .revisionType((RevisionType) history[2])
                .propertiesChanged((Set<String>) history[3]).build()).collect(toList());

        return new PageImpl<>(revisionAuditList, pageable, revisionAuditList.size());
    }


}
