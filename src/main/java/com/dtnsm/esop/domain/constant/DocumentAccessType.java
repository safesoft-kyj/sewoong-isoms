package com.dtnsm.esop.domain.constant;

public enum DocumentAccessType {
    DOWNLOAD("purple"),
    VIEWER("info"),
    TRAINING("warning");

    private String className;
    DocumentAccessType(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
