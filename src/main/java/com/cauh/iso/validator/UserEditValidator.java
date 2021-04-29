package com.cauh.iso.validator;

import com.cauh.common.entity.Account;
import com.cauh.iso.utils.DateUtils;
import io.micrometer.core.instrument.util.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Component
public class UserEditValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Account account = (Account) target;

        //TODO 2021-04-07 :: Validator 정보 수정 (연락처 제외)
        if(ObjectUtils.isEmpty(account.getBirthDate())){
            errors.rejectValue("birthDate", "message.birthDate.isEmpty", "생년월일이 입력되지 않았습니다.");
        }
        else if(ObjectUtils.isEmpty(account.getIndate())){
            errors.rejectValue("indate", "message.indate.isEmpty", "입사일이 입력되지 않았습니다.");
        }
        else if(StringUtils.isEmpty(account.getEmail())){
            errors.rejectValue("email", "message.email.isEmpty", "이메일이 입력되지 않았습니다.");
        }
    }
}
