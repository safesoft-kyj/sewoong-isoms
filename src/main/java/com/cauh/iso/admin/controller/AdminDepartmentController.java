package com.cauh.iso.admin.controller;

import com.cauh.common.entity.Department;
import com.cauh.common.entity.JobDescription;
import com.cauh.common.entity.QAccount;
import com.cauh.common.entity.QDepartment;
import com.cauh.common.repository.DepartmentRepository;
import com.cauh.common.repository.UserRepository;
import com.cauh.iso.admin.service.DepartmentService;
import com.cauh.iso.admin.validator.DepartmentValidator;
import com.groupdocs.conversion.internal.c.a.d.a.d.B;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.util.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@SessionAttributes({"departments", "department"})
public class AdminDepartmentController {

    private final DepartmentRepository departmentRepository;
    private final DepartmentService departmentService;
    private final DepartmentValidator departmentValidator;
    private final UserRepository userRepository;

    @GetMapping("/admin/department")
    public String departments(@RequestParam(value = "action", defaultValue = "list") String action,
                       @RequestParam(value = "id", required = false) Integer id,
                       @RequestParam(value = "pid", required = false) Integer parentId,
                       @ModelAttribute("jobDescription") JobDescription jobDescription, Model model) {
        QDepartment qDepartment = QDepartment.department;
        BooleanBuilder builder = new BooleanBuilder();
        //상위 부서가 없는 부서만 불러오기
        builder.and(qDepartment.parentDepartment.isNull());
        Iterable<Department> departments = departmentRepository.findAll(builder, qDepartment.id.asc());
        model.addAttribute("departments", departments);

        if("new".equals(action)){
            model.addAttribute("department", new Department());
        }
        else if("subNew".equals(action)) {
            Department department = new Department();
            //pid가 있으면 parentDepartment에 값 입력
            if(!ObjectUtils.isEmpty(parentId)){
                Department parentDepartment = new Department(parentId);
                department.setParentDepartment(parentDepartment);
            }
            model.addAttribute("department", department);
        } else if ("edit".equals(action)) {
            Department department = departmentRepository.findById(id).get();
            model.addAttribute("department", department);
        } else if ("subEdit".equals(action)){
            Department department = departmentRepository.findById(id).get();
            log.info("SubEdit Dept : {}", department);

            model.addAttribute("department", department);
        }

        model.addAttribute("action", action);
        model.addAttribute("id", id);
        model.addAttribute("pid", parentId);

        return "/admin/department/departments";
    }

    @DeleteMapping("/admin/department")
    public String deleteDepartment(@RequestParam("id") Integer id, RedirectAttributes attributes){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);

        if(optionalDepartment.isPresent()) {
            Department department = optionalDepartment.get();

            List<Department> totalDepartments = new ArrayList<>();
            totalDepartments.addAll(department.getChildDepartments());
            totalDepartments.add(department);

            for(Department dept : totalDepartments) {
                Integer size = userRepository.countAllByDepartment(dept);
                if(size > 0) {
                    attributes.addFlashAttribute("message", "ERROR :: [ " + dept.getName() + "] 부서에 배정된 유저가 있습니다.");
                    return "redirect:/admin/department";
                }
            }
            attributes.addFlashAttribute("message", "부서 삭제가 완료되었습니다.");
            departmentRepository.delete(optionalDepartment.get());
        }
        return "redirect:/admin/department";
    }

    @PostMapping("/admin/department")
    public String editDepartment(@RequestParam(value = "action", defaultValue = "list") String action,
                                 @RequestParam(value = "id", required = false) Integer id,
                                 @ModelAttribute("department") Department department, BindingResult result,
                                 SessionStatus status, Model model, RedirectAttributes attributes) {
        //isYn 판정 - 값이 null이면 'N' 있을경우 'Y'
        if(!StringUtils.isEmpty(department.getIsYn()) && department.getIsYn().equals("Y")){
            department.setIsYn("Y");
        }else{
            department.setIsYn("N");
        }

        departmentValidator.validate(department, result);

        if(result.hasErrors()) {
            //parentDepartment가 있는 경우
            if(!ObjectUtils.isEmpty(department.getParentDepartment())){
                model.addAttribute("pid", department.getParentDepartment().getId());
            }

            model.addAttribute("id", id);
            model.addAttribute("action", action);
            return "admin/department/departments";
        }

        departmentService.saveAll(department);
        status.setComplete();
        attributes.addFlashAttribute("message", "부서 정보가 등록 되었습니다.");
        return "redirect:/admin/department";
    }
}
