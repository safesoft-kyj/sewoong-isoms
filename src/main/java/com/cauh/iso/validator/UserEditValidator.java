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

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String strBirthDate = df.format(account.getBirthDate());

        if(ObjectUtils.isEmpty(account.getPhone())){
            errors.rejectValue("account.phone", "message.phone.isEmpty", "연락처가 입력되지 않았습니다.");
        }else if(ObjectUtils.isEmpty(strBirthDate)){
            errors.rejectValue("account.birthDate", "message.birthDate.isEmpty", "생년월일이 입력되지 않았습니다.");
        }else if(StringUtils.isEmpty(account.getEmail())){
            errors.rejectValue("account.email", "message.email.isEmpty", "이메일이 입력되지 않았습니다.");
        }
    }
}
