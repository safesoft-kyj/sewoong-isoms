package com.dtnsm.esop.schedule;

import com.dtnsm.esop.service.DocumentVersionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SOPStatusUpdateScheduler {
//    private final MailService mailService;
    private final DocumentVersionService documentVersionService;

//    @Scheduled(cron = "0 0 */1 * * * ")//초 분 시 일 월 요일 연(1시간 마다)
//    @Scheduled(cron = "0 */2 * * * *")//초 분 시 일 월 요일 연(5분마다)
    @Scheduled(cron = "${scheduler.sop-status-update}")//초 분 시 일 월 요일 연
    public void approvedToEffective() {
        log.info(" => Approved SOP의 Effective Date를 확인하여 effective 상태로 변경한다.");
//        log.info(" => 상위 버전이 있는 경우 superseded 상태로 변경한다.");
//        log.info(" => 해당 내용들을 메일로 전송 한다.");
        documentVersionService.approvedToEffective();

//        Mail mail = Mail.builder()
//                .to(new String[]{"jhseo@dtnsm.com"})
//                .subject("e-SOP Alert Test")
//                .templateName("alert-test")
//                .model(null)
//                .build();
//
//        mailService.sendMail(mail);
    }

}
