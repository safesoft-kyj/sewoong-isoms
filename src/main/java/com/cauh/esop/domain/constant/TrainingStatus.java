package com.cauh.esop.domain.constant;

public enum TrainingStatus {
    NOT_STARTED("Not Started", "default"),
    PROGRESS("Progress", "warning"),
    TRAINING_COMPLETED("Training Completed", "info"),
    TEST_FAILED("Re-test", "danger"),
    COMPLETED("Pass", "success");

    private String label;
    private String className;

    TrainingStatus(String label, String className) {
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
