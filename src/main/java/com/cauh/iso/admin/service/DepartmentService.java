package com.cauh.iso.admin.service;

import com.cauh.common.entity.Department;
import com.cauh.common.entity.JobDescription;
import com.cauh.common.entity.QDepartment;
import com.cauh.common.repository.DepartmentRepository;
import com.cauh.iso.domain.MyTrainingMatrix;
import com.cauh.iso.repository.DepartmentTreeRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.cauh.iso.domain.QTrainingMatrix.trainingMatrix;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentTreeRepository departmentTreeRepository;

    @Transactional
    public void saveAll(Department department) {
        Department savedDepartment = departmentRepository.save(department);
        log.info("저장된 Department : {}", savedDepartment);
    }

    public TreeMap<String, String> getDeptMap(){
        Map<String, String> deptMap = departmentTreeRepository.findAllTree().stream()
                .collect(Collectors.toMap(d -> String.format("%03d", d.getId()), d -> d.getDepthFullname()));

        TreeMap<String, String> sortedDeptMap = new TreeMap<>();
        sortedDeptMap.putAll(deptMap);

        return sortedDeptMap;
    }

    public List<Department> getParentDepartment(){
        return departmentRepository.findAllByParentDepartmentIsNull();
    }

    public List<Department> getChildDepartment(Department department){
        return departmentRepository.findAllByParentDepartment(department);
    }

}
