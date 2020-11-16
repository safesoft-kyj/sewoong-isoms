package com.cauh.esop.schedule;

import com.cauh.esop.service.DocumentVersionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SOPTrainingAlertScheduler {
    private final DocumentVersionService documentVersionService;

//    @Scheduled(cron = "0 0 5 * * * ")//초 분 시 일 월 요일 연(0시 1분)
//    @Scheduled(cron = "0 */5 * * * *")//초 분 시 일 월 요일 연(0시 1분)
    @Scheduled(cron = "${scheduler.training-alert}")//초 분 시 일 월 요일 연(0시 1분)
    public void sopTrainingAlert() {
        documentVersionService.sopTrainingAlert();
    }
}
