package com.cauh.iso.security.interceptor;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.constant.UserType;
import com.cauh.common.repository.SignatureRepository;
import com.cauh.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Vector;

@Slf4j
public class LoginPostCheckInterceptor extends HandlerInterceptorAdapter {

    //private UserRepository userRepository;
    //private SignatureRepository signatureRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account user = (Account)authentication.getPrincipal();

        //CASE 1.User 비밀번호기한 만료 시 화면 이동.
        //내부 사용자면서 비밀번호 기한이 만료 됐으면
        if(!ObjectUtils.isEmpty(user.getCredentialsExpiredDate())) {
            LocalDate today = LocalDate.now();
            LocalDate credentialsExpire = user.getCredentialsExpiredDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            log.info("다음에 변경 : {}", user.isPasswordExpiredIgnore());
            log.info("비밀번호 기한이 만료되었나? : {}", today.isAfter(credentialsExpire));

            //내부 사용자이면서 비밀번호 기한이 만료되었는가?
//            if (user.getUserType() == UserType.USER && today.isAfter(credentialsExpire)) {
//                if (!user.isPasswordExpiredIgnore()) { //처음 시작 : false, 다음에 변경 선택 : true
//                    log.info("비밀번호 기한 만료");
//                    //TODO :: 작업 수정 필요
//
//                    modelAndView.setViewName("redirect:/password-change");
//                    super.postHandle(request, response, handler, modelAndView);
//                    return;
//                }
//            }
        }

        //CASE 2.User Signature 미 등록 시,
        //내부 사용자면서 서명이 없으면,
        if(user.getUserType() == UserType.USER && user.isSignature() == false) {
            //modelAndView.getModel().put("message", "서명을 등록해 주세요.");
            modelAndView.setViewName("redirect:/user/signature");
            super.postHandle(request, response, handler, modelAndView);
            return;
        }

        //CASE 3.사용
    }
}
