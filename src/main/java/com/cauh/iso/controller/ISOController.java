package com.cauh.iso.controller;

import com.cauh.common.entity.Account;
import com.cauh.common.security.annotation.CurrentUser;
import com.cauh.iso.domain.QDocumentVersion;
import com.cauh.iso.domain.QISO;
import com.cauh.iso.domain.QNotice;
import com.cauh.iso.domain.TrainingMatrixFile;
import com.cauh.iso.domain.constant.ApprovalLineType;
import com.cauh.iso.service.ISOService;
import com.cauh.iso.service.NoticeService;
import com.cauh.iso.service.TrainingMatrixService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Optional;

@Controller
@Slf4j
@SessionAttributes({"iso"})
@RequiredArgsConstructor
public class ISOController {

    private final ISOService isoService;
    private final NoticeService noticeService;
    private final TrainingMatrixService trainingMatrixService;

    @GetMapping("/iso-14155")
    public String ISOlist(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 15) Pageable pageable, @CurrentUser Account user, Model model){
        QISO qISO = QISO.iSO;

        //공지사항 리스트
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qISO.deleted.eq(false));
        model.addAttribute("isoList", noticeService.getList(builder, pageable));

        //공지사항(상단공지)
        Date today = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        builder.and(qISO.topViewEndDate.goe(Date.valueOf(format.format(today))));
        model.addAttribute("topISOList", noticeService.getTopNotices(builder));

        Optional<TrainingMatrixFile> optionalTrainingMatrixFile = trainingMatrixService.findFirstByOrderByIdDesc();
        model.addAttribute("trainingMatrixFile", optionalTrainingMatrixFile.isPresent() ? optionalTrainingMatrixFile.get() : null);
        return "iso/iso14155/list";
    }

}
