package com.cauh.common.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Slf4j
public class AjaxRequestHandlingFilter implements Filter {
    private int errorCode = 401;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        //Ajax Filter 예외 URI 선별
        List<String> permitURIList = new ArrayList<>();
        permitURIList.add("/signUp/ajax/validation");


        try {
            HttpServletResponse resp = (HttpServletResponse) response;
            String ajaxHeader = ((HttpServletRequest) request).getHeader("X-Requested-With");
            if ("XMLHttpRequest".equals(ajaxHeader)) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                log.debug("XMLHttpRequest[{}] username={}", ((HttpServletRequest) request).getRequestURI(), authentication.getName());
                //permitURIList에 포함되어있는 경우, Ajax를 예외적으로 사용 가능함
                Boolean isPermit = permitURIList.contains(((HttpServletRequest) request).getRequestURI());
                //log.info("uri : {}", ((HttpServletRequest) request).getRequestURI());

                //YSH :: 회원가입 양식의 validation link는 예외적으로 anonymous User에서 ajax를 사용하게 처리.
                if(!isPermit && "anonymousUser".equals(authentication.getName())) {
                    resp.setStatus(this.errorCode);
                    resp.sendError(this.errorCode, "Ajax time out");
                    SecurityContextHolder.clearContext();
                    throw new AccessDeniedException("Ajax request time out.");
                }
            }
            filterChain.doFilter(request, response);
        } catch (IOException e) {
            throw e;
        } catch (Exception ex) {
            throw ex;
        }
    }


    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {

    }
}
