package com.cauh.esop.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(Authentication authentication) {
        if(ObjectUtils.isEmpty(authentication)) {
            return "login";
        } else {
            return "redirect:/notice";
        }
    }
}
