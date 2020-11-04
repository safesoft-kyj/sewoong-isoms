package com.dtnsm.esop.domain.constant;

public enum ApprovalLineType {
    requester("Requested by"),
    reviewer("Reviewed by"),
    approver("Approved by");
//    confirm("Confirmed by");

    private String label;
    ApprovalLineType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }


}
