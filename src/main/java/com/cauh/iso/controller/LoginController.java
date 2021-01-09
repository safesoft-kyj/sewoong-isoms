package com.cauh.iso.controller;

import com.cauh.common.entity.Account;
import com.cauh.common.security.annotation.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class LoginController {
    @GetMapping("/login")
    public String login(Authentication authentication) {
        if(ObjectUtils.isEmpty(authentication)) {
            return "login";
        } else {
            return "redirect:/notice";
        }
    }

    @GetMapping("/password-change")
    public String passwordChange(@CurrentUser Account user){


        return "common/";
    }
}