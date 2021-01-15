package com.cauh.common.repository;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.Department;
import com.cauh.common.entity.constant.UserStatus;
import com.cauh.common.entity.constant.UserType;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<Account, Integer>, QuerydslPredicateExecutor<Account> {
    @Override
    List<Account> findAll();

    Optional<Account> findByUsername(@Param("username") String username);

    Optional<Account> findByUsernameAndEmail(String username, String email);

    List<Account> findAllByUserTypeAndEnabledOrderByNameAsc(UserType userType, boolean enabled);

    //WHERE enabled = ? and user_status in (?, ?)
    List<Account> findAllByEnabledAndUserStatusIn(boolean enabled, List<UserStatus> userStatus);

    List<Account> findAllByDepartment(Department department);
    Integer countAllByDepartment(Department department);

    long countByUserStatus(UserStatus userStatus);

//    Page<Account> findAllByUserTypeAndEnabled(UserType userType, boolean enabled, Pageable pageable);
}
