package com.cauh.iso.admin.domain.constant;

import com.cauh.common.entity.Account;
import com.cauh.iso.domain.Document;
import com.cauh.iso.domain.DocumentVersion;
import com.cauh.iso.domain.Notice;
import lombok.Getter;

@Getter
public enum AuditComponent {

    ACCOUNT(Account.class, "account_change_history"),
    NOTICE(Notice.class, "notice_change_history"),
    DOCUMENT(Document.class, "document_change_history"),
    DOCUMENT_VERSION(DocumentVersion.class, "document_version_change_history");

    private Class<?> classType;

    private String viewName;

    AuditComponent(Class<?> classType, String viewName) {
        this.classType = classType;
        this.viewName = viewName;
    }

}
