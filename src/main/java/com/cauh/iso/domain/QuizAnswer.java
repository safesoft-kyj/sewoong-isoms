package com.cauh.iso.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class QuizAnswer implements Serializable {
    private static final long serialVersionUID = 1385872362817839143L;

    public QuizAnswer(Integer index) {
        this.index = index;
    }

    public QuizAnswer(Integer index, String text, boolean correct) {
        this.index = index;
        this.text = text;
        this.correct = correct;
    }

    private Integer index;

    private String text;

    private boolean correct;
}
