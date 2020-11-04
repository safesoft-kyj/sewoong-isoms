package com.dtnsm.common.security.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Slf4j
public class CustomWebAccessDecisionVoter implements AccessDecisionVoter<FilterInvocation> {
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return attribute instanceof SecurityConfig;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz != null && clazz.isAssignableFrom(FilterInvocation.class);
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation filterInvocation, Collection<ConfigAttribute> attributes) {
        if (authentication == null) {
            return -1;
        } else {
            boolean containAuthority;
            final int accessAuthoritySize = attributes.size();
            int containAuthorityCount = 0;
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

//            attributes.stream().filter(attribute -> this.supports(attribute) && authorities.stream().filter(authority -> ((GrantedAuthority) authority).getAuthority().equals(attribute.getAttribute())).findAny().isPresent();
            for (ConfigAttribute attribute : attributes) {
                if (this.supports(attribute)) {
                    for (GrantedAuthority authority : authorities) {
                        if (attribute.getAttribute().equals(authority.getAuthority())) {
                            containAuthorityCount++;
                            if (accessAuthoritySize == containAuthorityCount) {
                                break;
                            }
                        }
                    }
                }
            }

            HttpServletRequest request = filterInvocation.getRequest();
//            containAuthority = (accessAuthoritySize == containAuthorityCount) ? true : false;
            containAuthority = (containAuthorityCount > 0) ? true : false;
            log.info("===> {}:[{}], Access Authorities:{}", authentication.getName(), request.getRequestURI(), attributes);
            log.info("<=== {}:[{}:{}], User Authorities:{} *** {} ***", authentication.getName(), request.getRequestURI(), request.getMethod(), authentication.getAuthorities(), (containAuthority ? "Access Granted." : "Access Denied."));
            return containAuthority ? ACCESS_GRANTED : ACCESS_DENIED;
        }
    }
}