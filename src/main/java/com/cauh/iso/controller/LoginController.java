package com.cauh.iso.controller;

import com.cauh.common.entity.Account;
import com.cauh.common.repository.UserRepository;
import com.cauh.common.security.annotation.CurrentUser;
import com.cauh.iso.component.CurrentUserComponent;
import com.cauh.iso.validator.UserPasswordValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@SessionAttributes({"user"})
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository userRepository;
    private final UserPasswordValidator userPasswordValidator;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if(ObjectUtils.isEmpty(authentication)) {
            return "login";
        } else {
            return "redirect:/notice";
        }
    }

    @GetMapping("/password-change")
    public String passwordChange(@CurrentUser Account user, Model model){
        model.addAttribute("user", Account.builder().id(user.getId()).name(user.getName()).build());

        return "common/pwchange";
    }

    @GetMapping("/password-change/skip")
    public String passwordChangeSkip(@CurrentUser Account user) {
        user.setPasswordExpiredIgnore(true);

        return "redirect:/notice";
    }

    @PostMapping("/password-change")
    public String passwordChangeProc(@CurrentUser Account currentUser,
                                     @ModelAttribute("user") Account user,
                                     @RequestParam("newPassword") String password,
                                     RedirectAttributes attributes, BindingResult result) {
        log.info("New Password : {}", password);
        log.info("Current Password : {}", user.getPassword());

        userPasswordValidator.validate(user, result);

        if(result.hasErrors()){
            log.debug("Errors : {}", result.getAllErrors());
            return "common/pwchange";
        }

        //비밀번호 기한 설정 및 비밀번호 변경
        currentUser.setCredentialsExpiredDate(Date.from(LocalDate.now().plusDays(90).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        currentUser.setPassword(passwordEncoder.encode(password));
        Account savedUser = userRepository.save(currentUser);

        log.info("저장된 유저 : {}", savedUser);
        log.info("저장된 비밀번호 : {}", savedUser.getPassword());
        log.info("저장된 비밀번호 유효기간 : {}", savedUser.getCredentialsExpiredDate());

        return "redirect:/";
    }

    /**
     * Password change 시, 현재 Password와 비교하기.
     * @param data
     * @return
     */
    @PostMapping("/ajax/password-chanage/validation")
    @ResponseBody
    public Map<String, Boolean> passwordValidation(@CurrentUser Account user, @RequestParam("data") String data){
        Map<String, Boolean> map = new HashMap<>();
        boolean result = true;
        if(passwordEncoder.matches(data, user.getPassword())){
            result = false;
        }

        map.put("valid", result);
        return map;
    }
}