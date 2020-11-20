package com.cauh.iso.domain.constant;

public enum ReportType {
    SOP_Deviation_Report("SOP Deviation Report", "sop_deviationReport", "app_sop_deviation_report"),
    SOP_Waiver_Approval_Form("SOP Waiver Request and Approval Form", "sop_waiver_approvalForm", "app_sop_waiver_approval_form"),
    SOP_RD_Request_Form("SOP/RD Request Form", "sop_rd_requestForm", "app_sop_rd_request_form"),
//    RD_Approval_Form("RD Approval Form", "rd_approvalForm"),
    SOP_RD_Retirement_Form("SOP/RD Retirement Form", "sop_rd_retirementForm", "app_sop_rd_retirement_form"),
    SOP_Disclosure_Request_Form("SOP & Digital Binder Disclosure Request Form", "sop_disclosure_requestForm", "app_sop_disclosure_request_form");

    private String label;
    private String viewName;
    private String emailTemplate;
    ReportType(String label, String viewName, String emailTemplate) {
        this.label = label;
        this.viewName = viewName;
        this.emailTemplate = emailTemplate;
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
}
