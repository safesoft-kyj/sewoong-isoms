package com.cauh.iso.admin.validator;

import com.cauh.common.entity.Department;
import com.cauh.common.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
@Slf4j
public class DepartmentValidator implements Validator {

    private final DepartmentRepository departmentRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        Department department = (Department)o;

        if(StringUtils.isEmpty(department.getName())){
            errors.rejectValue("department.name", "message.empty", "필수 입력값입니다.");
        }

        if(!ObjectUtils.isEmpty(department.getId())){
            Integer childDepartmentCount = departmentRepository.countAllByParentDepartmentAndIsYn(department, "N");
            log.info("Count : {}", childDepartmentCount);
            if(department.getIsYn().equals("N") && childDepartmentCount > 0) {
                errors.rejectValue("department.isYn", "message.disable.departments", "하위 부서가 있는 부서는 비활성화 할 수 없습니다.");
            }
        }
    }
}
