package com.cauh.common.security.authentication;

import org.springframework.security.authentication.AccountStatusException;

public class InternalAccountAuthenticationException extends AccountStatusException {
    public InternalAccountAuthenticationException(String explanation) {
        super(explanation);
    }
}
