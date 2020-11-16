package com.cauh.esop.xdocreport.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class IndexReport implements Serializable {
    private static final long serialVersionUID = 9104051622299381743L;

    private String category;
    private String sopNo;
    private String docId;
    private String title;
    private String version;
    private String effectiveDate;

    @Builder
    public IndexReport(String category, String sopNo, String docId, String title, String version, String effectiveDate) {
        this.category = category;
        this.sopNo = sopNo;
        this.docId = docId;
        this.title = title;
        this.version = version;
        this.effectiveDate = effectiveDate;
    }
}
