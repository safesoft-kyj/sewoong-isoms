package com.cauh.iso.controller;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.constant.UserType;
import com.cauh.common.mapper.DeptUserMapper;
import com.cauh.common.repository.UserRepository;
import com.cauh.iso.domain.JsTreeIcon;
import com.cauh.iso.domain.JsTreeNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OrganizationController {
    private final DeptUserMapper deptUserMapper;
    private final UserRepository userRepository;

    @Value("${gw.userTbl}")
    private String gwUserTbl;

    @Value("${gw.deptTbl}")
    private String gwDeptTbl;

    @GetMapping("/common/organization/chart")
    @ResponseBody
    public List<JsTreeNode> organization() {
        Map<String, String> param = new HashMap<>();
        param.put("gwUserTbl", gwUserTbl);
        param.put("gwDeptTbl", gwDeptTbl);

        param.put("state", "1");
//        List<Map<String, String>> allUsers = deptUserMapper.getAllUsers(param);
        List<Account> accounts = userRepository.findAllByUserTypeAndEnabledOrderByNameAsc(UserType.USER, true);
        List<JsTreeNode> jsTreeNodes = new ArrayList<>();
        String mainCode = "DTNSM";
        JsTreeNode rootNode = new JsTreeNode(mainCode, "KCSG(" + accounts.size() + ")");
//        rootNode.setIcon("jstree-folder");
        rootNode.getState().setOpened(true);
        jsTreeNodes.add(rootNode);

        String deptCode;
        String teamCode;
        String korName;
        String username;
        int lev;
        boolean sex;

        for(Account u : accounts) {

            log.trace(" -- user : {}", u);
//            deptCode = StringUtils.isEmpty(u.get("deptCode")) ? mainCode : u.get("deptCode");
//            teamCode = u.get("teamCode");
//            korName = u.get("korName");
//            username = u.get("username");
//            lev = Integer.parseInt(String.valueOf(u.get("lev")));
//            sex = Boolean.valueOf(String.valueOf(u.get("sex")));

//            JsTreeNode deptNode = new JsTreeNode(deptCode, u.get("deptName"), deptCode, u.get("deptName"), teamCode, u.get("teamName"), u.get("empNo"), username, JsTreeIcon.dept);
//            JsTreeNode teamNode = new JsTreeNode(teamCode, u.get("teamName"), deptCode, u.get("deptName"), teamCode, u.get("teamName"), u.get("empNo"), username, JsTreeIcon.team);
            JsTreeNode userNode = new JsTreeNode(u.getEmpNo(), StringUtils.isEmpty(u.getPosition()) ? u.getName() : u.getName() + "(" + u.getPosition() + ")", "DEPT_CODE", u.getDeptName(), "TEAM_CODE",
                    u.getTeamName(), u.getEmpNo(), u.getUsername(),
                    JsTreeIcon.user_male);
//            if(lev == 0) {
                log.trace("KCSG - 사용자 추가 : {}", u.getName());
                rootNode.getChildren().add(userNode);
//            } else if(rootNode.getChildren().contains(deptNode)) {//부서 존재
//                log.trace("부서 존재함 deptCode : {} add user : {}", deptCode, korName);
//                JsTreeNode findDeptNode = rootNode.getChildren().get(rootNode.getChildren().indexOf(deptNode));
////                if(deptCode.equals(mainCode)) {
////                    log.trace("DtnSM - 사용자 추가 : {}", korName);
////                    findDeptNode.getChildren().add(userNode);
////                } else {
//                if (StringUtils.isEmpty(teamCode) || lev == 1) {
//                    log.trace("팀이 없는 사용자 or lev : {} 부서에 추가 : {}", lev, korName);
//                    findDeptNode.getChildren().add(userNode);
//                } else {
//                    if(findDeptNode.getChildren().contains(teamNode)) {
//                        log.trace("{}/{} 팀이 존재함 사용자 추가 : {}", deptCode, teamCode, korName);
//                        JsTreeNode findTeamNode = findDeptNode.getChildren().get(findDeptNode.getChildren().indexOf(teamNode));
//                        findTeamNode.getChildren().add(userNode);
//                    } else {
//                        log.trace("{} 에 {} 팀/사용자 신규 추가 : {}", deptCode, teamCode, korName);
//                        teamNode.getChildren().add(userNode);
//                        findDeptNode.getChildren().add(teamNode);
//                    }
//                }
//                }
//            } else {
//                log.trace("신규 Dept/Team 추가, deptCode : {} add user : {}", deptCode, korName);
//                if(StringUtils.isEmpty(teamCode) || lev == 1) {
//                    log.trace("-> 부서만 존재하는 사용자 or Lev : {} : {}", lev, korName);
//                    deptNode.getChildren().add(userNode);
//                } else {
//                    log.trace("-> 부서/팀 존재하는 사용자 : {}", korName);
//                    teamNode.getChildren().add(userNode);
//                    deptNode.getChildren().add(teamNode);
//                }
//                rootNode.getChildren().add(deptNode);
////                jsTreeNodes.add(deptNode);
//            }
        }


        return jsTreeNodes;
    }
}
