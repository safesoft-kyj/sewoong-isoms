package com.dtnsm.common.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class AjaxRequestHandlingFilter implements Filter {
    private int errorCode = 401;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletResponse resp = (HttpServletResponse) response;
            String ajaxHeader = ((HttpServletRequest) request).getHeader("X-Requested-With");
            if ("XMLHttpRequest".equals(ajaxHeader)) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                log.debug("XMLHttpRequest[{}] username={}", ((HttpServletRequest) request).getRequestURI(), authentication.getName());
                if("anonymousUser".equals(authentication.getName())) {
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
