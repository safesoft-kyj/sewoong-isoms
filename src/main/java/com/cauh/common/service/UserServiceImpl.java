package com.cauh.common.service;

import com.cauh.common.entity.*;
import com.cauh.common.entity.constant.JobDescriptionStatus;
import com.cauh.common.entity.constant.RoleStatus;
import com.cauh.common.entity.constant.UserStatus;
import com.cauh.common.entity.constant.UserType;
import com.cauh.common.repository.DepartmentRepository;
import com.cauh.common.repository.UserJobDescriptionChangeLogRepository;
import com.cauh.common.repository.UserJobDescriptionRepository;
import com.cauh.common.repository.UserRepository;
import com.cauh.common.security.authentication.InternalAccountAuthenticationException;
import com.cauh.common.security.authentication.SignUpRequestedAccountException;
import com.cauh.common.utils.DateUtils;
import com.cauh.iso.admin.service.UserJobDescriptionService;
import com.cauh.iso.component.CurrentUserComponent;
import com.cauh.iso.service.JobDescriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.AccountLockedException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Dt&amp;SanoMedics <br>
 * Developer : Jeonghwan Seo <br>
 * Date &amp; Time : 2018-09-27  16:11 <br>
 * Comments : Description. <br>
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserJobDescriptionRepository userJobDescriptionRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepository departmentRepository;

    //현재 유저정보를 가지고있는 Component
    private final CurrentUserComponent currentUserComponent;

    private final UserJobDescriptionChangeLogRepository userJobDescriptionChangeLogRepository;
    private final JobDescriptionService jobDescriptionService;

