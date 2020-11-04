package com.dtnsm.esop.validator;

import com.dtnsm.esop.domain.Quiz;
import com.dtnsm.esop.domain.QuizAnswer;
import com.dtnsm.esop.domain.QuizQuestion;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuizValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        Quiz quiz = (Quiz)o;

        int size = quiz.getQuizQuestions().size();
        for(int i = 0; i < size; i ++) {
            QuizQuestion quizQuestion = quiz.getQuizQuestions().get(i);
            int answerSize = quizQuestion.getAnswers().size();
            if(StringUtils.isEmpty(quizQuestion.getText())) {
                errors.rejectValue("quizQuestions[" + i +"].text", "message.required", "필수 입력 항목입니다.");
            }

            for(int x = 0; x < answerSize; x ++) {
                if(StringUtils.isEmpty(quizQuestion.getAnswers().get(x).getText())) {
                    errors.rejectValue("quizQuestions[" + i +"].answers[" + x + "].text", "message.required", "필수 입력 항목입니다.");
                }
            }

            List<QuizAnswer> quizAnswers = quizQuestion.getAnswers().stream().filter(a -> a.isCorrect() == true).collect(Collectors.toList());
            if(ObjectUtils.isEmpty(quizAnswers)) {
                errors.rejectValue("quizQuestions[" + i +"].text", "message.required", "선택된 정답이 없습니다.");
            } else {
                quizQuestion.setCorrect(quizAnswers.stream().map(a -> a.getIndex()).collect(Collectors.toList()).toArray(Integer[]::new));
            }
        }
    }
}
