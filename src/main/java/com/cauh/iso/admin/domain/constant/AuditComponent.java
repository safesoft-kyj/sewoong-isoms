package com.cauh.iso.admin.domain.constant;

import com.cauh.common.entity.Account;
import lombok.Getter;

@Getter
public enum AuditComponent {

    ACCOUNT(Account.class, "account_change_history");

    private Class<?> classType;

    private String viewName;

    AuditComponent(Class<?> classType, String viewName) {
        this.classType = classType;
        this.viewName = viewName;
    }

}
