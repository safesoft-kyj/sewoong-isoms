package com.dtnsm.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DigitalBinderController {
    @GetMapping("/digitalBinder")
    public String digitalBinder() {
        return "digitalBinder/index";
    }
}
