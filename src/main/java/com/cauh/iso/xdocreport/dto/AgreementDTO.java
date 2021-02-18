package com.cauh.iso.xdocreport.dto;

import lombok.Data;

import java.io.ByteArrayInputStream;

@Data
public class AgreementDTO {
    private String customerName;
    private String roleAndCompany;
    private ByteArrayInputStream sign;
    private String agreementDate;
}
