package com.cauh.esop.xdocreport.dto;

import com.cauh.common.entity.Account;
import lombok.Data;

import java.io.ByteArrayInputStream;
import java.util.List;

@Data
public class TrainingLogDTO {
    private ByteArrayInputStream sign;
    private Account user;
    private List<TrainingLogReport> trainingLogs;
    private String printDate;
}
