package com.dtnsm.common.mapper;

import com.dtnsm.common.entity.Account;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DeptUserMapper {
    List<Map<String, String>> findAllDept(Map<String, String> param);

//    List<Map<String, String>> findByDeptTeam(String deptCode);
    List<Map<String, String>> findByDeptTeam(Map<String, String> param);

    Account findByUsername(Map<String, String> param);

    List<Map<String, String>> getAllUsers(Map<String, String> param);
}
