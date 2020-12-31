package com.cauh.iso.validator;

import com.cauh.iso.domain.ISOCertification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Slf4j
public class ISOCertificationValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        ISOCertification isoCertification = (ISOCertification)o;

        if(StringUtils.isEmpty(isoCertification.getTitle())) {
            errors.rejectValue("title", "message.required", "제목을 입력해 주세요.");
        }

        if(StringUtils.isEmpty(isoCertification.getContent())) {
            errors.rejectValue("content", "message.required", "내용을 입력해 주세요.");
        }

        if(ObjectUtils.isEmpty(isoCertification.getCertDate())) {
            errors.rejectValue("certDate", "message.required", "날짜 정보를 입력해 주세요.");
        }
    }
}
