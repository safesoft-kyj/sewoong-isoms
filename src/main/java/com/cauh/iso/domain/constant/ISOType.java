package com.cauh.iso.domain.constant;

import lombok.Getter;

@Getter
public enum ISOType {
    ISO_14155("ISO-14155", "primary"),
    ISO_CERT_STATUS("인증현황", "mint"),
    ISO_14155_CERT("14155 수료증", "success");

    String label;
    String className;

    ISOType(String label, String className) {
        this.label = label;
        this.className = className;
    }
}
