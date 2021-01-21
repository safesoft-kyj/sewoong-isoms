package com.cauh.iso.repository;

import com.cauh.iso.domain.Category;
import com.cauh.iso.domain.constant.CategoryType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String>, QuerydslPredicateExecutor<Category> {
}
