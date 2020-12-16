package com.cauh.iso.repository;

import com.cauh.iso.domain.DepartmentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DepartmentTreeRepository {

    private final JdbcTemplate jdbcTemplate;

    protected String query;

    public List<DepartmentDTO> findAllTree(){
        query = "SELECT id, pid, name, depth_fullname " +
                "FROM vw_department_tree";

        List<DepartmentDTO> depts = jdbcTemplate.query(query,
                (rs, rowNum) -> DepartmentDTO.builder()
                        .id(rs.getInt("id"))
                        .pid(rs.getInt("pid"))
                        .name(rs.getString("name"))
                        .depthFullname(rs.getString("depth_fullname"))
                        .build()
        );
        
        return depts;
    }

}
