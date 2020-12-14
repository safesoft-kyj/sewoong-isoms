package com.cauh.iso.admin.service;

import com.cauh.common.entity.Department;
import com.cauh.common.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Transactional
    public void saveAll(Department department) {
        Department savedDepartment = departmentRepository.save(department);
        log.info("저장된 Department : {}", savedDepartment);
    }

}
