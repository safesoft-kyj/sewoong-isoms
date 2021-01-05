package com.cauh.iso.controller;

import com.cauh.common.entity.*;
import com.cauh.common.entity.constant.RoleStatus;
import com.cauh.common.repository.SignatureRepository;
import com.cauh.common.repository.UserRepository;
import com.cauh.common.security.annotation.CurrentUser;
import com.cauh.common.security.authentication.CustomUsernamePasswordAuthenticationToken;
import com.cauh.common.service.UserService;
import com.cauh.iso.admin.service.DepartmentService;
import com.cauh.iso.component.CurrentUserComponent;
import com.cauh.iso.service.JDService;
import com.cauh.iso.service.JobDescriptionService;
import com.cauh.iso.service.UserJobDescriptionChangeLogService;
import com.cauh.iso.validator.UserJobDescriptionChangeLogValidator;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@SessionAttributes({"userJobDescriptionChangeLog"})
public class UserController {
    private final UserRepository userRepository;

    //현재 유저 정보를 담고 있는 Component Class
    private final CurrentUserComponent currentUserComponent;
    private final SignatureRepository signatureRepository;
    private final JDService jdService;
    private final UserService userService;
    private final DepartmentService departmentService;
    private final UserJobDescriptionChangeLogService userJobDescriptionChangeLogService;
    private final UserJobDescriptionChangeLogValidator userJobDescriptionChangeLogValidator;

    private final PasswordEncoder passwordEncoder;

    private List<Account> accounts = new ArrayList<>();

    @GetMapping("/user/profile")
    public String profile() {
        return "user/profile";
    }

