package com.cauh.iso.validator;

import com.cauh.iso.domain.Notice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class NoticeValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        Notice notice = (Notice)o;

        if(StringUtils.isEmpty(notice.getTitle())) {
            errors.rejectValue("title", "message.required", "제목을 입력해 주세요.");
        }

        if(StringUtils.isEmpty(notice.getContent())) {
            errors.rejectValue("content", "message.required", "내용을 입력해 주세요.");
        }
    }
}
