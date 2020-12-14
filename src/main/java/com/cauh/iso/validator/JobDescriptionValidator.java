package com.cauh.iso.validator;

import com.cauh.common.entity.JobDescription;
import com.cauh.iso.service.JobDescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class JobDescriptionValidator implements Validator {
    private final JobDescriptionService jobDescriptionService;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        JobDescription jobDescription = (JobDescription) o;

        if(StringUtils.isEmpty(jobDescription.getShortName())) {
            errors.rejectValue("shortName", "message.required", "필수 입력 항목입니다.");
        } else if (StringUtils.isEmpty(jobDescription.getId()) && jobDescriptionService.findByShortName(jobDescription.getShortName()).isPresent()) {
            errors.rejectValue("shortName", "message.required", "이미 사용중 입니다.");
        } else if (!StringUtils.isEmpty(jobDescription.getId()) &&
                jobDescriptionService.findByShortNameAndIdNot(jobDescription.getShortName(), jobDescription.getId()).isPresent()) {
            errors.rejectValue("shortName", "message.required", "이미 사용중 입니다.");
        }

        if(StringUtils.isEmpty(jobDescription.getTitle())) {
            errors.rejectValue("title", "message.required", "필수 입력 항목입니다.");
        }
    }
}