package com.cauh.iso.admin.controller;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.EntityAudit;
import com.cauh.common.repository.UserRepository;
import com.cauh.iso.admin.domain.constant.AuditComponent;
import com.cauh.iso.admin.service.ChangeAuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
    public String changeControl(@PageableDefault(size = 15) Pageable pageable,
            @PathVariable(value = "audit", required = false) String auditString, RedirectAttributes attributes, Model model) {
        if(StringUtils.isEmpty(auditString)) {
            return "redirect:/admin/change-control/account";
        }

        AuditComponent auditComponent = getAuditComponent(auditString);
        if(ObjectUtils.isEmpty(auditComponent)) {
            attributes.addFlashAttribute("messageType", "danger");
            attributes.addFlashAttribute("message", "존재하지 않는 경로입니다.");

            return "redirect:/admin/change-control/account";
        }

        Page<EntityAudit> revisionAuditList = changeAuditService.getRevisionAuditList(auditComponent.getClassType(), pageable);

        revisionAuditList.getContent().stream().forEach(t ->{
            log.info("JD : {} ", ((Account)t.getEntity()).getCommaJobTitle());
        });

        model.addAttribute("auditList", revisionAuditList);
        model.addAttribute("viewName", auditComponent.getViewName());

        return "admin/audit/changeControl";
    }


    //현재 다시 미사용
    @GetMapping("/ajax/change-control/{audit}")
    @ResponseBody
    public List<EntityAudit> getChangeList(@PathVariable("audit") String auditString) {
        AuditComponent auditComponent = getAuditComponent(auditString);
        List<EntityAudit> resultList = changeAuditService.getRevisionAuditList(auditComponent.getClassType());

        log.info("Entity Audit Info : {}", resultList);

        return resultList;
    }

    private AuditComponent getAuditComponent(String auditString) {
        AuditComponent auditComponent = null;

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
