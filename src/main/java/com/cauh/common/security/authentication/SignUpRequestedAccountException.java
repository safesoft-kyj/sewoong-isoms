package com.cauh.common.security.authentication;

import org.springframework.security.authentication.AccountStatusException;

import javax.security.auth.login.AccountException;

public class SignUpRequestedAccountException extends AccountStatusException {
    public SignUpRequestedAccountException(String msg){
        super(msg);
    }

}
