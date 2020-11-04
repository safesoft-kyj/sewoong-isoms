package com.dtnsm.esop.admin.controller;

import com.dtnsm.common.entity.QAccount;
import com.dtnsm.common.entity.Account;
import com.dtnsm.common.entity.constant.UserType;
import com.dtnsm.common.mapper.DeptUserMapper;
import com.dtnsm.common.repository.UserRepository;
import com.dtnsm.common.service.UserService;
import com.dtnsm.esop.domain.AgreementPersonalInformation;
import com.dtnsm.esop.domain.NonDisclosureAgreement;
import com.dtnsm.esop.domain.constant.ApprovalStatus;
import com.dtnsm.esop.domain.report.QExternalCustomer;
import com.dtnsm.esop.repository.ExternalCustomerRepository;
import com.dtnsm.esop.service.AgreementPersonalInformationService;
import com.dtnsm.esop.service.NonDisclosureAgreementService;
import com.dtnsm.esop.xdocreport.AgreementReportService;
import com.dtnsm.esop.xdocreport.NonDisclosureAgreementReportService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminAuthorityController {
    private final AgreementPersonalInformationService agreementPersonalInformationService;
    private final NonDisclosureAgreementService nonDisclosureAgreementService;
//    private final SOPDisclosureRequestFormRepository sopDisclosureRequestFormRepository;
    private final AgreementReportService agreementReportService;
    private final NonDisclosureAgreementReportService nonDisclosureAgreementReportService;
    private final UserRepository userRepository;
    private final DeptUserMapper deptUserMapper;
    private final UserService userService;
    private final ExternalCustomerRepository externalCustomerRepository;

    @Value("${gw.userTbl}")
    private String gwUserTbl;

    @Value("${gw.deptTbl}")
    private String gwDeptTbl;

    @GetMapping("/authority/accounts")
    public String accounts(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 15) Pageable pageable, Model model) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QExternalCustomer qExternalCustomer = QExternalCustomer.externalCustomer;
        booleanBuilder.and(qExternalCustomer.sopDisclosureRequestForm.approval.status.eq(ApprovalStatus.approved));

        model.addAttribute("account", externalCustomerRepository.findAll(booleanBuilder, pageable));

        return "admin/authority/account";
    }

    @GetMapping("/authority/agreement-to-collect-and-use")
    public String agreement(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 15) Pageable pageable, Model model) {
        model.addAttribute("agreement", agreementPersonalInformationService.findAll(pageable));
        return "admin/authority/agreement";
    }

    @GetMapping("/authority/agreement-to-collect-and-use/{id}/print")
    public void agreement(@PathVariable("id") Integer id, HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Agreement to Collect and Use Personal Information.pdf");
        AgreementPersonalInformation agreement = agreementPersonalInformationService.findById(id).get();
        agreementReportService.generateReport(agreement, response);
    }

    @GetMapping("/authority/non-disclosure-agreement-for-sop")
    public String nonDisclosureSOP(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 15) Pageable pageable, Model model) {
        model.addAttribute("agreement", nonDisclosureAgreementService.findAll(pageable));
        return "admin/authority/non-disclosure-agreement";
    }

    @GetMapping("/authority/non-disclosure-agreement-for-sop/{id}/print")
    public void nonDisclosureSOP(@PathVariable("id") Integer id, HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Non-Disclosure Agreement for SOP.pdf");
        NonDisclosureAgreement agreement = nonDisclosureAgreementService.findById(id).get();
        nonDisclosureAgreementReportService.generateReport(agreement, response);
    }

    @GetMapping("/authority/users")
    public String users(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 15) Pageable pageable,
                        @RequestParam(value = "deptCode", required = false) String deptCode,
                        @RequestParam(value = "teamCode", required = false) String teamCode,
                        @RequestParam(value = "name", required = false) String name,
                        Model model) {
        BooleanBuilder builder = new BooleanBuilder();
        QAccount qUser = QAccount.account;


        if(StringUtils.isEmpty(name) == false) {
            builder.and(qUser.name.like("%" + name + "%"));
        }

        builder.and(qUser.enabled.eq(true));
        builder.and(qUser.userType.eq(UserType.U));
        model.addAttribute("users", userRepository.findAll(builder, pageable));
        return "admin/authority/users";
    }

    @PostMapping("/authority/users/sync")
    @Transactional
    public String sync(RedirectAttributes attributes) {
        userService.sync();
        attributes.addFlashAttribute("message", "조직도 정보가 업데이트 되었습니다.");

        return "redirect:/admin/authority/users";
    }


    @PutMapping("/authority/users/{id}/training")
    @ResponseBody
    public Map<String, String> setTraining(@PathVariable("id") Integer id) {
        Map<String, String> result = new HashMap<>();
        Optional<Account> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            Account user = optionalUser.get();
            user.setTraining(!user.isTraining());

            userRepository.save(user);
            result.put("result", "success");
            result.put("id", id.toString());
            result.put("name", user.getName());
            result.put("className", user.isTraining() ? "success" : "default");
        } else {
            result.put("result", "fail");
        }

        return result;
    }

    @PutMapping("/authority/users/{id}/receiveEmail")
    @ResponseBody
    public Map<String, String> setReceiveEmail(@PathVariable("id") Integer id) {
        Map<String, String> result = new HashMap<>();
        Optional<Account> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            Account user = optionalUser.get();
            user.setReceiveEmail(!user.isReceiveEmail());

            userRepository.save(user);
            result.put("result", "success");
            result.put("id", id.toString());
            result.put("name", user.getName());
            result.put("className", user.isReceiveEmail() ? "success" : "default");
        } else {
            result.put("result", "fail");
        }

        return result;
    }
}
