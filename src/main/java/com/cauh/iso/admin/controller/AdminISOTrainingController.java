package com.cauh.iso.admin.controller;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.Department;
import com.cauh.common.entity.QAccount;
import com.cauh.common.repository.UserRepository;
import com.cauh.common.security.annotation.CurrentUser;
import com.cauh.iso.admin.service.DepartmentService;
import com.cauh.iso.domain.*;
import com.cauh.iso.domain.constant.DocumentStatus;
import com.cauh.iso.domain.constant.ISOType;
import com.cauh.iso.domain.constant.TrainingStatus;
import com.cauh.iso.repository.ISOTrainingMatrixRepository;
import com.cauh.iso.repository.TrainingMatrixRepository;
import com.cauh.iso.service.ISOOfflineTrainingService;
import com.cauh.iso.service.ISOService;
import com.cauh.iso.service.ISOTrainingPeriodService;
import com.cauh.iso.utils.DateUtils;
import com.cauh.iso.validator.ISOTrainingPeriodValidator;
import com.cauh.iso.xdocreport.ISOTrainingCertificationService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping({"/admin", "/ajax/admin"})
@RequiredArgsConstructor
@SessionAttributes({"isoTrainingPeriod", "isoMap"})
@Slf4j
public class AdminISOTrainingController {

    private final ISOOfflineTrainingService isoOfflineTrainingService;
    private final ISOTrainingPeriodService isoTrainingPeriodService;
    private final ISOService isoService;
    private final ISOTrainingPeriodValidator isoTrainingPeriodValidator;
    private final ISOTrainingCertificationService isoTrainingCertificationService;
    private final TrainingMatrixRepository trainingMatrixRepository;
    private final DepartmentService departmentService;
    private final UserRepository userRepository;

    @GetMapping("/training/iso/offline-training")
    public String offlineTraining(@PageableDefault(size = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("isoOfflineTraining", isoOfflineTrainingService.findAll(pageable));
        return "admin/training/offline/iso/list";
    }

    @GetMapping("/training/iso/offline-training/{id}")
    public String offlineTraining(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("isoOfflineTraining", isoOfflineTrainingService.findById(id).get());
        return "admin/training/offline/iso/view";
    }

    /**
     * Training 이력 반영 및 반영 적용 메일 알림
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/training/iso/offline-training/{id}")
    public String offlineTraining(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        ISOOfflineTraining savedOfflineTraining = isoOfflineTrainingService.isoOfflineTrainingApply(id);
        isoOfflineTrainingService.sendApplyEmail(savedOfflineTraining);

        attributes.addFlashAttribute("message", "트레이닝 이력이 반영 되었습니다.");
        return "redirect:/admin/training/iso/offline-training";
    }

    @DeleteMapping("/training/iso/offline-training/{id}")
    public String deleteOfflineTraining(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        isoOfflineTrainingService.delete(id);

        attributes.addFlashAttribute("message", "Offline Training 요청 정보가 삭제 되었습니다.");
        return "redirect:/admin/training/iso/offline-training";
    }

    @GetMapping("/training/iso/trainingLog")
    public String teamDeptTrainingLog2(@PageableDefault(size = 25) Pageable pageable,
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
            department.setChildDepartments(departmentService.getChildDepartment(department));
            model.addAttribute("teamList", department.getChildDepartments());

            if (!ObjectUtils.isEmpty(teamId)) {
                department = new Department(teamId);
                QAccount qUser = QAccount.account;
                BooleanBuilder userBuilder = new BooleanBuilder();
                userBuilder.and(qUser.department.eq(department));
                model.addAttribute("userList", userRepository.findAll(userBuilder, qUser.engName.asc()));
            }
        }

        model.addAttribute("isoTrainingLog", trainingMatrixRepository.getISOTrainingList(department, userId, ISOType.ISO_14155, pageable));
        return "iso/training/teamDeptTrainingLog2";
    }

    @GetMapping("/training/iso/training-certification")
    public String trainingCertification(Model model){

        return "admin/iso/certList";
    }


    @GetMapping("/training/iso/trainingCertList")
    @ResponseBody
    public List<ISOTrainingCertificationDTO> ajaxTrainingCertification() {
        QISOTrainingCertification qisoTrainingCertification = QISOTrainingCertification.iSOTrainingCertification;
        BooleanBuilder builder = new BooleanBuilder();

        Iterable<ISOTrainingCertification> isoTrainingCertifications = isoTrainingCertificationService.findAll(builder, qisoTrainingCertification.id.desc());
        List<ISOTrainingCertification> isoTrainingCertificationList = StreamSupport.stream(isoTrainingCertifications.spliterator(), false).collect(Collectors.toList());
        log.info("@Test : {}", isoTrainingCertificationList);

        int size = isoTrainingCertificationList.size();
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.set(size);
        List<ISOTrainingCertificationDTO> isoTrainingCertificationDTOS = new ArrayList<>();
        isoTrainingCertificationList.forEach(cert -> {
            ISOTrainingCertificationDTO dto = new ISOTrainingCertificationDTO();

            dto.setIndex(atomicInteger.getAndDecrement());
            dto.setCertNo(cert.getId());
            dto.setName(cert.getUser().getName());
            dto.setTeamDept(cert.getUser().getTeamDept());
            dto.setRole(cert.getUser().getCommaJobTitle());
            dto.setIsoType(cert.getIso().getIsoType().getLabel());
            dto.setTrainingTitle(cert.getIso().getTitle());
            dto.setCompletionDate(DateUtils.format(cert.getIsoTrainingLog().getCompleteDate(), "dd-MMM-yyyy").toUpperCase());

            isoTrainingCertificationDTOS.add(dto);
        });

        return isoTrainingCertificationDTOS;
    }

}
