package com.dtnsm.common.service;

import com.dtnsm.common.entity.Account;

import java.util.Optional;

public interface ExternalCustomUserService {
    Optional<Account> findByEmail(String email);
}
