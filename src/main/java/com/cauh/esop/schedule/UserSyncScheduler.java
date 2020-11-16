package com.cauh.esop.schedule;

import com.cauh.common.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
@Slf4j
@RequiredArgsConstructor
public class UserSyncScheduler {
    private final UserService userService;

    @Scheduled(cron = "${scheduler.user-sync}")//초 분 시 일 월 요일 연(0시 1분)
    public void userSync() {
        userService.sync();
    }
}
