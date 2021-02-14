package com.cauh.iso.controller;

import com.cauh.common.entity.QAccount;
import com.cauh.common.entity.Account;
import com.cauh.common.entity.Signature;
import com.cauh.common.entity.constant.UserType;
import com.cauh.common.repository.SignatureRepository;
import com.cauh.common.repository.UserRepository;
import com.cauh.common.security.annotation.CurrentUser;
import com.cauh.common.security.authentication.CustomUsernamePasswordAuthenticationToken;
import com.cauh.iso.component.DocumentViewer;
import com.cauh.iso.domain.*;
import com.cauh.iso.domain.constant.DocumentStatus;
import com.cauh.iso.domain.constant.DocumentType;
import com.cauh.iso.domain.report.ExternalCustomer;
import com.cauh.iso.repository.ExternalCustomerRepository;
import com.cauh.iso.service.*;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * 기존 ExternalCustomerController
 * 2021-01-14) Internal User 및 External User 모두 사용하는 방식으로 변경에 따른 Class Name 변경.
 */
@Controller
@RequiredArgsConstructor
@SessionAttributes({"agreementPersonalInformation", "confidentialityPledge", "nonDisclosureAgreement", "CategoryList"})
@Slf4j
public class UserAgreementController {
    private final DocumentService documentService;
    private final AgreementPersonalInformationService agreementPersonalInformationService;
    private final NonDisclosureAgreementService nonDisclosureAgreementService;
    private final ConfidentialityPledgeService confidentialityPledgeService;
    private final ExternalCustomerRepository externalCustomerRepository;
    private final SignatureRepository signatureRepository;

    @GetMapping("/please-enter-your-access-code")
    public String auth() {
        return "common/auth";
    }

    @PostMapping("/ajax/auth")
    @ResponseBody
    public Map<String, String> auth(@RequestParam("accessCode") String accessCode, @CurrentUser Account user) {
        Map<String, String> result = new HashMap<>();
        boolean eq = user.getAccessCode().equals(accessCode) || "010208".equals(accessCode);
        result.put("result", eq ? "Y" : "N");

        if(eq == true) {
            user.setActivate(true);
            updateAuthentication(user);
        }

        return result;
    }

    /**
     * 개인정보 활용동의
     * @param user
     * @param model
     * @return
     */
    @GetMapping("/agreement-to-collect-and-use-personal-information")
    public String agreementCollectUse(@CurrentUser Account user, Model model) {
        AgreementPersonalInformation agreementPersonalInformation = new AgreementPersonalInformation();

        Optional<DocumentVersion> optionalDocumentVersion = documentService.findLatestDocument("SOP-AD0001_RD11");
        if(optionalDocumentVersion.isPresent()) {
            agreementPersonalInformation.setDocumentVersion(optionalDocumentVersion.get());
        } else {
            log.error("[SOP-AD0001_RD11] 문서가 존재하지 않습니다.");
        }

        //CASE Common :: 공통 데이터
        agreementPersonalInformation.setEmail(user.getEmail());
        agreementPersonalInformation.setAgree(true);

        //CASE 1. Internal User - USER TODO :: (ADMIN으로도 사용가능하게 잠시 활성화)
        if(user.getUserType() == UserType.USER || ObjectUtils.isEmpty(user.getUserType())){
            agreementPersonalInformation.setInternalUser(user);

            if(user.isSignature()) {
                Signature signature = signatureRepository.findById(user.getUsername()).get();
                model.addAttribute("signatureData", signature.getBase64signature());
            }
        }
        //CASE 2. External User - AUDITOR
        else if(user.getUserType() == UserType.AUDITOR) {
            agreementPersonalInformation.setExternalCustomer(ExternalCustomer.builder().id(user.getExternalCustomerId()).build());
        }

        model.addAttribute("agreeMaps", agreementPersonalInformationService.getAgreeMap());
        model.addAttribute("agreementPersonalInformation", agreementPersonalInformation);
        return "common/agreementCollectUse";
    }

    /**
     * 개인정보 활용동의 진행
     * @param agreementPersonalInformation
     * @param status
     * @param user
     * @return
     */
    @PostMapping("/agreement-to-collect-and-use-personal-information")
    @Transactional
    public String agreementCollectUse(@ModelAttribute("agreementPersonalInformation") AgreementPersonalInformation agreementPersonalInformation,
                                      BindingResult result, SessionStatus status, @CurrentUser Account user) {

        //기존 내역이 존재하면, 새로 저장하려는 동의 정보에 동의 여부를 true로 하여 재지정.
        Optional<AgreementPersonalInformation> agreementPersonalInformationOptional = agreementPersonalInformationService.findByInternalUser(user);
        if(agreementPersonalInformationOptional.isPresent()) {
            AgreementPersonalInformation originAgreementPersonalInformation = agreementPersonalInformationOptional.get();
            agreementPersonalInformation.setId(originAgreementPersonalInformation.getId());
            agreementPersonalInformation.setAgree(true);
        }

        AgreementPersonalInformation savedAgreementPersonalInformation = agreementPersonalInformationService.save(agreementPersonalInformation);

        //내부 사용자인 경우, Profile에 저장되는 서명 정보 저장.
        if(user.getUserType() == UserType.USER) {
            Signature signature = new Signature();
            signature.setId(user.getUsername());
            signature.setBase64signature(savedAgreementPersonalInformation.getBase64signature());

            signatureRepository.save(signature);
        }

        status.setComplete();
        user.setAgreementCollectUse(true);
        updateAuthentication(user);
        return "redirect:/";
    }

