package com.dtnsm.common.service;

import com.dtnsm.common.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;

/**
 * Created by Dt&amp;SanoMedics <br>
 * Developer : Jeonghwan Seo <br>
 * Date &amp; Time : 2018-09-27  17:29 <br>
 * Comments : Description. <br>
 **/
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public Account loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = userService.loadUserByUsername(username);
        log.info("loadUserByUsername({}) : {}", username, user);
        if (ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException("Username " + username + " not found.");
        }

        return user;
    }
}

