package com.cauh.iso.schedule;

import com.cauh.iso.service.AgreementsWithdrawalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class WithdrawalScheduler {

    private final AgreementsWithdrawalService agreementsWithdrawalService;

    @Scheduled(cron = "${scheduler.user-withdrawal}")
    public void withdrawal(){
        agreementsWithdrawalService.periodicWithdrawalProc();
    }

}
