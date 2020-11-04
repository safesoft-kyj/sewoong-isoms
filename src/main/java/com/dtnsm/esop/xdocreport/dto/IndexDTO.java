package com.dtnsm.esop.xdocreport.dto;

import com.dtnsm.esop.domain.Category;
import lombok.Data;

import java.util.List;

@Data
public class IndexDTO {
    private String issueDate;
    private List<Category> categories;
    private List<IndexReport> docs;
}