//    private final DeptUserMapper deptUserMapper;
//
//    @Value("${gw.userTbl}")
//    private String gwUserTbl;
//
//    @Value("${gw.deptTbl}")
//    private String gwDeptTbl;

    @Override
    public Account loadUserByUsername(String username) throws UsernameNotFoundException{
//        User user = authenticationMapper.loadUserByUsername(username);
        Optional<Account> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UsernameNotFoundException("[" + username + "] Username Not Found.");
    }

    @Override
    public Account authenticate(String username, String password){
        log.info("@UserService : Authenticate");
        Optional<Account> accountOptional = userRepository.findByUsername(username);

        if(accountOptional.isPresent())
        {
            log.info("@Authenticate ID 확인");
            //비밀번호 Matching이 되지 않거나, Account 계정이 없는 경우 실패
            Account account = accountOptional.get();

            if(account.getUserStatus() == UserStatus.ACTIVE) {
                account.getPassword();
                if(passwordEncoder.matches(password, account.getPassword())) {
                    return account;
                }
                throw new InternalAccountAuthenticationException("내부사용자 로그인에 실패하였습니다.");
            } else if (account.getUserStatus() == UserStatus.SIGNUP_REQUEST) {
                throw new SignUpRequestedAccountException("가입 신청중인 사용자입니다.");
            } else if (ObjectUtils.isEmpty(account.getUserStatus())){
                throw new InternalAccountAuthenticationException("내부사용자 로그인에 실패하였습니다.");
            }
        }
        throw new UsernameNotFoundException("[" + username + "] Username Not Found.");
    }

    @Override
    public Optional<Account> findUserByEmail(String usernameOrEmail) {
        return userRepository.findByUsername(usernameOrEmail);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Account saveOrUpdate(Account user) {
        return userRepository.save(user);
    }

    @Override
    public Account signUpRequest(Account account) {
        //userStatus를 통해 현재 유저 상태 설정 (SIGNUP_REQUEST)
        account.setUserType(UserType.USER);
        account.setAccountNonLocked(true);
        account.setEnabled(true);
        account.setUserStatus(UserStatus.SIGNUP_REQUEST);

        //입력된 비밀번호 암호화
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        //가입 시 계정 유효기한을 설정(가입시점 + D-14)
        LocalDate localDate = LocalDate.now().plusDays(14);
        Date DDay_14 = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        account.setAccountExpiredDate(DDay_14);

        //부서입력
        if(!ObjectUtils.isEmpty(account.getDepartment())) {
            Department department = account.getDepartment();

            //상위 부서가 존재할 경우
            if(!ObjectUtils.isEmpty(department.getParentDepartment())) {
                account.setDeptName(department.getParentDepartment().getName());
                account.setTeamName(department.getName());
            } else { //상위 부서가 없을 경우
                account.setDeptName(department.getName());
            }
        }

        if(!ObjectUtils.isEmpty(account.getJdIds())){
            //selectedIds 목록 추가
            List<String> selectedIds = Arrays.asList(account.getJdIds());
            for(String id : selectedIds) {
                UserJobDescription userJobDescription = new UserJobDescription();
                userJobDescription.setJobDescription(JobDescription.builder().id(Integer.parseInt(id)).build());
                userJobDescription.setUser(account);
                userJobDescription.setStatus(JobDescriptionStatus.APPROVED);

                userJobDescriptionRepository.save(userJobDescription);
            }
        }

        return account;
    }

    @Override
    public Account signUpAccept(Account account) {
        //가입 수락 - 가입 날짜 설정
        account.setIndate(new Date());
        //가입 수락 - 계정 기한 설정
        LocalDate accountExpiredDate = LocalDate.of(9999, 12, 31);
        account.setAccountExpiredDate(Date.from(accountExpiredDate.atStartOfDay(ZoneId.systemDefault()).toInstant())); //9999-12-31 설정
        //가입 수락 - 비밀번호 기한 설정
        LocalDate credentialExpiredDate = LocalDate.now().plusDays(90); // + 90일
        account.setCredentialsExpiredDate(Date.from(credentialExpiredDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        account.setUserStatus(UserStatus.ACTIVE);
        account.setEnabled(true);

        //부서입력
        if(!ObjectUtils.isEmpty(account.getDepartment())) {
            Department department = account.getDepartment();

            //상위 부서가 존재할 경우
            if(!ObjectUtils.isEmpty(department.getParentDepartment())) {
                account.setDeptName(department.getParentDepartment().getName());
                account.setTeamName(department.getName());
            } else { //상위 부서가 없을 경우
                account.setDeptName(department.getName());
            }
        }


        //JD 재입력
        if(!ObjectUtils.isEmpty(account.getJdIds())){
            List<String> selectedIds = Arrays.asList(account.getJdIds());
            List<UserJobDescription> newUserJobDescriptions = new ArrayList<>();
            List<UserJobDescription> userJobDescriptions = account.getUserJobDescriptions();

            //변경 전
            String prevRole = userJobDescriptions.stream().map(d -> d.getJobDescription().getShortName()).collect(Collectors.joining(","));
            log.info("Prev : {}", prevRole);

            List<String> currentJdIds = userJobDescriptions.stream().map(jd -> Integer.toString(jd.getJobDescription().getId())).collect(Collectors.toList());
            LocalDate now = LocalDate.now();

            //새로이 선택된 값에 중복되거나 기존에 겹치는 값 설정.
            for(UserJobDescription userJobDescription : userJobDescriptions){
                //사라진 값 -> userDescription 삭제
                if(!selectedIds.contains(Integer.toString(userJobDescription.getJobDescription().getId()))){
                    userJobDescriptionRepository.delete(userJobDescription);
                }else { //기존에 유지되는 값 - AssginDate 설정
                    userJobDescription.setAssignDate(Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    UserJobDescription savedUserJobDescription = userJobDescriptionRepository.save(userJobDescription);
                    newUserJobDescriptions.add(savedUserJobDescription);
                }
            }

            List<String> nextRoleList = new ArrayList<>();

            //새로 선택된 UserJobDescription 선택
            for(String id : selectedIds) {
                if(!currentJdIds.contains(id)){ //기존 JDIds에 없는 Case 생성
                    UserJobDescription userJobDescription = new UserJobDescription();
                    userJobDescription.setJobDescription(JobDescription.builder().id(Integer.parseInt(id)).build());
                    userJobDescription.setUser(account);
                    userJobDescription.setStatus(JobDescriptionStatus.APPROVED);
                    userJobDescription.setAssignDate(Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    UserJobDescription savedUserJobDescription = userJobDescriptionRepository.save(userJobDescription);
                    newUserJobDescriptions.add(savedUserJobDescription);
                }

                nextRoleList.add(jobDescriptionService.findById(Integer.parseInt(id)).getShortName());
            }

            //변경 후
            String nextRole = nextRoleList.stream().map(d -> d).collect(Collectors.joining(","));
            log.info("Next : {}", nextRole);

            UserJobDescriptionChangeLog userJobDescriptionChangeLog = UserJobDescriptionChangeLog.builder()
                    .user(account)
                    .requestDate(new Date())
                    .assignDate(Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                    .prevJobDescription(prevRole)
                    .nextJobDescription(nextRole)
                    .reason("신규 가입")
                    .roleStatus(RoleStatus.APPROVED)
                    .build();
            userJobDescriptionChangeLogRepository.save(userJobDescriptionChangeLog);
        }

        //Account 저장 시, UserJobDescription 저장데이터 제거
        account.setUserJobDescriptions(null);
        return account;
    }


    public Account signUpReject(Account account) {
        //계정 유효기간 만료로 처리 (현재 시간 입력)
        //계정 상태 INACTIVE 지정 / Enabled 변수 false로 설정.
        account.setAccountExpiredDate(new Date());
        account.setUserStatus(UserStatus.INACTIVE);
        account.setEnabled(false);

        return account;
    }

    @Override
    public void sync() {
//        Map<String, String> param = new HashMap<>();
//        param.put("gwUserTbl", gwUserTbl);
//        param.put("gwDeptTbl", gwDeptTbl);
//
//        List<Map<String, String>> allUsers = deptUserMapper.getAllUsers(param);
//        List<String> usernames = allUsers.stream().map(m -> m.get("username"))
//                .collect(Collectors.toList());
//        for(Map<String, String> userMap : allUsers) {
//            String username = userMap.get("username");
//
//            log.debug("@username = {}", username);
//            if(StringUtils.isEmpty(userMap.get("empNo")) == false && userMap.get("empNo").length() == 9) {
//                //S18052801
//                updateUserInfo(username);
//            } else {
//                log.warn("@@ 사용자 : {}, 사번 : {} 오류 ", username, userMap.get("empNo"));
//            }
//        }
//        userDisabled(usernames);//비활성화 처리
    }


    /**
     * Account Refresh 기능
     */
    @Override
    public void refresh(){
        List<UserStatus> userStatusList = new ArrayList<>();
        userStatusList.add(UserStatus.SIGNUP_REQUEST);
        userStatusList.add(UserStatus.ACTIVE);

        //enabled 가 true이거나 회원가입 신청 / 활성화된 계정 대상으로 refresh 진행.
        List<Account> accountList = userRepository.findAllByEnabledAndUserStatusIn(true, userStatusList);
        for(Account account : accountList) {
            //오늘날짜 기준, 계정 유효기한이 경과한 경우
            if(!DateUtils.isFutureDate(account.getAccountExpiredDate())) {
                log.debug("Account Refrsh=== 계정 유효기한 경과 : {}", account.getUsername());
                account.setUserStatus(UserStatus.INACTIVE);
                account.setEnabled(false);
                userRepository.save(account);
            }
        }

        //User List 업데이트
        currentUserComponent.updateCurrentUserList();
    }

    @Override
    public void countFailure(String username) {
        Optional<Account> account = userRepository.findByUsername(username);
        if(account.isPresent()){
            account.get().setLoginFailCnt(account.get().getLoginFailCnt()+1);
            userRepository.save(account.get());
        }
    }

    @Override
    public void lockedUser(String username) {
        Optional<Account> account = userRepository.findByUsername(username);
        if(account.isPresent()){
            account.get().setAccountNonLocked(false);
            userRepository.save(account.get());
        }
    }

    @Override
    public Integer checkFailureCount(String username) {
        return null;
    }


    protected void userDisabled(List<String> activeUsernames) {
        log.debug("=> 비활성화 처리 할 계정 정보 확인 ***********");
        List<String> usernames = userRepository.findAll()
                .stream().map(user -> user.getUsername())
                .collect(Collectors.toList());

        usernames.removeAll(activeUsernames);

        if(!ObjectUtils.isEmpty(usernames)) {
            for(String username : usernames) {
                log.info("=> @username : {} 비활성화.", username);
                Account user = userRepository.findByUsername(username).get();
                user.setEnabled(false);

                userRepository.save(user);
            }
        } else {
            log.info("<= 비활성화 할 계정이 존재하지 않습니다.");
        }
    }

    protected Date toDate(String empNo) {
        String s = empNo.replace("S", "20");
        return DateUtils.toDate(s.substring(0, s.length() - 2), "yyyyMMdd");
    }
}
