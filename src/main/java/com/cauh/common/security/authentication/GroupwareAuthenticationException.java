package com.cauh.common.security.authentication;

import org.springframework.security.authentication.AccountStatusException;

public class GroupwareAuthenticationException extends AccountStatusException {
    public GroupwareAuthenticationException(String explanation) {
        super(explanation);
    }
}
