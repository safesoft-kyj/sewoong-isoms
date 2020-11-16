package com.cauh.esop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@ToString(of = "text")
public class QuizQuestion implements Serializable {
    private static final long serialVersionUID = -9134284330649267978L;

    public QuizQuestion(Integer index) {
        this.index = index;
    }

    private Integer index;

    private String text;

    private List<QuizAnswer> answers;

    private Integer[] correct;

    private Integer[] choices;
}
