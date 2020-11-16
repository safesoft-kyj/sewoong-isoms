package com.cauh.esop.repository;

import com.cauh.esop.domain.report.SOPDisclosureRequestForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SOPDisclosureRequestFormRepository extends JpaRepository<SOPDisclosureRequestForm, Integer>, QuerydslPredicateExecutor<SOPDisclosureRequestForm> {
}
