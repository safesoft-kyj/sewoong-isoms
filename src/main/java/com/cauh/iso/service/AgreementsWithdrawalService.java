package com.cauh.iso.service;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.constant.UserStatus;
import com.cauh.iso.domain.AgreementPersonalInformation;
import com.cauh.iso.domain.AgreementsWithdrawal;
import com.cauh.iso.domain.ConfidentialityPledge;
import com.cauh.iso.domain.Mail;
import com.cauh.iso.repository.AgreementPersonalInformationRepository;
import com.cauh.iso.repository.AgreementsWithdrawalRepository;
import com.cauh.iso.repository.ConfidentialityPledgeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgreementsWithdrawalService {

    private final AgreementsWithdrawalRepository agreementsWithdrawalRepository;
    private final AgreementPersonalInformationRepository agreementPersonalInformationRepository;
    private final ConfidentialityPledgeRepository confidentialityPledgeRepository;
    private final MailService mailService;

    public Page<AgreementsWithdrawal> findAll(Pageable pageable){
        return agreementsWithdrawalRepository.findAll(pageable);
    }

    public AgreementsWithdrawal withdrawalRequest(Account user, AgreementsWithdrawal withdrawal) {
        //현재 접속중인 유저 정보 추가.
        withdrawal.setUser(user);

        HashMap<String, Object> model = new HashMap<>();
        model.put("message", "철회 신청 알림");
        model.put("teamDept", user.getTeamDept());
        model.put("username", user.getUsername());
        model.put("name", user.getName());
        model.put("withdrawalDate", withdrawal.getWithdrawalDate());

        Mail mail = Mail.builder()
                .to(new String[]{withdrawal.getEmail()})
                .subject(String.format("[ISO-MS/System] 개인정보 활용동의/기밀 유지 서약 철회 신청 알림"))
                .model(model)
                .templateName("withdrawal-notification")
                .build();

        mailService.sendMail(mail);


        AgreementsWithdrawal result = agreementsWithdrawalRepository.save(withdrawal);

        return result;
    }

    /**
     * 1일 단위로 철회 신청 정보에 기반한 내용 확인.
     */
    public void periodicWithdrawalProc() {
        log.info("@철회 신청 내용 확인 후 비활성화 진행=================");

        Date date = new Date();
        List<AgreementsWithdrawal> agreementsWithdrawals = agreementsWithdrawalRepository.findAllByWithdrawalDateBeforeAndAndApply(date, false);

        for(AgreementsWithdrawal agreementsWithdrawal : agreementsWithdrawals) {
            Account user = agreementsWithdrawal.getUser();

            Optional<AgreementPersonalInformation> agreementPersonalInformationOptional = agreementPersonalInformationRepository.findByInternalUser(user);
            if(agreementPersonalInformationOptional.isPresent()) {
                //개인정보 활용 동의 철회 설정.
                AgreementPersonalInformation agreementPersonalInformation = agreementPersonalInformationOptional.get();
                agreementPersonalInformation.setAgree(false);
                agreementPersonalInformationRepository.save(agreementPersonalInformation);
            }

            Optional<ConfidentialityPledge> confidentialityPledgeOptional = confidentialityPledgeRepository.findByInternalUser(user);
            if(confidentialityPledgeOptional.isPresent()) {

                //기밀유지 서약 동의 철회 설정.
                ConfidentialityPledge confidentialityPledge = confidentialityPledgeOptional.get();
                confidentialityPledge.setAgree(false);
                confidentialityPledgeRepository.save(confidentialityPledge);
            }

            //TODO :: 회원탈퇴 절차 추가 필요.
            if(ObjectUtils.isEmpty(user)) {
                //회원탈퇴 절차 진행
                user.setEnabled(false);
                user.setAccountExpiredDate(new Date());
                user.setUserStatus(UserStatus.RETIREE);
            }

            //위의 내역 반영 후 적용
            agreementsWithdrawal.setApply(true);
            agreementsWithdrawalRepository.save(agreementsWithdrawal);
            log.info("철회 내용 기반 작업 완료 : {}", agreementsWithdrawal.isApply());
        }
    }
}
