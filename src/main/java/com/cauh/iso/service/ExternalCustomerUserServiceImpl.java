package com.cauh.iso.service;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.constant.UserType;
import com.cauh.common.service.ExternalCustomUserService;
import com.cauh.iso.domain.AgreementPersonalInformation;
import com.cauh.iso.domain.Mail;
import com.cauh.iso.domain.NonDisclosureAgreement;
import com.cauh.iso.domain.constant.ApprovalStatus;
import com.cauh.iso.domain.report.ExternalCustomer;
import com.cauh.iso.domain.report.QExternalCustomer;
import com.cauh.iso.domain.report.SOPDisclosureRequestForm;
import com.cauh.iso.repository.ExternalCustomerRepository;
import com.cauh.iso.utils.DateUtils;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExternalCustomerUserServiceImpl implements ExternalCustomUserService {
    private final MailService mailService;
    private final AgreementPersonalInformationService agreementPersonalInformationService;
    private final NonDisclosureAgreementService nonDisclosureAgreementService;
    private final ExternalCustomerRepository externalCustomerRepository;

    @Transactional(readOnly = true)
    public Optional<Account> findByEmail(String email) {
        Date currentDate = DateUtils.truncate(new Date());
        QExternalCustomer qExternalCustomer = QExternalCustomer.externalCustomer;
        BooleanBuilder ecBuilder = new BooleanBuilder();
        ecBuilder.and(qExternalCustomer.email.eq(email));
        ecBuilder.and(qExternalCustomer.sopDisclosureRequestForm.approval.status.eq(ApprovalStatus.approved));
        ecBuilder.and(qExternalCustomer.sopDisclosureRequestForm.requestStartDate.loe(currentDate).and(qExternalCustomer.sopDisclosureRequestForm.requestEndDate.goe(currentDate)));

        Iterable<ExternalCustomer> iterable = externalCustomerRepository.findAll(ecBuilder, qExternalCustomer.id.desc());
        Optional<ExternalCustomer> optionalExternalCustomer = StreamSupport.stream(iterable.spliterator(), false)
                .findFirst();

        if(optionalExternalCustomer.isPresent()) {

            ExternalCustomer externalCustomer = optionalExternalCustomer.get();
            SOPDisclosureRequestForm sopDisclosureRequestForm = externalCustomer.getSopDisclosureRequestForm();

            if (sopDisclosureRequestForm.getApproval().getStatus() == ApprovalStatus.approved) {
                log.info("@Customer Email : {} 로 승인된 SOP Disclosure Report Form : {} 정보 확인", email, sopDisclosureRequestForm.getId());
                String randomNo = randomNo();

                Optional<AgreementPersonalInformation> optionalAgreementPersonalInformation = agreementPersonalInformationService.findOneAgreementPersonalInformation(email);
                Optional<NonDisclosureAgreement> optionalNonDisclosureAgreement = nonDisclosureAgreementService.findOneNonDisclosureAgreement(email);
                Account user = new Account();
                user.setId(externalCustomer.getId());
                user.setName(externalCustomer.getName());
                user.setEngName(externalCustomer.getName());
                user.setUsername(email);
                user.setAccountNonLocked(true);
                user.setUserType(UserType.AUDITOR);
                user.setDeptName(sopDisclosureRequestForm.getCompanyNameOrInstituteName());
                user.setTeamName(sopDisclosureRequestForm.getCompanyNameOrInstituteName());
                user.setEmail(email);
                user.setAccessCode(randomNo);
                user.setAgreementCollectUse(optionalAgreementPersonalInformation.isPresent());
                user.setNonDisclosureAgreement(optionalNonDisclosureAgreement.isPresent());
                user.setExternalCustomerId(externalCustomer.getId());

                user.setDisclosureStartDate(sopDisclosureRequestForm.getRequestStartDate());
                user.setDisclosureEndDate(sopDisclosureRequestForm.getRequestEndDate());

                List<String> sopIds = sopDisclosureRequestForm.getRequestedDocumentSOPs().stream().map(s -> s.getDocumentVersion().getId()).collect(Collectors.toList());
                Map<String, String> allowedRFMap = sopDisclosureRequestForm.getRequestedDocumentRDs().stream()
                        .map(s -> s.getDocumentVersion())
                        .distinct()
                        .collect(Collectors.toMap(s -> s.getId(), s -> s.getDocument().getSop().getId()));
                log.debug("==> allowedRDMap : {}", allowedRFMap);
                if (ObjectUtils.isEmpty(sopIds) == false) {
                    user.getAllowedSOP().addAll(sopIds);
                }

                if (ObjectUtils.isEmpty(allowedRFMap) == false) {
                    user.getAllowedRFMap().putAll(allowedRFMap);
                }

                if(!ObjectUtils.isEmpty(sopDisclosureRequestForm.getDisclosureDigitalBinders())) {
                    user.setDisclosureUsers(sopDisclosureRequestForm.getDisclosureDigitalBinders().stream().map(s -> s.getUser().getUsername()).collect(Collectors.toList()));
                }

                log.info("@@ Allowed SOP[{}]", user.getAllowedSOP());
                log.info("@@ Allowed RD Maps(rdId, sopId)[{}]", user.getAllowedRFMap());
                log.info("@@ Allowed DB Users : {}", user.getDisclosureUsers());

                HashMap<String, Object> model = new HashMap<>();
                model.put("code", randomNo);
                Mail mail = Mail.builder()
                        .to(new String[]{email})
                        .subject("[KCSG] e-SOP Access Code")
                        .model(model)
                        .templateName("external-customer-code-template")
                        .build();
                mailService.sendMail(mail);
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    private String randomNo() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 6; i ++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }
}
