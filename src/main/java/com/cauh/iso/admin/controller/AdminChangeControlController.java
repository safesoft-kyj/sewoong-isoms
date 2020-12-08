package com.cauh.iso.admin.controller;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.AccountAudit;
import com.cauh.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @PersistenceContext
    private EntityManager entityManager;

    public List<AccountAudit> getRevisionAccountList(){
        final AuditReader auditReader = AuditReaderFactory.get(entityManager);

        //TODO:: suffix를 변경해서 검색하는 것으로 어떻게 해야하나??
        AuditQuery auditQuery = auditReader.createQuery().forRevisionsOfEntity(Account.class, false, false);
        auditQuery.addOrder(AuditEntity.revisionNumber().desc());

        //Paging - page size
        auditQuery.setMaxResults(20);

        List<Object[]> histories = auditQuery.getResultList();

        return histories.stream().map(history -> AccountAudit.builder()
                        .account((Account) history[0])
                        .revisionEntity((DefaultRevisionEntity) history[1])
                        .revisionType((RevisionType) history[2])
                        .propertiesChanged((Set<String>) history[3]).build()).collect(toList());
    }

    @GetMapping("/admin/change-control")
    public String changeControl(@PageableDefault(sort = {"createdDate"}, direction = Sort.Direction.DESC, size = 15) Pageable pageable, Model model) {
        //TODO :: 작업 필요
        log.info("Account Audit List : {}", getRevisionAccountList());

        model.addAttribute("changeLog", getRevisionAccountList());

        return "admin/audit/changeControl";
    }

}
