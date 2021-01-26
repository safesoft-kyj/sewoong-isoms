package com.cauh.iso.domain.constant;

import lombok.Getter;

@Getter()
public enum ISOType {
    ISO_14155("ISO-14155"), ISO_9001("ISO-9001");

    String label;

    ISOType(String label){
        this.label = label;
    }
}
