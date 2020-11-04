package com.dtnsm.common.security.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class GroupwareUserAuthService {
    @Value("${gw.login.url}")
    private String loginURL;

    public boolean authenticate(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("txtUserid", username);
        params.put("txtpassword", password);

        log.debug("==> 그룹웨어 로그인 체크 \n- URL : {}\n- Params : {}", loginURL, params);

        String response = restTemplate.getForObject(loginURL, String.class, params);
        log.debug("<== 그룹웨어 응답 : {}", response);

        if("Y".equals(response)) {
            return true;
        } else {
            return false;
        }
    }
}
