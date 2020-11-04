package com.dtnsm.common.security.authentication;

import com.dtnsm.common.entity.constant.UserType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Slf4j
@Getter
@Setter
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails implements Serializable {

    private static final long serialVersionUID = 5649671012980400731L;
    //    private Environment environment;
    private UserType userType;
    private boolean forceLogin;
    private String clientIP;

    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.userType = UserType.valueOf(ServletRequestUtils.getStringParameter(request, "userType", "U"));
        this.forceLogin = ServletRequestUtils.getBooleanParameter(request, "forceLogin", false);
        this.clientIP = getClientIP(request);
    }

    public String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Read-IP");
        log.info("> X-Real-IP : " + ip);
        if(ip == null) {
            ip = request.getHeader("X-Forwarded-For");
            log.info("> X-FORWARDED-FOR : " + ip);
        }

        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
            log.info("> Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            log.info(">  WL-Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            log.info("> HTTP_CLIENT_IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            log.info("> HTTP_X_FORWARDED_FOR : " + ip);
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
            log.info("> getRemoteAddr : "+ip);
        }
        log.debug("request.getHeader(\"X-Read-IP\") : {}", request.getHeader("X-Read-IP"));
        log.debug("request.getHeader(\"X-Forwarded-For\") : {}", request.getHeader("X-Forwarded-For"));
        log.debug("request.getHeader(\"Proxy-Client-IP\") : {}", request.getHeader("Proxy-Client-IP"));
        log.debug("request.getHeader(\"WL-Proxy-Client-IP\") : {}", request.getHeader("WL-Proxy-Client-IP"));
        log.debug("request.getHeader(\"HTTP_CLIENT_IP\") : {}", request.getHeader("HTTP_CLIENT_IP"));
        log.debug("request.getHeader(\"HTTP_X_FORWARDED_FOR\") : {}", request.getHeader("HTTP_X_FORWARDED_FOR"));
        log.debug("request.getRemoteAddr() : {}", request.getRemoteAddr());
        log.info("> Result : IP Address : "+ip);

        return ip;
    }
}
