package com.cauh.iso.domain.report;

public enum DocumentAccess {
    PDF("PDF"),
    HARDCOPY("HARDCOPY"),
    OTHER("Other");

    private String label;
    DocumentAccess(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
