package com.cauh.esop.domain.constant;

public enum DocumentLanguage {
    KOR("Korean"), ENG("English");

    private String label;

    DocumentLanguage(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
