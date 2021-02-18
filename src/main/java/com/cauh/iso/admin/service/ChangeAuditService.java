package com.cauh.iso.admin.service;

import com.cauh.common.entity.CustomRevisionEntity;
import com.cauh.common.entity.EntityAudit;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChangeAuditService {

    @PersistenceContext(unitName = "common")
    private EntityManager entityManager;

//    public Page<EntityAudit> getRevisionAuditList(Class<?> clazz, Pageable pageable) {
//        final AuditReader auditReader = AuditReaderFactory.get(entityManager);
//
//        /**
//         * size : Page Size - 5, 10, 15, 20 ...
//         * sort : sort Type - AuditEntity.revisionNumber().desc() / asc() etc...
//         */
//        AuditQuery auditQuery = auditReader.createQuery().forRevisionsOfEntityWithChanges(clazz,true);
//
//        List<Object[]> histories = auditQuery.getResultList();
//
//        List<EntityAudit> revisionAuditList = histories.stream().map(history -> EntityAudit.builder()
//                .entity(clazz.cast(history[0]))
//                .revisionEntity((CustomRevisionEntity) history[1])
//                .revisionType((RevisionType) history[2])
//                .propertiesChanged((Set<String>) history[3]).build()).collect(toList());
//
//        return new PageImpl<>(revisionAuditList, pageable, revisionAuditList.size());
//    }

    public <T> List<EntityAudit> getRevisionAuditList(Class<T> clazz) {
        final AuditReader auditReader = AuditReaderFactory.get(entityManager);
        AuditQuery auditQuery = auditReader.createQuery()
                .forRevisionsOfEntityWithChanges(clazz,true);
        auditQuery.addOrder(AuditEntity.revisionNumber().desc());

        List results = auditQuery.getResultList();

        List<EntityAudit> entityAuditList = new ArrayList<>();

        for(Object row : results) {
            Object[] rowArray =(Object[])row;

            final T entity = (T)rowArray[0];
            final CustomRevisionEntity revisionEntity = (CustomRevisionEntity) rowArray[1];
            final RevisionType revisionType = (RevisionType) rowArray[2];
            final Set<String> propertiesChanged = (Set<String>) rowArray[3];

            EntityAudit<T> audit = new EntityAudit<T>();

            audit.setEntity(entity);
            audit.setRevisionEntity(revisionEntity);
            audit.setRevisionType(revisionType);
            audit.setPropertiesChanged(propertiesChanged);
            entityAuditList.add(audit);
        }

        return entityAuditList;
    }

    public <T, K> List<EntityAudit> getRevisionAuditList(Class<T> clazz, K entityId) {
        final AuditReader auditReader = AuditReaderFactory.get(entityManager);
        AuditQuery auditQuery = auditReader.createQuery()
                .forRevisionsOfEntityWithChanges(clazz,true);

        auditQuery.add(AuditEntity.id().eq(entityId));
        auditQuery.addOrder(AuditEntity.revisionNumber().desc());

        List results = auditQuery.getResultList();

        List<EntityAudit> entityAuditList = new ArrayList<>();

        for(Object row : results) {
            Object[] rowArray =(Object[])row;

            final T entity = (T)rowArray[0];
            final CustomRevisionEntity revisionEntity = (CustomRevisionEntity) rowArray[1];
            final RevisionType revisionType = (RevisionType) rowArray[2];
            final Set<String> propertiesChanged = (Set<String>) rowArray[3];

            EntityAudit<T> audit = new EntityAudit<T>();

            audit.setEntity(entity);
            audit.setRevisionEntity(revisionEntity);
            audit.setRevisionType(revisionType);
            audit.setPropertiesChanged(propertiesChanged);
            entityAuditList.add(audit);

        }

        return entityAuditList;
    }


}
