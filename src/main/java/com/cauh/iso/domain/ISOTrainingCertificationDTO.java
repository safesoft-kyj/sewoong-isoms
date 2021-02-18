package com.cauh.iso.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class ISOTrainingCertificationDTO implements Serializable {
    private static final long serialVersionUID = 6895867392813120132L;

    private Integer index;
    private Integer id;
    private String certNo;
    private String name;
    private String teamDept;
    private String role;
    private String trainingTitle;
    private String isoType;
    private String completionDate;
}
