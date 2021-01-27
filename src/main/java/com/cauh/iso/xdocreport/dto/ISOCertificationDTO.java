package com.cauh.iso.xdocreport.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayInputStream;

@Setter
@Getter
public class ISOCertificationDTO {
    private String no;
    private String name;
    private String isoTitle;
    private ByteArrayInputStream sign;
}
