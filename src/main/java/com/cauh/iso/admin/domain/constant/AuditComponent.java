package com.cauh.iso.admin.domain.constant;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.UserJobDescriptionChangeLog;
import com.cauh.iso.domain.*;
import com.cauh.iso.domain.report.SOPDeviationReport;
import com.cauh.iso.domain.report.SOPDisclosureRequestForm;
import lombok.Getter;

@Getter
public enum AuditComponent {

    ACCOUNT(Account.class, "account_change_history"),
    USER_ROLE(UserJobDescriptionChangeLog.class, "user_role_change_history"),
    NOTICE(Notice.class, "notice_change_history"),
    CERTIFICATIONS(ISOCertification.class, "certifications_change_history"),
    DOCUMENT(Document.class, "document_change_history"),
    DOCUMENT_VERSION(DocumentVersion.class, "document_version_change_history"),
    SOP_TRAINING(TrainingLog.class, "sop_training_change_history"),
    ISO(ISO.class, "iso_change_history"),
    ISO_TRAINING(ISOTrainingLog.class, "iso_training_change_history"),
    APPROVAL(Approval.class, "approval_change_history"),
    SOP_TRAINING_DEVIATION(SOPDeviationReport.class, "sop_training_deviation_change_history"),
    SOP_ISO_DISCLOSURE(SOPDisclosureRequestForm.class, "sop_iso_disclosure_change_history");

    private Class<?> classType;

    private String viewName;

    AuditComponent(Class<?> classType, String viewName) {
        this.classType = classType;
        this.viewName = viewName;
    }

}
