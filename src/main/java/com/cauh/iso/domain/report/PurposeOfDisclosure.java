package com.cauh.iso.domain.report;

public enum PurposeOfDisclosure {
    AUDIT("Audit"),
    INSPECTION("Inspection"),
    ASSESSMENT("Assessment/Evaluation Visit"),
    OTHER("Other");


    private String label;

    PurposeOfDisclosure(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
