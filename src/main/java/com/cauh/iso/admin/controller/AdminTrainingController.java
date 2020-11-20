package com.cauh.iso.admin.controller;

import com.cauh.common.entity.QAccount;
import com.cauh.common.mapper.DeptUserMapper;
import com.cauh.common.repository.UserRepository;
import com.cauh.iso.domain.*;
import com.cauh.iso.domain.constant.DocumentStatus;
import com.cauh.iso.domain.constant.DocumentType;
import com.cauh.iso.domain.constant.TrainingType;
import com.cauh.iso.repository.OfflineTrainingRepository;
import com.cauh.iso.repository.SOPTrainingMatrixRepository;
import com.cauh.iso.service.DocumentVersionService;
import com.cauh.iso.service.OfflineTrainingService;
import com.cauh.iso.service.TrainingMatrixService;
import com.cauh.iso.service.TrainingPeriodService;
import com.cauh.iso.utils.DateUtils;
import com.cauh.iso.validator.TrainingPeriodValidator;
import com.cauh.iso.xdocreport.IndexReportService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@SessionAttributes({"trainingPeriod", "sopMap"})
@Slf4j
public class AdminTrainingController {
    private final TrainingMatrixService trainingMatrixService;
    private final OfflineTrainingService offlineTrainingService;
    private final TrainingPeriodService trainingPeriodService;
    private final DocumentVersionService documentVersionService;
    private final TrainingPeriodValidator trainingPeriodValidator;
    private final DeptUserMapper deptUserMapper;
    private final UserRepository userRepository;
    private final SOPTrainingMatrixRepository sopTrainingMatrixRepository;

    @Value("${gw.userTbl}")
    private String gwUserTbl;

    @Value("${gw.deptTbl}")
    private String gwDeptTbl;

