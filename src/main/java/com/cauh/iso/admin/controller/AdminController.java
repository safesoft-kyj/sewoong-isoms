package com.cauh.iso.admin.controller;

import com.cauh.iso.repository.ApprovalRepository;
import com.cauh.iso.repository.OfflineTrainingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminController {
    private final ApprovalRepository approvalRepository;
    private final OfflineTrainingRepository offlineTrainingRepository;

    @GetMapping("/admin/dashboard")
    @Transactional(readOnly = true)
    public String dashboard(Model model) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());

        model.addAttribute("approvalList", approvalRepository.findTop10());
        model.addAttribute("offlineTrainingList", offlineTrainingRepository.findAll(pageable));
        return "admin/dashboard";
    }
}
