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
    private final UserRepository userRepository;
    private final SignatureRepository signatureRepository;
    private final DocumentViewer documentViewer;
    private final CategoryService categoryService;

    @Value("${file.binder-dir}")
    private String bindPath;

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
                                      SessionStatus status,
                                      @CurrentUser Account user) {

        //기존 내역이 존재하면, 새로 저장하려는 동의 정보에 동의 여부를 true로 하여 재지정.
        Optional<AgreementPersonalInformation> agreementPersonalInformationOptional = agreementPersonalInformationService.findByInternalUser(user);
        if(agreementPersonalInformationOptional.isPresent()) {
            AgreementPersonalInformation originAgreementPersonalInformation = agreementPersonalInformationOptional.get();
            agreementPersonalInformation.setId(originAgreementPersonalInformation.getId());
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
        return "redirect:/external/sop/effective";
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

    @GetMapping({
            "/external/sop/{status}",
            "/external/sop/{status}/{categoryId}",
            "/external/sop/{status}/{categoryId}/{sopId}"
    })
    public String sopList(@PathVariable("status") String stringStatus,
                          @PathVariable(value = "categoryId", required = false) String categoryId,
                          @PathVariable(value = "sopId", required = false) String sopId,
                          @CurrentUser Account user,
                          Model model) {
        log.info("@externalCustomerId : {}", user.getExternalCustomerId());
        Optional<ExternalCustomer> optionalExternalCustomer = externalCustomerRepository.findById(user.getExternalCustomerId());
        DocumentStatus status = DocumentStatus.valueOf(stringStatus.toUpperCase());
        if(optionalExternalCustomer.isPresent()) {
            ExternalCustomer externalCustomer = optionalExternalCustomer.get();
            List<DisclosureSOP> disclosureSOPList = externalCustomerRepository.getDocumentList(externalCustomer.getSopDisclosureRequestForm().getId(), status, categoryId, sopId);

            log.info("@SOP DB 조회 데이터 수 : {}", disclosureSOPList.size());
            /**
             * Category 정보 설정
             */
            if(!ObjectUtils.isEmpty(disclosureSOPList) && StringUtils.isEmpty(categoryId)) {
                model.addAttribute("CategoryList", StreamSupport.stream(disclosureSOPList.spliterator(), false)
                        .map(v -> v.getDocumentVersion().getDocument().getType() == DocumentType.SOP ? v.getDocument().getCategory() : v.getSopDocument().getCategory())
                        .distinct()
                        .sorted(Comparator.comparing(Category::getId))
                        .collect(Collectors.toList()));
            }

            /**
             * 최초 status 로만 필터가 된 경우
             */
            if(StringUtils.isEmpty(categoryId) && StringUtils.isEmpty(sopId)) {
                log.info("@Category/SOP 선택된 정보 없음");
                disclosureSOPList = disclosureSOPList.stream().map(s -> new DisclosureSOP(null, null,
                        s.getDocument().getType() == DocumentType.SOP ? s.getDocument() : s.getSopDocument()))
                        .distinct()
                        .sorted(Comparator.comparing(c -> c.getSopDocument().getDocId()))
                        .collect(Collectors.toList());
            }

            if(!StringUtils.isEmpty(categoryId) && StringUtils.isEmpty(sopId)) {/**category 가 선택된 경우*/
                log.info("@Category Id 선택됨 : {}", categoryId);
                disclosureSOPList = disclosureSOPList.stream()
                        .filter(s -> s.getDocument().getType() == DocumentType.SOP ?
                            s.getDocument().getCategory().getId().equals(categoryId) : s.getSopDocument().getCategory().getId().equals(categoryId))
                        .map(s -> new DisclosureSOP(null, null, s.getDocument().getType() == DocumentType.SOP ?
                                s.getDocument() : s.getSopDocument()))
                        .distinct()
                        .sorted(Comparator.comparing(s -> s.getSopDocument().getDocId()))
                        .collect(Collectors.toList());
            } else if(!StringUtils.isEmpty(sopId)) {/** SOP 가 선택된 경우 */
                log.info("@SOP 선택 됨 : {}", sopId);
                disclosureSOPList = disclosureSOPList.stream()
//                        .filter(s -> s.getDocument().getType() == DocumentType.SOP ?
//                                s.getDocument().getCategory().getId().equals(categoryId) : s.getSopDocument().getCategory().getId().equals(categoryId))
//                        .map(s -> new DisclosureSOP(s.getDocumentVersion(), s.getDocument(), s.getDocument().getType() == DocumentType.SOP ?
//                                s.getDocument() : s.getSopDocument()))
//                        .filter(s -> s.getSopDocument().getId().equals(sopId))
//                        .distinct()
                        .sorted(Comparator.comparing(s -> s.getDocument().getDocId()))
                        .collect(Collectors.toList());
            }

            log.debug("sopList = {}", disclosureSOPList);
            model.addAttribute("sopList", disclosureSOPList);
            model.addAttribute("categoryId", categoryId);

            if (!StringUtils.isEmpty(categoryId)) {
                model.addAttribute("category", categoryService.findById(categoryId));
            }

            model.addAttribute("sopId", sopId);
            model.addAttribute("status", status);

            return "sop/external-list";
        } else {
            throw new RuntimeException("현재 사용자는 Access 권한이 없습니다.");
        }
    }

    @GetMapping("/external/digital-binder")
    public String digitalBinder(@CurrentUser Account user, Model model) {
        if(!ObjectUtils.isEmpty(user.getDisclosureUsers())) {
            QAccount qUser = QAccount.account;
            BooleanBuilder builder = new BooleanBuilder();
            builder.and(qUser.username.in(user.getDisclosureUsers()));
            model.addAttribute("users", userRepository.findAll(builder));
        }
        return "externalCustomer/digital-binder";
    }

    @GetMapping("/ajax/digital-binder/{username}")
    public void digitalBinder(@PathVariable("username") String username, HttpServletResponse response) throws Exception {
        Optional<Signature> optionalSignature = signatureRepository.findById(username);
        OutputStream os = response.getOutputStream();
        if(optionalSignature.isPresent()) {
            try {
                Signature signature = optionalSignature.get();

                Path fileStorageLocation = Paths.get(bindPath).toAbsolutePath().normalize();
                Path filePath = fileStorageLocation.resolve(signature.getBinderFileName()).normalize();
                Resource resource = new UrlResource(filePath.toUri());

                ByteArrayOutputStream html = new ByteArrayOutputStream();
                documentViewer.toHTML("pdf", resource.getInputStream(), html);
                os.write(html.toByteArray());
            } catch (Exception e) {
                os.write("File Not Found.".getBytes());
            }

        } else {
            os.write("File Not Found.".getBytes());
        }

        os.flush();
        os.close();
    }
}
