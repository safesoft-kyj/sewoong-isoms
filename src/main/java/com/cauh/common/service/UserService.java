package com.cauh.common.service;


import com.cauh.common.entity.Account;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

/**
 * Created by Dt&amp;SanoMedics <br>
 * Developer : Jeonghwan Seo <br>
 * Date &amp; Time : 2018-09-27  16:10 <br>
 * Comments : Description. <br>
 **/
public interface UserService {
    Account loadUserByUsername(String username) throws UsernameNotFoundException;

    Optional<Account> findUserByEmail(String usernameOrEmail);

    Account saveOrUpdate(Account user);

    Optional<Account> findByUsername(String username);

    void sync();
}
