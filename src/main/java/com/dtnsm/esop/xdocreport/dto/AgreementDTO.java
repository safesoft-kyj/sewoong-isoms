package com.dtnsm.esop.xdocreport.dto;

import lombok.Data;

import java.io.ByteArrayInputStream;

@Data
public class AgreementDTO {
    private String customerName;
    private String jobTitleAndCompany;
    private ByteArrayInputStream sign;
    private String agreementDate;
}
