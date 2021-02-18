package com.cauh.iso.xdocreport.dto;

import lombok.Data;

import java.io.ByteArrayInputStream;

@Data
public class NonDisclosureAgreementDTO {
    private String customerName;
    private String RoleAndCompany;
    private ByteArrayInputStream sign;
    private String purpose;
    private String purposeOther;
    private String agreementDate;

}
