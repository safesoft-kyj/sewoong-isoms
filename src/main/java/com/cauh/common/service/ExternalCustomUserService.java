package com.cauh.common.service;


import com.cauh.common.entity.Account;

import java.util.Optional;

public interface ExternalCustomUserService {
    Optional<Account> findByEmail(String email);
}
