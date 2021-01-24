package com.cauh.iso.validator;

import com.cauh.iso.domain.AgreementsWithdrawal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Slf4j
public class AgreementsWithdrawalValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        AgreementsWithdrawal agreementsWithdrawal = (AgreementsWithdrawal) o;

        if(ObjectUtils.isEmpty(agreementsWithdrawal.getWithdrawalDate())) {
            errors.rejectValue("withdrawalDate", "message.withdrawalDate.required", "철회 날짜를 입력해 주세요.");
        }

        if(ObjectUtils.isEmpty(agreementsWithdrawal.getEmail())) {
            errors.rejectValue("email", "message.email.required", "이메일 주소를 입력해 주세요.");
        }

    }
}
