package com.cauh.iso.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartmentDTO {
    private Integer id;
    private Integer pid;
    private String name;
    private String depthFullname;
}
