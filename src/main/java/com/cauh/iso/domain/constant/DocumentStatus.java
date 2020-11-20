package com.cauh.iso.domain.constant;

public enum DocumentStatus {
    DEVELOPMENT("Development"),
    REVISION("Revision"),
    APPROVED("Approved"),
    EFFECTIVE("Effective"),
    RETIREMENT("Retirement"),
    SUPERSEDED("Superseded"),
    CONFIDENTIAL("Confidential"),
    REMOVED("REMOVED");

    private String label;

    DocumentStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
