package com.cauh.iso.domain.constant;

public enum TrainingRequirement {
    mandatory("My"), optional("Optional");

    private String label;

    TrainingRequirement(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
