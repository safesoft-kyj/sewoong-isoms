package com.cauh.esop.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Data
public class MatrixId implements Serializable {
    private static final long serialVersionUID = 7531597595447332612L;
    private Integer userId;

//    private String documentVersionId;

    private Integer trainingPeriodId;
}
