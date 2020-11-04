package com.dtnsm.esop.xdocreport.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class RetirementDocumentDTO {
    private String type;
    private String docId;
    private String title;
    private String ver;
    private String effectiveDate;
    private String reason;

    @Builder
    public RetirementDocumentDTO(String type, String docId, String title, String ver, String effectiveDate, String reason) {
        this.type = type;
        this.docId = docId;
        this.title = title;
        this.ver = ver;
        this.effectiveDate = effectiveDate;
        this.reason = reason;
    }
}
