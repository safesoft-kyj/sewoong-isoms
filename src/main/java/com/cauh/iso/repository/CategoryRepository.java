package com.cauh.iso.repository;

import com.cauh.iso.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CategoryRepository extends JpaRepository<Category, String>, QuerydslPredicateExecutor<Category> {
}
