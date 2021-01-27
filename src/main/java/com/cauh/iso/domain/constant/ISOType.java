package com.cauh.iso.domain.constant;

import lombok.Getter;

@Getter
public enum ISOType {
    ISO_14155("ISO-14155"), ISO_CERT_STATUS("ISO 인증현황"), ISO_14155_CERT("ISO-14155 수료증");

    String label;
    ISOType(String label){
        this.label = label;
    }
}
