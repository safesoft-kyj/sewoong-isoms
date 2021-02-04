package com.cauh.iso.security.annotation;


import org.springframework.security.access.prepost.PostFilter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PostFilter("(authentication.principal.userType.name() == 'ADMIN ') OR (authentication.principal.userType.name() == 'USER')" +
        "or (authentication.principal.userType.name() == 'AUDITOR' and authentication.principal.allowedRDMap.containsKey(filterObject.id))")
//@PreAuthorize("(authentication.principal.userType.name() == 'GROUP_WARE') " +
//        "or ((#stringStatus == 'current' and authentication.principal.userType.name() == 'AUDITOR' and #sopId eq null) " +
//        "or (#stringStatus == 'current' and authentication.principal.userType.name() == 'AUDITOR' and authentication.principal.allowedSOP.contains(#sopId)))")
public @interface IsAllowedRF {
    /**
     * 그룹웨어 사용자
     * 외부 사용자 인경우 허용한 RD만 접근 허용
     */
}
