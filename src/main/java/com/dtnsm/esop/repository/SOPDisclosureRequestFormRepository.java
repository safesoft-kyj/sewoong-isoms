package com.dtnsm.esop.repository;

import com.dtnsm.esop.domain.report.SOPDisclosureRequestForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SOPDisclosureRequestFormRepository extends JpaRepository<SOPDisclosureRequestForm, Integer>, QuerydslPredicateExecutor<SOPDisclosureRequestForm> {
}
