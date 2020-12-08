package com.cauh.iso.controller;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.constant.UserStatus;
import com.cauh.common.entity.constant.UserType;
import com.cauh.common.repository.SignatureRepository;
import com.cauh.common.repository.UserRepository;
import com.cauh.common.security.authentication.CustomUsernamePasswordAuthenticationToken;
import com.cauh.iso.component.CurrentUserComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.xpath.operations.Bool;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserRepository userRepository;

    //현재 유저 정보를 담고 있는 Component Class
    private final CurrentUserComponent currentUserComponent;

    private final SignatureRepository signatureRepository;

    private final PasswordEncoder passwordEncoder;

    private List<Account> accounts = new ArrayList<>();

    @GetMapping("/user/profile")
    public String profile() {
        return "user/profile";
    }

    @PostMapping("/signUp")
    @Transactional
    public String signUpRequest(Account account, RedirectAttributes attributes) {
        log.info("@Sign Up Request : {}", account.getUsername());

        Optional<Account> user = userRepository.findByUsername(account.getUsername());
        if(user.isPresent()) {

            attributes.addFlashAttribute("type", "danger");
            attributes.addFlashAttribute("message", "Sign Up request was failed");
        } else {
            //userStatus를 통해 현재 유저 상태 설정 (SIGNUP_REQUEST)
            account.setUserType(UserType.U);
            account.setAccountNonLocked(false);
            account.setEnabled(true);
            account.setUserStatus(UserStatus.SIGNUP_REQUEST);
            
            //가입 시 계정 유효기한을 설정(가입시점 + D-14)
            Date now = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.DATE, 14);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            account.setAccountExpiredDate(calendar.getTime());

            //입력된 비밀번호 암호화
            account.setPassword(passwordEncoder.encode(account.getPassword()));

            //계정정보 저장
            userRepository.save(account);

            attributes.addFlashAttribute("type", "success");
            attributes.addFlashAttribute("message", "Sign up request is success");
        }
        return "redirect:/login";
    }

//    @GetMapping("/user/signature")
//    public String signature(@CurrentUser Account user, Model model) {
//        Optional<Signature> optionalSignature = signatureRepository.findById(user.getUsername());
//        model.addAttribute("signature", optionalSignature.isPresent() ? optionalSignature.get() : new Signature());
//
//        return "user/signature";
//    }
//
//    @PostMapping("/user/signature")
//    public String updateSignature(@CurrentUser Account user, @RequestParam("base64signature") String base64signature, RedirectAttributes attributes) {
//        Optional<Account> optionalUser = userRepository.findById(user.getId());
//        if(optionalUser.isPresent()) {
//            Account u = optionalUser.get();
//
//            Signature signature = new Signature();
//            signature.setBase64signature(base64signature);
//            signature.setId(u.getUsername());
//
//            signatureRepository.save(signature);
//
//            user.setSignature(true);
//            updateAuthentication(user);
//        }
//
//        attributes.addFlashAttribute("message", "서명 정보가 등록 되었습니다.");
//        return "redirect:/user/signature";
//    }

    //BootstrapValidate - remote.
    @PostMapping("/signUp/ajax/validation")
    @ResponseBody
    public Map<String, Boolean> signUpUsernameValid(@RequestParam("type") String type,
                                                    @RequestParam("keyword") String keyword) {
        List<Account> currentUserAccountList = currentUserComponent.getCurrentUserList();
        log.debug("Account List : {}", currentUserAccountList);
        Map<String, Boolean> resultMap = new HashMap<>();
        Boolean result = true;

        if(type.equals("username")){
            //내용이 중복되면 false 반환
            for(Account account : currentUserAccountList){
                if(!ObjectUtils.isEmpty(account.getUsername()) && account.getUsername().equals(keyword)){
                    log.info("Validation Field : {}({})", type, keyword);
                    result = false;
                    break;
                }
            }
        }else if(type.equals("email")) {
            log.info("Data Type : {}({})", type, keyword);
            //내용이 중복되면 false 반환
            for(Account account : currentUserAccountList){
                if(!ObjectUtils.isEmpty(account.getEmail()) && account.getEmail().equals(keyword)){
                    log.info("Validation Field : {}({})", type, keyword);
                    result = false;
                    break;
                }
            }
        }
        resultMap.put("valid", result);
        return resultMap;
    }

    public void updateAuthentication(Account userDetails) {
        Collection authorities = userDetails.getSopAuthorities();
        Authentication authentication = new CustomUsernamePasswordAuthenticationToken(userDetails, null, authorities);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
    }
}