    @GetMapping("/training/{type}/matrix")
    public String trainingMatrix(
            @PathVariable DocumentType type,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 20) Pageable pageable, Model model) {
        model.addAttribute("trainingMatrix", trainingMatrixService.findAll(pageable));
        return "admin/training/matrix/list";
    }

    @PostMapping("/training/{type}/matrix")
    public String uploadTrainingMatrix(@ModelAttribute TrainingMatrixFile trainingMatrixFile) {
        trainingMatrixService.save(trainingMatrixFile);

        return "redirect:/admin/training/matrix";
    }

    //TODO :: YSH
    @GetMapping("/training/{dType}/offline-training")
    public String offlineTraining(@PathVariable("dType") DocumentType type,
            @PageableDefault(size = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        log.info("OfflineTraining Log Type : {}", type);
        model.addAttribute("offlineTraining", offlineTrainingService.findAll(pageable));
        return "admin/training/offline/list";
    }

    //TODO :: YSH
    @GetMapping("/training/{dType}/offline-training/{id}")
    public String offlineTraining(@PathVariable("dType") DocumentType documentType,
            @PathVariable("id") Integer id, Model model) {
        model.addAttribute("offlineTraining", offlineTrainingService.findById(id).get());
        return "admin/training/offline/view";
    }

    //TODO :: YSH
    @PostMapping("/training/{dType}/offline-training/{id}")
    public String offlineTraining(@PathVariable("dType") DocumentType documentType,
            @PathVariable("id") Integer id, RedirectAttributes attributes) {
        OfflineTraining savedOfflineTraining = offlineTrainingService.offlineTrainingApply(id);
        offlineTrainingService.sendApplyEmail(savedOfflineTraining);

        attributes.addFlashAttribute("message", "트레이닝 이력이 반영 되었습니다.");
        return "redirect:/admin/training/offline-training";
    }

    @DeleteMapping("/training/{type}/offline-training/{id}")
    public String deleteOfflineTraining(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        offlineTrainingService.delete(id);

        attributes.addFlashAttribute("message", "Offline Training 요청 정보가 삭제 되었습니다.");
        return "redirect:/admin/training/offline-training";
    }

    @GetMapping("/training/{dType}/refresh-training")
    public String refreshTraining(@PathVariable("dType") DocumentType type,
            @PageableDefault(size = 25, sort = {"startDate"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {

        log.info("Type : {}", type);

        QTrainingPeriod qTrainingPeriod = QTrainingPeriod.trainingPeriod;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qTrainingPeriod.documentVersion.document.type.eq(type));
        builder.and(qTrainingPeriod.trainingType.eq(TrainingType.REFRESH));

        model.addAttribute("refreshTraining", trainingPeriodService.findAll(builder, pageable));

        return "admin/training/refresh/list";
    }

    //refresh 삭제 시,
    @DeleteMapping("/training/{dType}/refresh-training")
    public String removeRefreshTraining(@PathVariable("dType") DocumentType type,
            @RequestParam("id") Integer id, RedirectAttributes attributes) {
        trainingPeriodService.deleteByIdAndType(id, type);
        attributes.addFlashAttribute("message", "삭제 되었습니다.");
        return "redirect:/admin/training/refresh-training";
    }

    //SOP 및 ISO refresh-training 신규 및 수정 화면 이동 시,
    @GetMapping({"/training/{dType}/refresh-training/new", "/training/{dType}/refresh-training/{id}"})
    public String refreshTraining(@PathVariable("dType") DocumentType documentType,
                                  @PathVariable(value = "id", required = false) Integer id, Model model) {
        QDocumentVersion qDocumentVersion = QDocumentVersion.documentVersion;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qDocumentVersion.document.type.eq(documentType)
                .and(qDocumentVersion.status.in(DocumentStatus.EFFECTIVE)));
        Iterable<DocumentVersion> documentVersions = documentVersionService.findAll(builder);

        model.addAttribute("sopMap", StreamSupport.stream(documentVersions.spliterator(), false)
                .collect(Collectors.toMap(s -> s.getId(), s -> s.getDocument().getDocId() + " " + s.getDocument().getTitle() + "v" + s.getVersion())));

        model.addAttribute("trainingPeriod", ObjectUtils.isEmpty(id) ? new TrainingPeriod() : trainingPeriodService.findById(id).get());

        return "admin/training/refresh/edit";
    }

    //SOP 및 ISO refresh-training 신규 및 수정 작업 수행 시,
    @PostMapping({"/training/{dType}/refresh-training/new", "/training/{dType}/refresh-training/{id}"})
    public String refreshTraining(@PathVariable("dType") DocumentType documentType,
                                @PathVariable(value = "id", required = false) Integer id, @ModelAttribute("trainingPeriod") TrainingPeriod trainingPeriod,
                                BindingResult result, SessionStatus status, RedirectAttributes attributes) {
        trainingPeriodValidator.validate(trainingPeriod, result);

        if(result.hasErrors()) {
            return "admin/training/refresh/edit";
        }
        if(!ObjectUtils.isEmpty(id)) {
            trainingPeriod.setId(id);
            trainingPeriod.setDocumentType(documentType);
        }

        trainingPeriodService.saveOrUpdateRefreshTraining(trainingPeriod);
        status.setComplete();
        attributes.addFlashAttribute("Refresh Training이 등록 되었습니다.");
        return "redirect:/admin/training/refresh-training";
    }

    @GetMapping("/training/{dType}/trainingLog")
    public String teamDeptTrainingLog2(@PathVariable("dType") DocumentType documentType,
                                       @PageableDefault(size = 25) Pageable pageable,
//                                       @CurrentUser User user,
                                       @RequestParam(value = "deptCode", required = false) String deptCode,
                                       @RequestParam(value = "teamCode", required = false) String teamCode,
                                       @RequestParam(value = "userId", required = false) Integer userId,
                                       @RequestParam(value = "docId", required = false) String docId,
                                       Model model) {
        Map<String, String> param = new HashMap<>();
        param.put("gwUserTbl", gwUserTbl);
        param.put("gwDeptTbl", gwDeptTbl);
        model.addAttribute("deptList", deptUserMapper.findAllDept(param));

        if(StringUtils.isEmpty(deptCode) == false) {
            param.put("deptCode", deptCode);
            model.addAttribute("teamList", deptUserMapper.findByDeptTeam(param));
        }

        if(ObjectUtils.isEmpty(teamCode) == false) {
            QAccount qUser = QAccount.account;
            BooleanBuilder userBuilder = new BooleanBuilder();
//            userBuilder.and(qUser.teamCode.eq(teamCode));
            model.addAttribute("userList", userRepository.findAll(userBuilder, qUser.engName.asc()));
        }


//        model.addAttribute("trainingLog", trainingLogService.findAll(builder, pageable));
        BooleanBuilder docStatus = new BooleanBuilder();
        QDocumentVersion qDocumentVersion = QDocumentVersion.documentVersion;

        docStatus.and(qDocumentVersion.status.in(DocumentStatus.APPROVED, DocumentStatus.EFFECTIVE));
        model.addAttribute("trainingLog", sopTrainingMatrixRepository.getTrainingList(deptCode, teamCode, userId, docId, null, pageable, docStatus));
        return "training/teamDeptTrainingLog2";
    }

    @PostMapping("/training/{dType}/trainingLog")
    @Transactional(readOnly = true)
    public void downloadTeamDeptTrainingLog(@PathVariable("dType") DocumentType documentType,
                                              @RequestParam(value = "deptCode", required = false) String deptCode,
                                              @RequestParam(value = "teamCode", required = false) String teamCode,
                                              @RequestParam(value = "userId", required = false) Integer userId,
                                              @RequestParam(value = "docId", required = false) String docId,
                                              HttpServletResponse response) throws Exception {
        List<MyTraining> trainingList = sopTrainingMatrixRepository.getDownloadTrainingList(deptCode, teamCode, userId, docId, null);
        InputStream is = null;
        if(documentType == DocumentType.ISO){
            //TODO :: 수정 필요.
            is = IndexReportService.class.getResourceAsStream("trainingLog.xlsx");
        } else if(documentType == DocumentType.SOP) {
            is = IndexReportService.class.getResourceAsStream("trainingLog.xlsx");
        }

        Context context = new Context();
        context.putVar("trainings", trainingList);
        response.setHeader("Content-Disposition", "attachment; filename=\"TrainingLog("+ DateUtils.format(new Date(), "yyyyMMdd")+").xlsx\"");
        JxlsHelper.getInstance().processTemplate(is, response.getOutputStream(), context);
    }
}
