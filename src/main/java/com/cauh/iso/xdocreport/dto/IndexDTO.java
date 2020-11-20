package com.cauh.iso.xdocreport.dto;

import com.cauh.iso.domain.Category;
import lombok.Data;

import java.util.List;

@Data
public class IndexDTO {
    private String issueDate;
    private List<Category> categories;
    private List<IndexReport> docs;
}
