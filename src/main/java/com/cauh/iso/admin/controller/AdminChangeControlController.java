package com.cauh.iso.admin.controller;

import com.cauh.common.entity.EntityAudit;
import com.cauh.common.repository.UserRepository;
import com.cauh.iso.admin.domain.constant.AuditComponent;
import com.cauh.iso.admin.service.ChangeAuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminChangeControlController {

    private final UserRepository userRepository;
    private final ChangeAuditService changeAuditService;

    @GetMapping({"/change-control", "/change-control/{audit}"})
    public String changeControl(@PathVariable(value = "audit", required = false) String auditString, RedirectAttributes attributes, Model model) {
        if(StringUtils.isEmpty(auditString)) {
            return "redirect:/admin/change-control/account";
        }

        AuditComponent auditComponent = getAuditComponent(auditString);
        if(ObjectUtils.isEmpty(auditComponent)) {
            attributes.addFlashAttribute("type", "danger");
            attributes.addFlashAttribute("message", "존재하지 않는 경로입니다.");

            return "redirect:/admin/change-control/account";
        }

        model.addAttribute("viewName", auditComponent.getViewName());

        return "admin/audit/changeControl";
    }

    @GetMapping("/ajax/change-control/{audit}}")
    @ResponseBody
    public List<EntityAudit> getChangeList(@PathVariable("audit") String auditString) {
        AuditComponent auditComponent = getAuditComponent(auditString);
        return changeAuditService.getRevisionAuditList(auditComponent.getClassType());
    }

    private AuditComponent getAuditComponent(String auditString) {
        AuditComponent auditComponent = null;

        //TODO :: 작업 진행 필요.
        if(auditString.equals("account")) {
            auditComponent = AuditComponent.ACCOUNT;
        } else if(auditString.equals("document")) {
            auditComponent = AuditComponent.DOCUMENT;
        } else if(auditString.equals("documentVersion")) {
            auditComponent = AuditComponent.DOCUMENT_VERSION;
        }

        return auditComponent;
    }

}
