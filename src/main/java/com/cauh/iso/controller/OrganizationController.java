package com.cauh.iso.controller;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.Department;
import com.cauh.common.entity.constant.UserStatus;
import com.cauh.common.entity.constant.UserType;
import com.cauh.common.mapper.DeptUserMapper;
import com.cauh.common.repository.DepartmentRepository;
import com.cauh.common.repository.UserRepository;
import com.cauh.iso.admin.service.DepartmentService;
import com.cauh.iso.domain.JsTreeIcon;
import com.cauh.iso.domain.JsTreeNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OrganizationController {
    private final DeptUserMapper deptUserMapper;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final DepartmentService departmentService;

//    @Value("${gw.userTbl}")
//    private String gwUserTbl;
//
//    @Value("${gw.deptTbl}")
//    private String gwDeptTbl;

    @Value("${site.code}")
    private String siteCode;

    @GetMapping("/common/organization/chart")
    @ResponseBody
    public List<JsTreeNode> organization() {
//        Map<String, String> param = new HashMap<>();
//        param.put("gwUserTbl", gwUserTbl);
//        param.put("gwDeptTbl", gwDeptTbl);
//        param.put("state", "1");
//        List<Map<String, String>> allUsers = deptUserMapper.getAllUsers(param);


        List<Account> accounts = userRepository.findAllByUserTypeAndUserStatusOrderByNameAsc(UserType.USER, UserStatus.ACTIVE);
//        List<Account> managers = accounts.stream().filter(user -> user.getUserJobDescriptions().stream()
//                                //?????? ????????? ??????????????? (Manager????????? ?????? JD??? 1??? ?????? ?????????)
//                                .filter(uj -> uj.getJobDescription().isManager()).count() > 0).collect(Collectors.toList());

        List<JsTreeNode> jsTreeNodes = new ArrayList<>();
        String mainCode = siteCode;
        String mainName = mainCode + "(" + accounts.size() + ")";

        JsTreeNode rootNode = new JsTreeNode(mainCode, mainName);
//        rootNode.setIcon("jstree-folder");
        rootNode.getState().setOpened(true);
        jsTreeNodes.add(rootNode);

        String deptCode, teamCode;
        String deptName, teamName;

        String korName;
        String username;
        int lev;
        boolean sex;

        for(Account u : accounts) {
            log.trace(" -- user : {}", u);
            deptCode = ObjectUtils.isEmpty(u.getDepartment().getParentDepartment()) ? u.getDepartment().getId().toString() : u.getDepartment().getParentDepartment().getId().toString();
            deptName = ObjectUtils.isEmpty(u.getDepartment().getParentDepartment()) ? u.getDepartment().getName() : u.getDepartment().getParentDepartment().getName();
            teamCode = ObjectUtils.isEmpty(u.getDepartment().getParentDepartment()) ? null : u.getDepartment().getId().toString();
            teamName = ObjectUtils.isEmpty(u.getDepartment().getParentDepartment()) ? null : u.getDepartment().getName();

            korName = u.getName();
            username = u.getUsername();
//            lev = Integer.parseInt(String.valueOf(u.get("lev")));
//            sex = Boolean.valueOf(String.valueOf(u.get("sex")));

            //User??? ????????? ???????????????
            JsTreeNode deptNode = new JsTreeNode(deptCode, deptName, deptCode, deptName, teamCode, teamName, "", u.getUsername(), JsTreeIcon.dept);
            JsTreeNode teamNode = new JsTreeNode(teamCode, teamName, deptCode, deptName, teamCode, teamName, "", u.getUsername(), JsTreeIcon.team);

            //?????????????????? ?????? ????????? ?????? code??? ??????
            JsTreeNode userNode = new JsTreeNode("0", StringUtils.isEmpty(u.getPosition()) ? u.getName() : u.getName() + "(" + u.getPosition() + ")",
                                                deptCode, u.getDeptName(), teamCode, u.getTeamName(), u.getEmpNo(), u.getUsername(), JsTreeIcon.user_male);

             if(ObjectUtils.isEmpty(u.getDepartment())) {
                log.trace("{} - ????????? ?????? : {}",siteCode, u.getName());
                rootNode.getChildren().add(userNode);
            } else if(rootNode.getChildren().contains(deptNode)) {//?????? ??????
                log.trace("?????? ????????? deptCode : {} add user : {}", deptCode, korName);
                JsTreeNode findDeptNode = rootNode.getChildren().get(rootNode.getChildren().indexOf(deptNode));

                if (ObjectUtils.isEmpty(u.getDepartment().getParentDepartment())) {
                    log.trace("?????? ?????? ?????????, ????????? ?????? : {}", korName);
                    findDeptNode.getChildren().add(userNode);
                } else {
                    if(findDeptNode.getChildren().contains(teamNode)) {
                        log.trace("{}/{} ?????? ????????? ????????? ?????? : {}", deptCode, teamCode, korName);
                        JsTreeNode findTeamNode = findDeptNode.getChildren().get(findDeptNode.getChildren().indexOf(teamNode));
                        findTeamNode.getChildren().add(userNode);
                    } else {
                        log.trace("{} ??? {} ???/????????? ?????? ?????? : {}", deptCode, teamCode, korName);
                        teamNode.getChildren().add(userNode);
                        findDeptNode.getChildren().add(teamNode);
                    }
                }
             } else{
                log.trace("?????? Dept/Team ??????, deptCode : {} add user : {}", deptCode, korName);
                if(ObjectUtils.isEmpty(u.getDepartment().getParentDepartment())) {
                    log.trace("-> ????????? ???????????? ????????? : {}", korName);
                    deptNode.getChildren().add(userNode);
                } else {
                    log.trace("-> ??????/??? ???????????? ????????? : {}", korName);
                    teamNode.getChildren().add(userNode);
                    deptNode.getChildren().add(teamNode);
                }
                rootNode.getChildren().add(deptNode);
            }
        }

        return jsTreeNodes;
    }
}
