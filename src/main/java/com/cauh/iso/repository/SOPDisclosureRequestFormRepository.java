package com.cauh.iso.repository;

import com.cauh.iso.domain.report.SOPDisclosureRequestForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SOPDisclosureRequestFormRepository extends JpaRepository<SOPDisclosureRequestForm, Integer>, QuerydslPredicateExecutor<SOPDisclosureRequestForm> {
}
