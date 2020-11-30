package com.cauh.iso.component;

import com.cauh.common.entity.Account;
import com.cauh.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  현재 유저 정보를 받아오기 위한 Component
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CurrentUserComponent {
    private final UserRepository userRepository;
    private List<Account> currentUserList;

    public void updateCurrentUserList(){
        log.info("====Current User List Update====");
        currentUserList = userRepository.findAll();
    }

    //현재 유저 List 넘기기.
    public List<Account> getCurrentUserList(){
        return currentUserList;
    }

}
