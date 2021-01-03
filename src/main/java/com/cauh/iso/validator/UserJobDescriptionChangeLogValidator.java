package com.cauh.iso.validator;

import com.cauh.common.entity.UserJobDescriptionChangeLog;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserJobDescriptionChangeLogValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserJobDescriptionChangeLog userJobDescriptionChangeLog = (UserJobDescriptionChangeLog)o;

        if(ObjectUtils.isEmpty(userJobDescriptionChangeLog.getPrevJobDescription())) {
            errors.rejectValue("prevJobDescription", "message.required", "변경 전 role이 확인되지 않습니다.");
        }

        if(ObjectUtils.isEmpty(userJobDescriptionChangeLog.getNextJobDescription())) {
            errors.rejectValue("nextJobDescription", "message.required", "변경 후 role이 확인되지 않습니다.");
        }

        if(ObjectUtils.isEmpty(userJobDescriptionChangeLog.getJdIds())) {
            errors.rejectValue("jdIds", "message.required", "role이 입력되지 않았습니다.");
        }

        if(ObjectUtils.isEmpty(userJobDescriptionChangeLog.getReason())) {
            errors.rejectValue("reason", "message.required", "사유를 입력해 주세요.");
        }

        if(ObjectUtils.isEmpty(userJobDescriptionChangeLog.getAssignDate())) {
            errors.rejectValue("assignDate", "message.required", "배정 날짜를 입력해 주세요.");
        }

    }
}
