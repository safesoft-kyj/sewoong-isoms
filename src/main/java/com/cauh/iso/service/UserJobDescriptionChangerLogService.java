package com.cauh.iso.service;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.UserJobDescriptionChangeLog;
import com.cauh.common.repository.UserJobDescriptionChangeLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserJobDescriptionChangerLogService {

    private final UserJobDescriptionChangeLogRepository userJobDescriptionChangeLogRepository;

    public List<UserJobDescriptionChangeLog> getUserChangeLog(Account user){
        return userJobDescriptionChangeLogRepository.findAllByUser(user);
    }


}
