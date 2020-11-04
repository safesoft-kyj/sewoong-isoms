package com.dtnsm.esop.domain.constant;

public enum TrainingType {
    SELF("Self-training"),
    RE_TRAINING("Re-Training"),
    REFRESH("Refresh"),
    OTHER("Other");


    private String label;
    TrainingType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
