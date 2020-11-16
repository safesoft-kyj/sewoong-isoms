package com.cauh.esop.security.interceptor;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.constant.UserType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignatureCheckInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account user = (Account)authentication.getPrincipal();
        if(user.getUserType() == UserType.U && user.isSignature() == false) {
//            modelAndView.getModel().put("message", "서명을 등록해 주세요.");
            modelAndView.setViewName("redirect:/user/signature");
        }
        super.postHandle(request, response, handler, modelAndView);
    }
}
