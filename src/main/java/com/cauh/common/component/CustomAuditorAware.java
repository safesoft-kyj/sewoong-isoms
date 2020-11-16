package com.cauh.common.component;

import com.cauh.common.entity.Account;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Component
public class CustomAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(ObjectUtils.isEmpty(authentication) || (authentication.getPrincipal() instanceof UserDetails) == false) {
            return Optional.empty();
        } else {
            return Optional.of(((Account)authentication.getPrincipal()).getName());
        }
    }
}
