package com.cauh.iso.domain.constant;

public enum TrainingLogType {
    ISO_TRAINING_LOG("ISO TR.Log", "mint"),
    SOP_TRAINING_LOG("SOP TR.Log", "primary");

    String label;
    String className;

    TrainingLogType(String label, String className){
        this.label = label;
        this.className = className;
    }

    public String getLabel() {
        return label;
    }

    public String getClassName(){
        return className;
    }

}
