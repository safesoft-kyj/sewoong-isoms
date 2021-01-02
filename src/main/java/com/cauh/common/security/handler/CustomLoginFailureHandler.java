package com.cauh.common.security.handler;

import com.cauh.common.security.authentication.SignUpRequestedAccountException;
import com.cauh.common.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;

import javax.security.auth.login.CredentialExpiredException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errormsg = null;
        String errorcnt = null;

        if(exception instanceof LockedException) { //계정 잠금 여부
            errormsg = "locked";
        } else if(exception instanceof DisabledException) { //계정 활성화 여부
            errormsg = "disabled";
        } else if(exception instanceof AccountExpiredException) { //계정 기한 만료
            errormsg = "accountExpired";
        } else if(exception instanceof CredentialsExpiredException) { //비밀번호 기한 만료
            errormsg = "credentialExpired";
        } else if(exception instanceof SignUpRequestedAccountException) {
            errormsg = "signup";
        } else if(exception instanceof BadCredentialsException || //ID / 비밀번호 입력 오류
                exception instanceof UsernameNotFoundException) {
            errormsg = null; // else로 빼두었음
        }

//        request.setAttribute("errormsg", errormsg);
//        request.getRequestDispatcher(this.defaultFailureUrl).forward(request, response);

        StringBuffer sb = new StringBuffer();
        sb.append("/login?error");

        if(!StringUtils.isEmpty(errormsg)){
            sb.append("=");
            sb.append(errormsg);
        }

        if(!StringUtils.isEmpty(errorcnt)){
            sb.append("&errorcnt=");
            sb.append(errorcnt);
        }

        //request.getRequestDispatcher(result);
        response.sendRedirect(sb.toString());
    }

    protected void loginFailureCount(String username) {
        userService.countFailure(username);
        int cnt = userService.checkFailureCount(username);
        if(cnt == 5) {
            userService.lockedUser(username);
        }
    }
}