    @GetMapping("/user/profile/role")
    public String roleChanged(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 15) Pageable pageable, @CurrentUser Account user, Model model){
        QUserJobDescriptionChangeLog qUserJobDescriptionChangeLog = QUserJobDescriptionChangeLog.userJobDescriptionChangeLog;

        model.addAttribute("currentRoles", user.getUserJobDescriptions());
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qUserJobDescriptionChangeLog.user.eq(user));

        model.addAttribute("roleList", userJobDescriptionChangeLogService.getUserChangeLog(booleanBuilder, pageable));

        return "user/role";
    }

    @GetMapping("/user/profile/role/new")
    public String roleRequestNew(@CurrentUser Account user, Model model) {
        UserJobDescriptionChangeLog userJobDescriptionChangeLog = UserJobDescriptionChangeLog.builder().prevJobDescription(user.getCommaJobTitle()).build();
        model.addAttribute("userJobDescriptionChangeLog", userJobDescriptionChangeLog);
        model.addAttribute("jobDescriptionMap", jdService.getJDMap());

        return "user/role_edit";
    }

    @GetMapping("/user/profile/role/{id}")
    public String roleRequestEdit(@PathVariable("id") Integer id, RedirectAttributes attributes, Model model){

        Optional<UserJobDescriptionChangeLog> userJobDescriptionChangeLogOptional = userJobDescriptionChangeLogService.getById(id);
        if(!userJobDescriptionChangeLogOptional.isPresent()){
            attributes.addFlashAttribute("message", "존재하지 않는 정보입니다.");
            return "redirect:/user/profile/role";
        }

        model.addAttribute("commaJdIds", userJobDescriptionChangeLogService.getJdIdsByShortNames(userJobDescriptionChangeLogOptional.get().getNextJobDescription()));
        model.addAttribute("userJobDescriptionChangeLog", userJobDescriptionChangeLogOptional.get());
        model.addAttribute("jobDescriptionMap", jdService.getJDMap());

        return "user/role_edit";
    }

    @PostMapping({"/user/profile/role/new", "user/profile/role/{id}"})
    @Transactional
    public String roleRequest(@CurrentUser Account user,
                              @ModelAttribute("userJobDescriptionChangeLog") UserJobDescriptionChangeLog userJobDescriptionChangeLog,
                              Model model, SessionStatus sessionStatus, RedirectAttributes attributes, BindingResult result) {
        userJobDescriptionChangeLogValidator.validate(userJobDescriptionChangeLog, result);
        if(result.hasErrors()) {
            log.debug("--- Role Change Request Validate ---\n{}", result.getAllErrors());
            model.addAttribute("commaJdIds", userJobDescriptionChangeLogService.getJdIdsByShortNames(userJobDescriptionChangeLog.getNextJobDescription()));
            model.addAttribute("jobDescriptionMap", jdService.getJDMap());
            return "user/role_edit";
        }

        //New 인 경우,
        if(ObjectUtils.isEmpty(userJobDescriptionChangeLog.getId())) {
            if(userJobDescriptionChangeLogService.isRequestedRole(user)) {
                attributes.addFlashAttribute("type", "danger");
                attributes.addFlashAttribute("message", "이미 신청중인 Role 내역이 존재합니다.");
                return "redirect:/user/profile/role";
            }
        }

        userJobDescriptionChangeLog.setUser(user);
        userJobDescriptionChangeLog.setRoleStatus(RoleStatus.REQUESTED);
        userJobDescriptionChangeLog.setRequestDate(new Date());
        userJobDescriptionChangeLogService.saveChangeLog(userJobDescriptionChangeLog);
        sessionStatus.setComplete();

        attributes.addFlashAttribute("message", "Role 변경에 대한 신청이 이루어졌습니다.");
        return "redirect:/user/profile/role";
    }


    @GetMapping("/signUp")
    public String signUp(Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("jobDescriptionMap", jdService.getJDMap());
        model.addAttribute("departments", departmentService.getParentDepartment());
        return "/signup";
    }

    @PostMapping("/signUp")
    @Transactional
    public String signUpRequest(@ModelAttribute("account")Account account,
                                RedirectAttributes attributes, BindingResult result) {
        log.info("@Sign Up Request : {}", account.getUsername());

        Optional<Account> user = userRepository.findByUsername(account.getUsername());
        if(user.isPresent()) {

            attributes.addFlashAttribute("type", "danger");
            attributes.addFlashAttribute("message", "Sign Up request was failed");
        } else {
            Account signUpAccount = userService.signUpRequest(account);
            //계정정보 저장
            userRepository.save(signUpAccount);

            attributes.addFlashAttribute("type", "success");
            attributes.addFlashAttribute("message", "Sign up request is success");
        }
        return "redirect:/login";
    }

    @GetMapping("/user/signature")
    public String signature(@CurrentUser Account user, Model model) {
        Optional<Signature> optionalSignature = signatureRepository.findById(user.getUsername());
        model.addAttribute("signature", optionalSignature.isPresent() ? optionalSignature.get() : new Signature());

        return "user/signature";
    }

    @PostMapping("/user/signature")
    public String updateSignature(@CurrentUser Account user, @RequestParam("base64signature") String base64signature, RedirectAttributes attributes) {
        Optional<Account> optionalUser = userRepository.findById(user.getId());
        if(optionalUser.isPresent()) {
            Account u = optionalUser.get();

            Signature signature = new Signature();
            signature.setBase64signature(base64signature);
            signature.setId(u.getUsername());

            signatureRepository.save(signature);

            user.setSignature(true);
            updateAuthentication(user);
        }

        attributes.addFlashAttribute("message", "서명 정보가 등록 되었습니다.");
        return "redirect:/user/signature";
    }

    /**
     * Department 설정
     * @param id
     * @return
     */
    @PostMapping(value = "/signUp/ajax/department", produces = "application/text;charset=utf8")
    @ResponseBody
    public String getchildDepartments(@RequestParam("id") Integer id){
        StringBuffer sb = new StringBuffer();
        List<Department> departmentList = departmentService.getChildDepartment(new Department(id));

        if(departmentList.size() > 0){
            sb.append("<option value='department'>----------</option>");
        }
        for(Department department : departmentList) {
            sb.append("<option value='").append(department.getId()).append("'>");
            sb.append(department.getName()).append("</option>");
        }

        return sb.toString();
    }

    //BootstrapValidate - remote.
    @PostMapping("/signUp/ajax/validation")
    @ResponseBody
    public Map<String, Boolean> signUpUsernameValid(@RequestParam("type") String type,
                                                    @RequestParam("keyword") String keyword) {
        List<Account> currentUserAccountList = currentUserComponent.getCurrentUserList();
        log.debug("Account List : {}", currentUserAccountList);
        Map<String, Boolean> resultMap = new HashMap<>();
        Boolean result = true;

        if(type.equals("username")){
            //내용이 중복되면 false 반환
            for(Account account : currentUserAccountList){
                if(!ObjectUtils.isEmpty(account.getUsername()) && account.getUsername().equals(keyword)){
                    log.info("Validation Field : {}({})", type, keyword);
                    result = false;
                    break;
                }
            }
        }else if(type.equals("email")) {
            log.info("Data Type : {}({})", type, keyword);
            //내용이 중복되면 false 반환
            for(Account account : currentUserAccountList){
                if(!ObjectUtils.isEmpty(account.getEmail()) && account.getEmail().equals(keyword)){
                    log.info("Validation Field : {}({})", type, keyword);
                    result = false;
                    break;
                }
            }
        }
        resultMap.put("valid", result);
        return resultMap;
    }

    public void updateAuthentication(Account userDetails) {
        Collection authorities = userDetails.getSopAuthorities();
        Authentication authentication = new CustomUsernamePasswordAuthenticationToken(userDetails, null, authorities);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
    }
}

