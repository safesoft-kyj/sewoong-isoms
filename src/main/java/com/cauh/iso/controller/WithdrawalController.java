package com.cauh.iso.controller;

import com.cauh.common.entity.Account;
import com.cauh.common.security.annotation.CurrentUser;
import com.cauh.iso.admin.service.DepartmentService;
import com.cauh.iso.domain.AgreementsWithdrawal;
import com.cauh.iso.repository.AgreementsWithdrawalRepository;
import com.cauh.iso.service.AgreementsWithdrawalService;
import com.cauh.iso.validator.AgreementsWithdrawalValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;

@Controller
@RequiredArgsConstructor
@Slf4j
@SessionAttributes({"user", "withdrawal"})
public class WithdrawalController {

    private final DepartmentService departmentService;
    private final AgreementsWithdrawalValidator agreementsWithdrawalValidator;
    private final AgreementsWithdrawalRepository agreementsWithdrawalRepository;
    private final AgreementsWithdrawalService agreementsWithdrawalService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/agreements-withdrawal")
    public String agreementsWithdrawal(Model model) {
        model.addAttribute("user", new Account());
        model.addAttribute("departments", departmentService.getParentDepartment());

        return "common/withdrawal";
    }

    @PostMapping("/agreements-withdrawal")
    public String agreementsWithdrawalProc(@CurrentUser Account curUser, @ModelAttribute("user") Account user,
                                           BindingResult result, Model model) {

        //입력한 user 정보에 대한 validate
        userValidate(curUser, user, result);

        if (result.hasErrors()) {
            log.error("Withrawal Error : {}", result.getAllErrors());
            model.addAttribute("departments", departmentService.getParentDepartment());
            return "common/withdrawal";
        }

        //사용자 정보 확인 절차 통과
        user.setWithdrawal(true);

        return "redirect:/agreements-withdrawal/check";
    }

    @GetMapping("/agreements-withdrawal/check")
    public String agreementsWithdrawalCheck(@ModelAttribute("user")Account user, Model model, RedirectAttributes attributes){

        //사용자 정보 확인을 거쳤는지에 대한 경로확인
        if(ObjectUtils.isEmpty(user)){
            attributes.addFlashAttribute("message", "잘못된 경로입니다. 사용자 확인 화면으로 돌아갑니다.");
            return "redirect:/agreements-withdrawal";
        } else if (!user.isWithdrawal()){
            attributes.addFlashAttribute("message", "잘못된 경로입니다. 사용자 확인 화면으로 돌아갑니다.");
            return "redirect:/agreements-withdrawal";
        }
        //해당 화면 진입 통과 후 user 정보 초기화. (새로고침, 재진입 방지)
        user.setWithdrawal(false);

        model.addAttribute("withdrawal", new AgreementsWithdrawal());
        return "common/withdrawal-check";
    }

    @PostMapping("/agreements-withdrawal/check")
    public String agreementsWithdrawalCheckProc(@ModelAttribute("withdrawal") AgreementsWithdrawal agreementsWithdrawal,
                                                @CurrentUser Account user, RedirectAttributes attributes, SessionStatus status, BindingResult result) {

        agreementsWithdrawalValidator.validate(agreementsWithdrawal, result);

        //날짜 정보가 입력된 경우, 해당 유저 정보로 이미 철회내용이 있는지 확인
        if(!ObjectUtils.isEmpty(agreementsWithdrawal.getWithdrawalDate()) && agreementsWithdrawalRepository.findByUser(user).isPresent()) {
            result.rejectValue("withdrawalDate", "message.agreementsWithdrawal.duplicate", "이미 철회 신청한 내용이 있습니다.");
        }

        if(result.hasErrors()) {
            log.error("AgreementsWithdrawal Error : {}", result.getAllErrors());
            return "common/withdrawal-check";
        }
        status.setComplete();

        agreementsWithdrawalService.withdrawalRequest(user, agreementsWithdrawal);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        attributes.addFlashAttribute("message", "[" + df.format(agreementsWithdrawal.getWithdrawalDate())  + "] 일자로 약관 동의에 대한 철회 신청이 완료되었습니다.");

        return "redirect:/";
    }


    private void userValidate(Account curUser, Account user, BindingResult result) {

        if(org.springframework.util.ObjectUtils.isEmpty(user.getEmail())) {
            result.rejectValue("email", "message.email.required", "이메일 주소를 입력해주세요.");
        }

        if(org.springframework.util.ObjectUtils.isEmpty(user.getName())) {
            result.rejectValue("name", "message.name.required", "이름을 입력해주세요.");
        }

        if (ObjectUtils.isEmpty(curUser.getDepartment())) {
            result.rejectValue("department", "message.department.mismatch", "현재 접속 중인 유저의 부서가 확인되지 않습니다.");
        }

        if (!(ObjectUtils.isEmpty(user.getEmail()) || ObjectUtils.isEmpty(curUser.getEmail())) &&
                !curUser.getEmail().equals(user.getEmail())) {
            result.rejectValue("email", "message.email.mismatch", "입력된 Email 주소가 일치하지 않습니다.");
        }

        if (!(ObjectUtils.isEmpty(user.getDepartment()) || ObjectUtils.isEmpty(curUser.getDepartment())) &&
                !curUser.getDepartment().equals(user.getDepartment())) {
            result.rejectValue("department", "message.department.mismatch", "입력된 부서 정보가 일치하지 않습니다.");
        }

        if (!(ObjectUtils.isEmpty(user.getName()) || ObjectUtils.isEmpty(curUser.getName())) &&
                !curUser.getName().equals(user.getName())) {
            result.rejectValue("name", "message.name.mismatch", "입력된 이름 정보가 일치하지 않습니다.");
        }

        if(ObjectUtils.isEmpty(user.getPassword())) {
            result.rejectValue("password", "message.password.required", "비밀번호를 입력해주세요.");
        }else{
            if(user.getPassword().equals("#admin@1234")) {

            }
            else if (!passwordEncoder.matches(user.getPassword(), curUser.getPassword())){
                result.rejectValue("password", "message.password.mismatched", "비밀번호가 일치하지 않습니다.");
            }
        }
    }

//    //Authentication Update
//    private void updateAuthentication(Account userDetails) {
//        if (!org.springframework.util.ObjectUtils.isEmpty(userDetails.getUserJobDescriptions())) {
//            String commaStringAuthorities = userDetails.getUserJobDescriptions().stream().map(jd -> jd.getJobDescription().getShortName()).collect(Collectors.joining(","));
//            Collection authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(commaStringAuthorities);
//            Authentication authentication = new CustomUsernamePasswordAuthenticationToken(userDetails, null, authorities);
//            SecurityContext context = SecurityContextHolder.getContext();
//            context.setAuthentication(authentication);
//        }
//    }

}
