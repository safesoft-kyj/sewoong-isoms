package com.cauh.iso.config;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class AuditReaderConfig {
    @PersistenceContext(unitName = "common")
    private EntityManager entityManager;

    @Bean
    public AuditReader getAuditReader() {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        return auditReader;
    }

}
