package com.dtnsm.esop.domain.constant;

public enum OfflineTrainingStatus {
    SUBMITTED("요청중", "info"), APPROVED("적용됨", "success"), DELETED("삭제됨", "danger");

    private String label;
    private String className;

    OfflineTrainingStatus(String label, String className) {
        this.label = label;
        this.className = className;
    }

    public String getLabel() {
        return label;
    }

    public String getClassName() {
        return className;
    }
}
