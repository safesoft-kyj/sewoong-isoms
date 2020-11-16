package com.cauh.common.security.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public class CustomUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken implements Serializable {

    private static final long serialVersionUID = -390638980741589698L;
    private CustomWebAuthenticationDetails details;

//    public CustomUsernamePasswordAuthenticationToken(Object principal, Object credentials) {
//        super(principal, credentials);
//
//    }

    public CustomUsernamePasswordAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    @Override
    public CustomWebAuthenticationDetails getDetails() {
        return details;
    }

    public void setDetails(CustomWebAuthenticationDetails details) {
        this.details = details;
    }
}
