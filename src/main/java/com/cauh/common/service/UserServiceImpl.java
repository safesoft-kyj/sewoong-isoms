package com.cauh.common.service;

import com.cauh.common.entity.Account;
import com.cauh.common.mapper.DeptUserMapper;
import com.cauh.common.repository.UserRepository;
import com.cauh.common.security.authentication.InternalAccountAuthenticationException;
import com.cauh.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    private final PasswordEncoder passwordEncoder;

//    private final DeptUserMapper deptUserMapper;
//
//    @Value("${gw.userTbl}")
//    private String gwUserTbl;
//
//    @Value("${gw.deptTbl}")
//    private String gwDeptTbl;

    @Override
    public Account loadUserByUsername(String username) throws UsernameNotFoundException {
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

            account.getPassword();

            if(passwordEncoder.matches(password, account.getPassword())) {
                return account;
            }
            throw new InternalAccountAuthenticationException("내부사용자 로그인에 실패하였습니다.");
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

//    private Account updateUserInfo(String username) {
//        Map<String, String> param = new HashMap<>();
//        param.put("gwUserTbl", gwUserTbl);
//        param.put("gwDeptTbl", gwDeptTbl);
//        param.put("username", username);
//        Account userDetails;
//        Account deptUser = deptUserMapper.findByUsername(param);
//        if(ObjectUtils.isEmpty(deptUser)) {
//            log.error(" *** 그룹웨어 사용자 연동 테이블을 확인해 주세요. ***");
//            throw new RuntimeException(" *** 그룹웨어 사용자 연동 테이블을 확인해 주세요. ***");
//        }
//
//        Optional<Account> optionalUser = findByUsername(username);
//        if(optionalUser.isPresent()) {
//            log.info("==> 기존 사용자 정보 업데이트 : {} **", username);
//            userDetails = optionalUser.get();
//            userDetails.setEmail(deptUser.getEmail());
//            userDetails.setEmpNo(deptUser.getEmpNo());
//            userDetails.setEngName(deptUser.getEngName());
//            userDetails.setKorName(deptUser.getKorName());
//            userDetails.setDeptName(deptUser.getDeptName());
//            userDetails.setTeamName(deptUser.getTeamName());
//            userDetails.setDeptCode(deptUser.getDeptCode());
//            userDetails.setTeamCode(deptUser.getTeamCode());
//            userDetails.setLev(deptUser.getLev());
//            userDetails.setDuty(deptUser.getDuty());
//            userDetails.setPosition(deptUser.getPosition());
//            userDetails.setEnabled(deptUser.isEnabled());
//            userDetails.setAccountNonLocked(deptUser.isEnabled());
//        } else {
//            log.info("==> 신규 사용자 정보 등록 : {} **", username);
//            userDetails = deptUser;
//            userDetails.setAccountNonLocked(deptUser.isEnabled());
////            userDetails.setEnabled(true);
//        }
//        if(StringUtils.isEmpty(deptUser.getStrInDate()) == false) {
//            userDetails.setInDate(DateUtils.toDate(deptUser.getStrInDate(), "yyyy-MM-dd"));
//        } else if(!StringUtils.isEmpty(deptUser.getEmpNo())) {
//            userDetails.setInDate(toDate(userDetails.getEmpNo()));//입사일자를 사번에서 가져와 설정한다.
//        }
//        return saveOrUpdate(userDetails);
//    }

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
