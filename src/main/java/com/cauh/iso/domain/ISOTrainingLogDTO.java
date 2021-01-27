package com.cauh.iso.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class ISOTrainingLogDTO implements Serializable {
    private static final long serialVersionUID = 6895213327657405132L;
    private Integer index;
    private Integer id;
    private String completionDate;
    private String course;
    private String hour;
    private String organization;
    private String certId;
    private String certHtml;
}
