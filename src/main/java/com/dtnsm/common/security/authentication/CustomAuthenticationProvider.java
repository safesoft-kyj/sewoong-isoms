package com.dtnsm.common.security.authentication;

import com.dtnsm.common.entity.Account;
import com.dtnsm.common.entity.QUserJobDescription;
import com.dtnsm.common.entity.UserJobDescription;
import com.dtnsm.common.entity.constant.JobDescriptionStatus;
import com.dtnsm.common.entity.constant.UserType;
import com.dtnsm.common.mapper.DeptUserMapper;
import com.dtnsm.common.repository.SignatureRepository;
import com.dtnsm.common.repository.UserJobDescriptionRepository;
import com.dtnsm.common.service.CustomUserDetailsService;
import com.dtnsm.common.service.ExternalCustomUserService;
import com.dtnsm.common.service.UserService;
import com.dtnsm.common.utils.DateUtils;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Dt&amp;SanoMedics <br>
 * Developer : Jeonghwan Seo <br>
 * Date &amp; Time : 2018-09-27  17:32 <br>
 * Comments : Description. <br>
 **/
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider, MessageSourceAware {
    private UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();

    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private UserJobDescriptionRepository userJobDescriptionRepository;
//    @Autowired
//    private GroupwareUserAuthService groupwareUserAuthService;
//    @Resource
//    private DeptUserMapper deptUserMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ExternalCustomUserService externalCustomUserService;
    @Autowired
    private SignatureRepository signatureRepository;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${gw.sign-image}")
    private String gwSignURL;

    @Value("${file.upload-dir}")
    private String fileUploadDir;

    @Value("${gw.userTbl}")
    private String gwUserTbl;

    @Value("${gw.deptTbl}")
    private String gwDeptTbl;

    private PasswordEncoder passwordEncoder;

    private MessageSourceAccessor messages;// = SpringSecurityMessageSource.getAccessor();

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    public void setUserDetailsService(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    private CustomUserDetailsService getUserDetailsService() {
        return customUserDetailsService;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private void preAuthenticationChecks(Account userDetails) {
        try {
            userDetailsChecker.check(userDetails);
        } catch (LockedException e) {
            throw e;
        } catch (DisabledException e) {
            throw e;
        } catch (AccountExpiredException e) {
            throw e;
            } catch (CredentialsExpiredException e) {
            throw e;
        }
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        try {
        String username = authentication.getName();

        Account userDetails;

        CustomWebAuthenticationDetails customWebAuthenticationDetails = (CustomWebAuthenticationDetails) authentication.getDetails();

        Object credentials = authentication.getCredentials();
//        log.debug("credentials : {}", credentials);
        boolean isGroupWareUser = username.indexOf("@") == -1;
//        List<String> usernames = new ArrayList<>();
//        usernames.add("mjlee");
//        usernames.add("hjlee");



        if(isGroupWareUser) {
            log.debug("==> 그룹웨어(gw.dtnsm.com) 사용자 로그인 : {}", username);
            String ip = ((CustomWebAuthenticationDetails) authentication.getDetails()).getClientIP();
            log.info("===> @username : {}, IP : {}", username, ip);

//            boolean isInternal = ip.startsWith("192.168.21");
//            boolean isVPN = ip.startsWith("10.212.134");
//            boolean isAdmin = username.endsWith("#admin");

//            log.info("==> isInternal : {}, isVPN : {}, isAdmin : {}", isInternal, isVPN, isAdmin);
//            if(!isInternal && !isVPN && !isAdmin) {
//                log.error("접속을 허용하지 않는 IP[{}] 입니다.", ip);
//                throw new GroupwareAuthenticationException("접속을 허용하지 않는 IP 입니다.");
//            }

            userDetails = userService.loadUserByUsername(username);

            preAuthenticationChecks(userDetails);

            userDetails.setUserType(UserType.U);

            userDetails.setSignature(signatureRepository.findById(username).isPresent());
//                //------------------
//                //TODO 요기 제거!!
//                userDetails.setKorName("홍길동");
//                userDetails.setEngName("Hong, Gil Dong");
//                userDetails.setTeamName("XX");
//                userDetails.setDeptName("XXX");
//                //-----------------
            /**
             * 로그인 한 사용자의 Job Description 정보 설정
             */
            log.info("@User -> JobDescription : {}", userDetails.getJobDescriptions());
            QUserJobDescription qUserJobDescription = QUserJobDescription.userJobDescription;
            BooleanBuilder builder = new BooleanBuilder();
            builder.and(qUserJobDescription.username.eq(username));
            builder.and(qUserJobDescription.status.in(JobDescriptionStatus.APPROVED));
            Iterable<UserJobDescription> iterable = userJobDescriptionRepository.findAll(builder);
            if(!ObjectUtils.isEmpty(iterable)) {
                userDetails.setJobDescriptions(StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList()));
//
                log.info("==> 사용자의 Job Assign Date 정보 설정");
                userDetails.setJobAssignDateMap(StreamSupport.stream(iterable.spliterator(), false)
                        .collect(Collectors.toMap(u -> u.getJobDescriptionVersion().getJobDescription().getId(), u -> u.getAssignDate())));

                log.info("<== {} : JobAssignDateMap : {}", username, userDetails.getJobAssignDateMap());
            }

        } else {
            Optional<Account> optionalUser = externalCustomUserService.findByEmail(username);

            log.debug("==> 외부 사용자 로그인 : {}", username);
            if(optionalUser.isPresent() == false) {
                throw new AccountExpiredException("자격 증명 확인에 실패 하였습니다.");
            }

            userDetails = optionalUser.get();
        }



//        log.info("User Role => {}", userDetails.getRole());

        log.info("Application Name : {}, Username : {}, isAdmin : {}", applicationName, userDetails.getUsername(), userDetails.isAdmin());

//        if("SOP".equals(applicationName)) {
//            log.info("SOP JD(QA) -> Admin, JD(QMO) -> Super Admin !!!");
//        }


//        if(userDetails.isAdmin()) {
//            log.debug("****** {}, 관리자로 로그인 되었습니다. ******", userDetails.getUsername());
//        } else {
            /**
             * JD 정보 체크
             */

            if(ObjectUtils.isEmpty(userDetails.getJobDescriptions())) {
                log.warn("사용자[{}] JD가 지정 되지 않았습니다.", userDetails.getUsername());
            } else {
                log.debug("사용자[{}] 의 JD : {}", userDetails.getUsername(), userDetails.getJobDescriptions());
            }





        return createSuccessAuthentication(customWebAuthenticationDetails, userDetails);
    }



//    private Account updateUserInfo(String username) {
//        Account userDetails;
//        Map<String, String> param = new HashMap<>();
//        param.put("gwUserTbl", gwUserTbl);
//        param.put("gwDeptTbl", gwDeptTbl);
//        param.put("username", username);
//        Account deptUser = deptUserMapper.findByUsername(param);
//
//        if(ObjectUtils.isEmpty(deptUser)) {
//            log.error(" *** 그룹웨어 사용자 연동 테이블을 확인해 주세요. ***");
//        }
//
//        Optional<User> optionalUser = userService.findByUsername(username);
//        if(optionalUser.isPresent()) {
//            log.info("==> 기존 사용자 정보 업데이트 : {} **", username);
//            if(!deptUser.isEnabled()) {
//                log.info("현재 로그인 한 사용자[{}]는 퇴사/삭제 상태 입니다.", username);
//            }
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
//        } else {
//            log.info("==> 신규 사용자 정보 등록 : {} **", username);
//            userDetails = deptUser;
//            userDetails.setAccountNonLocked(true);
//            userDetails.setEnabled(true);
//        }
//
//        if(ObjectUtils.isEmpty(userDetails.getInDate())) {
//            userDetails.setInDate(toDate(userDetails.getEmpNo()));//입사일자를 등록한다.
//        }
//        return userService.saveOrUpdate(userDetails);
//    }

    protected Date toDate(String empNo) {
        String s = empNo.replace("S", "20");
        return DateUtils.toDate(s.substring(0, s.length() - 2), "yyyyMMdd");
    }


    /**
     * @param customWebAuthenticationDetails
     * @param userDetails
     * @return
     */
    private CustomUsernamePasswordAuthenticationToken createSuccessAuthentication(CustomWebAuthenticationDetails customWebAuthenticationDetails, Account userDetails) {
//        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("QAA");
        List<GrantedAuthority> authorities = null;
        String username = userDetails.getUsername();
//        log.debug("username = {}, User Authorities = {}", userDetails.getUsername(), userDetails.getAuthorityList());
//

        log.info("==> username : {}, userType : {}", username, userDetails.getUserType().name());
        /**
         * Job Description 의 Short Name 을 사용자의 권한(role) 로 설정한다.
         */
        if(userDetails.getUserType() == UserType.U) {
            if (!ObjectUtils.isEmpty(userDetails.getJobDescriptions())) {
                String commaStringAuthorities = userDetails.getJobDescriptions().stream().map(jd -> jd.getJobDescriptionVersion().getJobDescription().getShortName()).collect(Collectors.joining(","));
                authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(commaStringAuthorities);

                log.debug("==> Username : {}, Role : {}", username, authorities);
            }
        } else if(userDetails.getUserType() == UserType.AUDITOR) {
            authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(UserType.AUDITOR.name());
        }
        userDetails.setLoginDate(new Date());
        userDetails.setSopAuthorities(authorities);
        /**
         * UserDetails 정보에 AccessLogId 설정
         */
//        userDetails.setAccessLogId(accessLog.getAccessLogId());
//        authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("QAA");
        CustomUsernamePasswordAuthenticationToken customUsernamePasswordAuthenticationToken = new CustomUsernamePasswordAuthenticationToken(userDetails, null, authorities);
        customUsernamePasswordAuthenticationToken.setDetails(customWebAuthenticationDetails);

        log.info("customUsernamePasswordAuthenticationToken success();");
        return customUsernamePasswordAuthenticationToken;
    }

    /**
     * 비밀번호 일치 여부 및 비밀번호 오류 회수를 체크하여 5회 오류인 경우 계정 잠금 처리한다.
     *
     * @param password
     * @param userDetails
     * @throws BadCredentialsException
     */
    protected void passwordMatches(String password, Account userDetails) throws BadCredentialsException {
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
