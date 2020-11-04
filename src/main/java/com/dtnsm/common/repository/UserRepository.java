package com.dtnsm.common.repository;

import com.dtnsm.common.entity.Account;
import com.dtnsm.common.entity.constant.UserType;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<Account, Integer>, QuerydslPredicateExecutor<Account> {
    @Override
    List<Account> findAll();

    Optional<Account> findByUsername(@Param("username") String username);

    List<Account> findAllByUserTypeAndEnabledOrderByNameAsc(UserType userType, boolean enabled);

//    Page<Account> findAllByUserTypeAndEnabled(UserType userType, boolean enabled, Pageable pageable);
}
