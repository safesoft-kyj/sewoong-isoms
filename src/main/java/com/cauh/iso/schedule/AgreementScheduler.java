package com.cauh.iso.schedule;

import com.cauh.common.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

//@Component
@Slf4j
@RequiredArgsConstructor
public class AgreementScheduler {
    private final UserService userService;

//    /**
//     * 개인정보 활용동의, 비밀 보장 서약서, SOP 비공개 동의 5년 경과 확인 후 거절 및 폐기.
//     */
//    @Scheduled(cron = "${scheduler.agreement-check}")//초 분 시 일 월 요일 연(0시 25분)
//    public void agreementCheck() {
//        userService.agreementCheck();
//    }
//
//    User 정보 갱신
//    @Scheduled(cron = "${scheduler.user-refresh}")//초 분 시 일 월 요일 연(0시 01분)
//    public void userRefresh() {
//        userService.sync();
//    }
}
