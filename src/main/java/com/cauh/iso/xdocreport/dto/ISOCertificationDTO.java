package com.cauh.iso.xdocreport.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayInputStream;

@Setter
@Getter
public class ISOCertificationDTO {
    private String affiliationDepartment;
    private String dateOfBirth;
    private String name;
    private String certificateNo;
    private String completionDate;
    private ByteArrayInputStream sign;
}