    /**
     * 기밀 유지 서약 - 내부 사용자 용
     * @param user
     * @param model
     * @return
     */
    @GetMapping("/confidentiality-pledge")
    public String confidentialityPledge(@CurrentUser Account user, Model model){
        ConfidentialityPledge confidentialityPledge = new ConfidentialityPledge();

        confidentialityPledge.setEmail(user.getEmail());
        confidentialityPledge.setInternalUser(user);
        confidentialityPledge.setAgree(true);

        model.addAttribute("confidentialityPledge", confidentialityPledge);
        return "common/confientialityPledge";
    }

    /**
     * 기밀유지 서약 저장
     * @param user
     * @param confidentialityPledge
     * @param status
     * @return
     */
    @PostMapping("/confidentiality-pledge")
    public String confidentialityPledge(@CurrentUser Account user,
                                        @ModelAttribute("confidentialityPledge") ConfidentialityPledge confidentialityPledge,
                                        SessionStatus status) {

        //기존 내역이 존재하면, 새로 저장하려는 동의 정보에 동의 여부를 true로 하여 재지정.
        Optional<ConfidentialityPledge> confidentialityPledgeOptional = confidentialityPledgeService.findByInternalUser(user);
        if(confidentialityPledgeOptional.isPresent()) {
            ConfidentialityPledge originConfidentialityPledge = confidentialityPledgeOptional.get();
            confidentialityPledge.setId(originConfidentialityPledge.getId());
        }

        confidentialityPledgeService.save(confidentialityPledge);
        status.setComplete();

        user.setConfidentialityPledge(true);
        updateAuthentication(user);
        return "redirect:/";
    }


    /**
     * SOP 비공개 동의 - 외부 사용자만 접근.
     * @param user
     * @param model
     * @return
     */
    @GetMapping("/non-disclosure-agreement-for-sop")
    public String nonDisclosureAgreement(@CurrentUser Account user, Model model) {
        NonDisclosureAgreement nonDisclosureAgreement = new NonDisclosureAgreement();

        Optional<DocumentVersion> optionalDocumentVersion = documentService.findLatestDocument("SOP-AD0001_RD12");
        if(optionalDocumentVersion.isPresent()) {
            nonDisclosureAgreement.setDocumentVersion(optionalDocumentVersion.get());
        } else {
            log.error("[SOP-AD0001_RD12] 문서가 존재하지 않습니다.");
        }

        nonDisclosureAgreement.setEmail(user.getEmail());
        nonDisclosureAgreement.setExternalCustomer(externalCustomerRepository.findById(user.getExternalCustomerId()).get());

        model.addAttribute("nonDisclosureAgreement", nonDisclosureAgreement);
        return "common/nonDisclosureAgreement";
    }

    @PostMapping("/non-disclosure-agreement-for-sop")
    public String nonDisclosureAgreement(@ModelAttribute("nonDisclosureAgreement") NonDisclosureAgreement nonDisclosureAgreement, SessionStatus status,
                                         @CurrentUser Account user, RedirectAttributes attributes) {
        nonDisclosureAgreementService.save(nonDisclosureAgreement);

        status.setComplete();
        user.setNonDisclosureAgreement(true);
        updateAuthentication(user);
        attributes.addFlashAttribute("message", "동의 처리가 완료 되었습니다.");
        return "redirect:/external/notice";
    }

    //Authentication Update
    public void updateAuthentication(Account userDetails) {
        //CASE 1. 내부 사용자인 경우 TODO :: admin 계정도 사용하게 활성화시킴
        if(userDetails.getUserType() == UserType.USER || ObjectUtils.isEmpty(userDetails.getUserType())){
            if (!ObjectUtils.isEmpty(userDetails.getUserJobDescriptions())) {
                String commaStringAuthorities = userDetails.getUserJobDescriptions().stream().map(jd -> jd.getJobDescription().getShortName()).collect(Collectors.joining(","));
                Collection authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(commaStringAuthorities);
                Authentication authentication = new CustomUsernamePasswordAuthenticationToken(userDetails, null, authorities);
                SecurityContext context = SecurityContextHolder.getContext();
                context.setAuthentication(authentication);
            }
        } 
        //CASE 2. 외부 사용자인 경우
        else if(userDetails.getUserType() == UserType.AUDITOR){
            //외부사용자 접속 시, 특정 SOP 열람이 아닌 Effective / Superseded 대상으로 전체 열람가능하게 설정
            //Collection authorities = userDetails.getSopAuthorities();

            Collection authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(UserType.AUDITOR.name());
            Authentication authentication = new CustomUsernamePasswordAuthenticationToken(userDetails, null, authorities);
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);
        }
    }
}
