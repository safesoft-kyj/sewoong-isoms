package com.cauh.iso.admin.controller;

import com.cauh.common.entity.Department;
import com.cauh.common.entity.QAccount;
import com.cauh.common.mapper.DeptUserMapper;
import com.cauh.common.repository.DepartmentRepository;
import com.cauh.common.repository.UserRepository;
import com.cauh.iso.admin.service.DepartmentService;
import com.cauh.iso.domain.*;
import com.cauh.iso.domain.constant.DocumentStatus;
import com.cauh.iso.domain.constant.DocumentType;
import com.cauh.iso.domain.constant.TrainingType;
import com.cauh.iso.repository.TrainingMatrixRepository;
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
    private final DepartmentService departmentService;
    private final UserRepository userRepository;
    private final TrainingMatrixRepository trainingMatrixRepository;

//    @Value("${gw.userTbl}")
//    private String gwUserTbl;
//
//    @Value("${gw.deptTbl}")
//    private String gwDeptTbl;

    @GetMapping("/training/sop/matrix")
    public String trainingMatrix(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 20) Pageable pageable, Model model) {
        model.addAttribute("trainingMatrix", trainingMatrixService.findAll(pageable));
        return "admin/training/matrix/list";
    }

    @PostMapping("/training/sop/matrix")
    public String uploadTrainingMatrix(@ModelAttribute TrainingMatrixFile trainingMatrixFile) {
        trainingMatrixService.save(trainingMatrixFile);

        return "redirect:/admin/training/matrix";
    }

    @GetMapping("/training/sop/offline-training")
    public String offlineTraining(@PageableDefault(size = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("offlineTraining", offlineTrainingService.findAll(pageable));
        return "admin/training/offline/list";
    }

    @GetMapping("/training/sop/offline-training/{id}")
    public String offlineTraining(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("offlineTraining", offlineTrainingService.findById(id).get());
        return "admin/training/offline/view";
    }


    @PostMapping("/training/sop/offline-training/{id}")
    public String offlineTraining(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        OfflineTraining savedOfflineTraining = offlineTrainingService.offlineTrainingApply(id);
        offlineTrainingService.sendApplyEmail(savedOfflineTraining);

        attributes.addFlashAttribute("message", "트레이닝 이력이 반영 되었습니다.");
        return "redirect:/admin/training/offline-training";
    }

    @DeleteMapping("/training/sop/offline-training/{id}")
    public String deleteOfflineTraining(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        offlineTrainingService.delete(id);

        attributes.addFlashAttribute("message", "Offline Training 요청 정보가 삭제 되었습니다.");
        return "redirect:/admin/training/offline-training";
    }

    @GetMapping("/training/sop/refresh-training")
    public String refreshTraining(@PageableDefault(size = 25, sort = {"startDate"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        QTrainingPeriod qTrainingPeriod = QTrainingPeriod.trainingPeriod;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qTrainingPeriod.documentVersion.document.type.eq(DocumentType.SOP));
        builder.and(qTrainingPeriod.trainingType.eq(TrainingType.REFRESH));

        model.addAttribute("refreshTraining", trainingPeriodService.findAll(builder, pageable));

        return "admin/training/refresh/list";
    }

    //refresh 삭제 시,
    @DeleteMapping("/training/sop/refresh-training")
    public String removeRefreshTraining(@RequestParam("id") Integer id, RedirectAttributes attributes) {
        trainingPeriodService.deleteById(id);
        attributes.addFlashAttribute("message", "삭제 되었습니다.");
        return "redirect:/admin/training/refresh-training";
    }

    //SOP 및 ISO refresh-training 신규 및 수정 화면 이동 시,
    @GetMapping({"/training/sop/refresh-training/new", "/training/sop/refresh-training/{id}"})
    public String refreshTraining(@PathVariable(value = "id", required = false) Integer id, Model model) {
        QDocumentVersion qDocumentVersion = QDocumentVersion.documentVersion;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qDocumentVersion.document.type.eq(DocumentType.SOP)
                .and(qDocumentVersion.status.in(DocumentStatus.EFFECTIVE)));
        Iterable<DocumentVersion> documentVersions = documentVersionService.findAll(builder);

        model.addAttribute("sopMap", StreamSupport.stream(documentVersions.spliterator(), false)
                .collect(Collectors.toMap(s -> s.getId(), s -> s.getDocument().getDocId() + " " + s.getDocument().getTitle() + "v" + s.getVersion())));

        model.addAttribute("trainingPeriod", ObjectUtils.isEmpty(id) ? new TrainingPeriod() : trainingPeriodService.findById(id).get());

        return "admin/training/refresh/edit";
    }

    //SOP 및 ISO refresh-training 신규 및 수정 작업 수행 시,
    @PostMapping({"/training/sop/refresh-training/new", "/training/sop/refresh-training/{id}"})
    public String refreshTraining(@PathVariable(value = "id", required = false) Integer id, @ModelAttribute("trainingPeriod") TrainingPeriod trainingPeriod,
                                BindingResult result, SessionStatus status, RedirectAttributes attributes) {
        trainingPeriodValidator.validate(trainingPeriod, result);

        if(result.hasErrors()) {
            return "admin/training/refresh/edit";
        }
        if(!ObjectUtils.isEmpty(id)) {
            trainingPeriod.setId(id);
        }

        trainingPeriodService.saveOrUpdateRefreshTraining(trainingPeriod);
        status.setComplete();
        attributes.addFlashAttribute("Refresh Training이 등록 되었습니다.");
        return "redirect:/admin/training/refresh-training";
    }

    @GetMapping("/training/sop/trainingLog")
    public String teamDeptTrainingLog2(@PageableDefault(size = 25) Pageable pageable,
//                                       @CurrentUser User user,
                                       @RequestParam(value = "deptId", required = false) Integer deptId,
                                       @RequestParam(value = "teamId", required = false) Integer teamId,
                                       @RequestParam(value = "userId", required = false) Integer userId,
                                       @RequestParam(value = "docId", required = false) String docId,
                                       Model model) {
        //부서 목록
        model.addAttribute("deptList", departmentService.getParentDepartment());
        Department department = null;

        if(!ObjectUtils.isEmpty(deptId)) {
            department = new Department(deptId);
            model.addAttribute("teamList", departmentService.getChildDepartment(department));

            if (!ObjectUtils.isEmpty(teamId)) {
                department = new Department(teamId);
                QAccount qUser = QAccount.account;
                BooleanBuilder userBuilder = new BooleanBuilder();
                userBuilder.and(qUser.department.eq(department));
                model.addAttribute("userList", userRepository.findAll(userBuilder, qUser.engName.asc()));
            }
        }

        BooleanBuilder docStatus = new BooleanBuilder();
        QDocumentVersion qDocumentVersion = QDocumentVersion.documentVersion;

        //Document Type을 지정. (ISO or SOP)
        docStatus.and(qDocumentVersion.status.in(DocumentStatus.APPROVED, DocumentStatus.EFFECTIVE));
        model.addAttribute("trainingLog", trainingMatrixRepository.getTrainingList(department, userId, docId, null, pageable, docStatus));

//        model.addAttribute("trainingLog", trainingLogService.findAll(builder, pageable));
        return "training/teamDeptTrainingLog2";
    }

    @PostMapping("/training/sop/trainingLog")
//    @Transactional(readOnly = true)
    @Transactional
    public void downloadTeamDeptTrainingLog(@RequestParam(value = "deptId", required = false) Integer deptId,
                                            @RequestParam(value = "teamId", required = false) Integer teamId,
                                            @RequestParam(value = "userId", required = false) Integer userId,
                                            @RequestParam(value = "docId", required = false) String docId,
                                            HttpServletResponse response) throws Exception {

        //team, user 정보는 있는데 부서정보가 없는 경우
        if((!ObjectUtils.isEmpty(teamId) || !ObjectUtils.isEmpty(userId)) && ObjectUtils.isEmpty(deptId)) {
            log.error("부서 정보가 잘못되었습니다. deptId : {}, teamId = {}, userId = {}", deptId, teamId, userId);
            return;
        }

        Department department = null;

        //팀정보가 있으면 팀, 없으면 부서정보.
        if(!ObjectUtils.isEmpty(deptId)) {
            if(!ObjectUtils.isEmpty(teamId)) {
                department = departmentService.getDepartmentById(teamId);
            } else {
                department = departmentService.getDepartmentById(deptId);
            }
        }

        List<MyTraining> trainingList = trainingMatrixRepository.getDownloadTrainingList(department, userId, docId, null);
        InputStream is = IndexReportService.class.getResourceAsStream("trainingLog.xlsx");

        Context context = new Context();
        context.putVar("trainings", trainingList);
        response.setHeader("Content-Disposition", "attachment; filename=\"TrainingLog("+ DateUtils.format(new Date(), "yyyyMMdd")+").xlsx\"");
        JxlsHelper.getInstance().processTemplate(is, response.getOutputStream(), context);
    }
}
