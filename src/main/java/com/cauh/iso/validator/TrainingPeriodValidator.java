package com.cauh.iso.validator;

import com.cauh.iso.domain.TrainingPeriod;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TrainingPeriodValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        TrainingPeriod trainingPeriod = (TrainingPeriod)o;

        if(ObjectUtils.isEmpty(trainingPeriod.getStartDate())) {
            errors.rejectValue("startDate", "message.required", "시작일을 입력해 주세요.");
        }
        if(ObjectUtils.isEmpty(trainingPeriod.getEndDate())) {
            errors.rejectValue("startDate", "message.required", "종료일을 입력해 주세요.");
        }
    }
}
