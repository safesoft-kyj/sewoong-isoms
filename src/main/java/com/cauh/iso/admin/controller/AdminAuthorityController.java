package com.cauh.iso.admin.controller;

import com.cauh.common.entity.QAccount;
import com.cauh.common.entity.Account;
import com.cauh.common.entity.RoleAccount;
import com.cauh.common.entity.constant.UserStatus;
import com.cauh.common.entity.constant.UserType;
import com.cauh.common.mapper.DeptUserMapper;
import com.cauh.common.repository.UserRepository;
import com.cauh.common.service.UserService;
import com.cauh.iso.admin.service.DepartmentService;
import com.cauh.iso.domain.AgreementPersonalInformation;
import com.cauh.iso.domain.NonDisclosureAgreement;
import com.cauh.iso.domain.constant.ApprovalStatus;
import com.cauh.iso.domain.report.QExternalCustomer;
import com.cauh.iso.repository.ExternalCustomerRepository;
import com.cauh.iso.service.AgreementPersonalInformationService;
import com.cauh.iso.service.JDService;
import com.cauh.iso.service.NonDisclosureAgreementService;
import com.cauh.iso.validator.UserEditValidator;
import com.cauh.iso.xdocreport.AgreementReportService;
import com.cauh.iso.xdocreport.NonDisclosureAgreementReportService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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
    private final UserEditValidator userEditValidator;
    private final DeptUserMapper deptUserMapper;
    private final UserService userService;
    private final JDService jdService;
    private final DepartmentService departmentService;
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
        builder.and(qUser.userType.eq(UserType.USER));

        model.addAttribute("account", new Account());
        model.addAttribute("departments", departmentService.getParentDepartment());
        model.addAttribute("jobDescriptionMap", jdService.getJDMap());
        model.addAttribute("users", userRepository.findAll(builder, pageable));
        return "admin/authority/users";
    }

    /**
     *
     * @param actionCmd
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/authority/users")
    public String userSignUpAction(
            @RequestParam(value = "result", required = false ) String actionCmd,
            @RequestParam("id") Integer id, RedirectAttributes attributes){
        log.info("Action : {}, User id : {}", actionCmd, id);
        Optional<Account> optionalAccount = userRepository.findById(id);

        if(!optionalAccount.isPresent()) {
            attributes.addFlashAttribute("message", "User 정보가 잘못되었습니다.");
            return "redirect:/admin/authority/users";
        }
        Account account = optionalAccount.get();

        //SignUp Agree
        //TODO:: Logic 수정 필요. (직무배정 및 ROLE 부여가 추가가 되는지 확인)
        if(!StringUtils.isEmpty(actionCmd)) {
            if(actionCmd.equals("accept")) {
                log.info("동의");

                //계정 유효기간 설정
                Account acceptUser = userService.signUpAccept(account);

                Account savedUser = userService.saveOrUpdate(account);

                String message = "[" + savedUser + "] 가입 요청이 수락되었습니다.";
                attributes.addFlashAttribute("message", message);

            }else if(actionCmd.equals("reject")){
                log.info("거절");

                //계정 유효기간 만료로 처리 (현재 시간 입력)
                //계정 상태 INACTIVE 지정 / Enabled 변수 false로 설정.
                account.setAccountExpiredDate(new Date());
                account.setUserStatus(UserStatus.INACTIVE);
                account.setEnabled(false);
                Account savedUser = userService.saveOrUpdate(account);

                String message = "[" + savedUser.getUsername() + "] 가입 요청이 거절되었습니다.";
                attributes.addFlashAttribute("message", message);
                return "redirect:/admin/authority/users";
            }
        }
        return "redirect:/admin/authority/users";
    }

    @GetMapping("/authority/users/{id}")
    public String usersEdit(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
        log.info("ID : {}", id);
        Optional<Account> optionalAccount = userRepository.findById(id);
        if(!optionalAccount.isPresent()) {
            attributes.addFlashAttribute("message", "존재하지 않는 유저 정보입니다.");
            return "redirect:/admin/authority/users";
        }

        Account account = optionalAccount.get();

        //User Edit
        model.addAttribute("account", account);
        return "admin/authority/edit";
    }

    @PostMapping("/authority/users/{id}")
    @Transactional
    public String usersEditAction(@PathVariable("{id}") String id, Account account, RedirectAttributes redirectAttributes, BindingResult result) {

        //TODO :: 유저정보 수정 작업 필요.
        userEditValidator.validate(account, result);

        if(result.hasErrors()) {
            return "admin/authority/edit";
        }



        return "redirect:/admin/authority/users";
    }

//    @PostMapping("/authority/users/sync")
//    @Transactional
//    public String sync(RedirectAttributes attributes) {
//        userService.sync();
//        attributes.addFlashAttribute("message", "조직도 정보가 업데이트 되었습니다.");
//
//        return "redirect:/admin/authority/users";
//    }


    //교육 진행 여부 설정 변경.
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
