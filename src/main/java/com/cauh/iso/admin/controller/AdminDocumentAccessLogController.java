package com.cauh.iso.admin.controller;

import com.cauh.iso.service.DocumentAccessLogService;
import com.cauh.iso.service.ISOAccessLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminDocumentAccessLogController {
    private final DocumentAccessLogService documentAccessLogService;
    private final ISOAccessLogService isoAccessLogService;

    @GetMapping("/admin/document/accessLog")
    public String accessLog(@PageableDefault(sort = {"createdDate"}, direction = Sort.Direction.DESC, size = 15) Pageable pageable, Model model) {
        model.addAttribute("accessLog", documentAccessLogService.findAll(pageable));

        return "admin/sop/accessLog";
    }

    @GetMapping("/admin/iso/accessLog")
    public String isoAccessLog(@PageableDefault(sort = {"createdDate"}, direction = Sort.Direction.DESC, size = 15) Pageable pageable, Model model) {
        model.addAttribute("isoAccessLog", isoAccessLogService.findAll(pageable));

        return "admin/iso/accessLog";
    }

}
