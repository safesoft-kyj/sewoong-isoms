package com.dtnsm.esop.repository;

import com.dtnsm.esop.domain.report.ExternalCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ExternalCustomerRepository extends JpaRepository<ExternalCustomer, Integer>, QuerydslPredicateExecutor<ExternalCustomer>, ExternalCustomerRepositoryCustom {
}
