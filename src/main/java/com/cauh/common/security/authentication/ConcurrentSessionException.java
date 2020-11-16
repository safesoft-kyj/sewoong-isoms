package com.cauh.common.security.authentication;

import org.springframework.security.core.AuthenticationException;

public class ConcurrentSessionException extends AuthenticationException {

    public ConcurrentSessionException(String msg) {
        super(msg);
    }
}