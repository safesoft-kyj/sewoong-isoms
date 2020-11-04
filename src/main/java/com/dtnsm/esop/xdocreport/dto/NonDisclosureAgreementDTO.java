package com.dtnsm.esop.xdocreport.dto;

import lombok.Data;

import java.io.ByteArrayInputStream;

@Data
public class NonDisclosureAgreementDTO {
    private String customerName;
    private String jobTitleAndCompany;
    private ByteArrayInputStream sign;
    private String purpose;
    private String purposeOther;
    private String agreementDate;

}
