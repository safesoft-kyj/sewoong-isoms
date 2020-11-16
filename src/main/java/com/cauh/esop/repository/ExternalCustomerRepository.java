package com.cauh.esop.repository;

import com.cauh.esop.domain.report.ExternalCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ExternalCustomerRepository extends JpaRepository<ExternalCustomer, Integer>, QuerydslPredicateExecutor<ExternalCustomer>, ExternalCustomerRepositoryCustom {
}
