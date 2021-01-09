package com.cauh.iso.admin.controller;

import com.cauh.common.entity.*;
import com.cauh.common.entity.constant.RoleStatus;
import com.cauh.common.entity.constant.UserType;
import com.cauh.common.mapper.DeptUserMapper;
import com.cauh.common.repository.UserJobDescriptionChangeLogRepository;
import com.cauh.common.repository.UserRepository;
import com.cauh.common.service.UserService;
import com.cauh.iso.admin.service.DepartmentService;
import com.cauh.iso.domain.AgreementPersonalInformation;
import com.cauh.iso.domain.Mail;
import com.cauh.iso.domain.NonDisclosureAgreement;
import com.cauh.iso.domain.constant.ApprovalStatus;
import com.cauh.iso.domain.report.QExternalCustomer;
import com.cauh.iso.repository.ExternalCustomerRepository;
import com.cauh.iso.security.annotation.IsAdmin;
import com.cauh.iso.service.*;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
@SessionAttributes({"account"})
public class AdminAuthorityController {
    private final AgreementPersonalInformationService agreementPersonalInformationService;
    private final NonDisclosureAgreementService nonDisclosureAgreementService;
    private final PasswordEncoder passwordEncoder;
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
    private final UserJobDescriptionChangeLogRepository userJobDescriptionChangeLogRepository;
    private final UserJobDescriptionChangeLogService userJobDescriptionChangeLogService;
    private final MailService mailService;

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
        QUserJobDescriptionChangeLog qUserJobDescriptionChangeLog = QUserJobDescriptionChangeLog.userJobDescriptionChangeLog;;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qUserJobDescriptionChangeLog.roleStatus.eq(RoleStatus.REQUESTED));
        model.addAttribute("roleRequestList", userJobDescriptionChangeLogRepository.findAll(builder, pageable));

        builder = new BooleanBuilder();
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
            @RequestParam(value = "department", required = false) Integer departmentId,
            @RequestParam(value = "jdIds", required = false) String[] jdIds,
            @RequestParam("id") Integer id, RedirectAttributes attributes){
        log.info("Action : {}, User id : {}", actionCmd, id);
        Optional<Account> optionalAccount = userRepository.findById(id);

        if(!optionalAccount.isPresent()) {
            attributes.addFlashAttribute("message", "User 정보가 잘못되었습니다.");
            return "redirect:/admin/authority/users";
        }
        Account account = optionalAccount.get();

        //SignUp Accept / Reject
        if(!StringUtils.isEmpty(actionCmd)) {
            if(actionCmd.equals("accept")) {
                log.debug("동의 : {}", account.getUsername());

                if(!ObjectUtils.isEmpty(departmentId)) {
                    account.setDepartment(departmentService.getDepartmentById(departmentId));
                    log.info("AC Dept : {}", account.getDepartment());
                }

                if(!ObjectUtils.isEmpty(jdIds)){
                    account.setJdIds(jdIds);
                    log.info("JD IDs : {}", jdIds);
                }

                //계정 유효기간 설정
                Account acceptUser = userService.signUpAccept(account);

                log.info("User's JD : {}", acceptUser.getUserJobDescriptions());

                Account savedUser = userService.saveOrUpdate(acceptUser);

                String message = "[" + savedUser.getUsername() + "] 가입 요청이 수락되었습니다.";
                attributes.addFlashAttribute("message", message);

            }else if(actionCmd.equals("reject")){
                log.debug("거절 : {}", account.getUsername());
                Account rejectUser = userService.signUpReject(account);
                Account savedUser = userService.saveOrUpdate(rejectUser);

                String message = "[" + savedUser.getUsername() + "] 가입 요청이 거절되었습니다.";
                attributes.addFlashAttribute("message", message);
            }
        }
        return "redirect:/admin/authority/users";
    }


    /**
     * Role Request 신청 수락 및 거절
     * @param id
     * @param action
     * @param attributes
     * @return
     */
    @PutMapping("/authority/users/role_request")
    @Transactional
    public String roleRequest(@RequestParam("id") Integer id,
                              @RequestParam("action") String action,
                              RedirectAttributes attributes){
        log.info("==== role Request");
        log.info("id : {}", id);
        log.info("action : {}", action);


        Optional<UserJobDescriptionChangeLog> userJobDescriptionChangeLogOptional
                = userJobDescriptionChangeLogRepository.findById(id);
        Account user = null;

        if(userJobDescriptionChangeLogOptional.isPresent()) {
            UserJobDescriptionChangeLog userJobDescriptionChangeLog = userJobDescriptionChangeLogOptional.get();

            //userJobDescriptionChangeLog Status Change
            if(action.equals("accept")) {
                userJobDescriptionChangeLog.setRoleStatus(RoleStatus.ACCEPTED);

                //UserJobDescription working
                user = userJobDescriptionChangeLog.getUser();
                String prevJDs = userJobDescriptionChangeLog.getPrevJobDescription();
                String nextJDs = userJobDescriptionChangeLog.getNextJobDescription();
                Date assignDate = userJobDescriptionChangeLog.getAssignDate();

                userJobDescriptionChangeLogService.updateUserJobDescription(user, prevJDs, nextJDs, assignDate);
                attributes.addFlashAttribute("message", "[" + user.getName() + "]의 ROLE 신청이 수락되었습니다.");

            } else if(action.equals("reject")){ //거절 하면 변동 없이 끝
                userJobDescriptionChangeLog.setRoleStatus(RoleStatus.REJECTED);
                user = userJobDescriptionChangeLog.getUser();

                attributes.addFlashAttribute("type", "warning");
                attributes.addFlashAttribute("message", "[" + user.getName() + "]의 ROLE 신청이 반려되었습니다.");
            } else {
                attributes.addFlashAttribute("type", "dnager");
                attributes.addFlashAttribute("message", "잘못된 동작을 수행하였습니다.");
                return "redirect:/admin/authority/users";
            }

            userJobDescriptionChangeLogRepository.save(userJobDescriptionChangeLog);
        }
        
        return "redirect:/admin/authority/users";
    }

    @PostMapping("/authority/users/pwReset/{id}")
    @Transactional
    public String passwordReset(@PathVariable("id") Integer id, RedirectAttributes attributes){
        //TODO :: Password Reset 작업 필요.
        Optional<Account> accountOptional = userRepository.findById(id);

        if(!accountOptional.isPresent()){ // 유저 정보가 없으면
            attributes.addFlashAttribute("type", "danger");
            attributes.addFlashAttribute("message", "잘못된 유저 정보입니다.");
            return "redirect:/admin/authority/users";
        }
        Account account = accountOptional.get();

        //계정 비밀번호 Setting 구간
        String rdPassword = getRandomPassword(10);
        account.setPassword(passwordEncoder.encode(rdPassword));
        LocalDate pwDueDate = LocalDate.now().plusDays(90); //오늘 날짜로부터 90일 다시 갱신.
        account.setCredentialsExpiredDate(Date.from(pwDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        userRepository.save(account);

        //계정 Mail 전송 구간.
        HashMap<String, Object> model = new HashMap<>();
        model.put("message", "임시 비밀번호 안내");
        model.put("password", rdPassword);

        Mail mail = Mail.builder()
                .to(new String[]{account.getEmail()})
                .subject(String.format("[ISO-MS/System] '%s' 사용자 임시 비밀번호 안내", account.getUsername()))
                .model(model)
                .templateName("user-password-reset")
                .build();

        mailService.sendMail(mail);

        attributes.addFlashAttribute("message", "[" + account.getName() + "]님의 임시 비밀번호가\n이메일로 전송되었습니다.");
        return "redirect:/admin/authority/users";
    }

    /**
     * 유저 정보 수정
     * @param id
     * @param model
     * @param attributes
     * @return
     */
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
        model.addAttribute("departments", departmentService.getParentDepartment());

        return "admin/authority/edit";
    }

    @PostMapping("/authority/users/{id}")
    @Transactional
    public String usersEditAction(@ModelAttribute("account")Account account, Model model, SessionStatus sessionStatus, RedirectAttributes attributes, BindingResult result) {
        userEditValidator.validate(account, result);
        if(result.hasErrors()) {
            log.debug("User Edit Errors : {}==========", result.getAllErrors());
            model.addAttribute("departments", departmentService.getParentDepartment());

            return "admin/authority/edit";
        }

        if(!ObjectUtils.isEmpty(account.getDepartment())) {
            Department department = account.getDepartment();
            if(!ObjectUtils.isEmpty(department.getParentDepartment())) {
                account.setDeptName(department.getParentDepartment().getName());
                account.setTeamName(department.getName());
            }else {
                account.setDeptName(department.getName());
                account.setTeamName(null);
            }
        }
        userService.saveOrUpdate(account);
        attributes.addFlashAttribute("message", "[" + account.getUsername() + "]의 정보가 수정되었습니다.");
        sessionStatus.setComplete();

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


    public static String getRandomPassword(int len) {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        int idx = 0;
        StringBuffer sb = new StringBuffer();
//        System.out.println("charSet.length :::: " + charSet.length);
        for (int i = 0; i < len; i++) {
            idx = (int) (charSet.length * Math.random()); // 36 * 생성된 난수를 Int로 추출 (소숫점제거)
            System.out.println("idx :::: "+idx);
            sb.append(charSet[idx]);
        }

        return sb.toString();
    }
}
