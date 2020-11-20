package com.cauh.iso.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Quiz implements Serializable {
    private static final long serialVersionUID = -9134284330649267978L;

    private List<QuizQuestion> quizQuestions;
}
