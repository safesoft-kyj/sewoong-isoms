package com.cauh.iso.xdocreport.dto;

import com.cauh.common.utils.Base64Utils;
import com.cauh.iso.domain.constant.ApprovalLineType;
import lombok.Data;

import java.io.ByteArrayInputStream;

@Data
public class ApprovalLineDTO {
    private ApprovalLineType lineType;
    private String strLineType;
    private String name;
    private String jobTitle;
    private ByteArrayInputStream sign;
    private String date;
    private String comments;

    public ApprovalLineDTO(ApprovalLineType lineType, String strLineType, String name, String jobTitle, String base64str, String date, String comments) {
        this.lineType = lineType;
        this.strLineType = strLineType;
        this.name = name;
        this.jobTitle = jobTitle;
        this.date = date;
        this.comments = comments;
        this.sign = new ByteArrayInputStream(Base64Utils.decodeBase64ToBytes(base64str));
    }

}
