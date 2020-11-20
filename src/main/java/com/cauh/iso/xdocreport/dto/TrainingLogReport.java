package com.cauh.iso.xdocreport.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class TrainingLogReport implements Serializable {
    private static final long serialVersionUID = 5355111172113870421L;

    private String completeDate;

    private String course;

    private String hr;

    private String organization;

    @Builder
    public TrainingLogReport(String completeDate, String course, String hr, String organization) {
        this.completeDate = completeDate;
        this.course = course;
        this.hr = hr;
        this.organization = organization;
    }
}
