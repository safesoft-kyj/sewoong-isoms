package com.cauh.iso.admin.controller;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.RevisionAudit;
import com.cauh.common.repository.UserRepository;
import com.cauh.iso.admin.domain.constant.AuditComponent;
import com.cauh.iso.admin.service.ChangeAuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminChangeControlController {

    private final UserRepository userRepository;
    private final ChangeAuditService changeAuditService;


    @GetMapping({"/admin/change-control", "/admin/change-control/{auditEntity}"})
    public String changeControl(@PageableDefault(sort = {"revisionEntity.timestamp"}, direction = Sort.Direction.DESC, size = 15) Pageable pageable,
                                @PathVariable(value = "auditEntity", required = false) String auditEntity, Model model) {
        //TODO :: 작업 필요
        AuditComponent auditComponent = null;

        if(StringUtils.isEmpty(auditEntity)) {
            return "redirect:/admin/change-control/account";
        } else if(auditEntity.equals("account")) {
            auditComponent = AuditComponent.ACCOUNT;
        }

        Page<RevisionAudit> changeLogs = changeAuditService.getRevisionAuditList(auditComponent.getClassType(), pageable);
        log.debug("Change Logs : {}", changeLogs.getContent());

        model.addAttribute("changeLogs", changeLogs);
        model.addAttribute("viewName", auditComponent.getViewName());

        return "admin/audit/changeControl";
    }

}
