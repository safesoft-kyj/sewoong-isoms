package com.cauh.iso.domain.constant;

public enum ReportType {
    SOP_Training_Deviation_Report("SOP Training Deviation Report", "sop_deviationReport", "app_sop_deviation_report", true),
    SOP_Waiver_Approval_Form("SOP Waiver Request and Approval Form", "sop_waiver_approvalForm", "app_sop_waiver_approval_form", false),
    SOP_RF_Request_Form("SOP/RF Request Form", "sop_rf_requestForm", "app_sop_rf_request_form", false),
    SOP_RF_Retirement_Form("SOP/RF Retirement Form", "sop_rf_retirementForm", "app_sop_rf_retirement_form", false),
    SOP_Disclosure_Request_Form("SOP & Disclosure Request Form", "sop_disclosure_requestForm", "app_sop_disclosure_request_form", true);

    private String label;
    private String viewName;
    private String emailTemplate;
    private Boolean enable;

    ReportType(String label, String viewName, String emailTemplate, Boolean enable) {
        this.label = label;
        this.viewName = viewName;
        this.emailTemplate = emailTemplate;
        this.enable = enable;
    }

    public String getLabel() {
        return label;
    }

    public String getViewName() {
        return viewName;
    }

    public String getEmailTemplate() {
        return emailTemplate;
    }

    public Boolean isEnable(){return enable;}
}
