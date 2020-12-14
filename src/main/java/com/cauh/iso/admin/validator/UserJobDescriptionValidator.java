package com.cauh.iso.admin.validator;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.JobDescription;
import com.cauh.common.entity.UserJobDescription;
import com.cauh.common.repository.JobDescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserJobDescriptionValidator implements Validator {
    private final JobDescriptionRepository jobDescriptionRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        Account account = (Account)o;
        int i = 0;
        for(UserJobDescription jd : account.getUserJobDescriptions()) {
            if(ObjectUtils.isEmpty(jd.getAssignDate())) {
                errors.rejectValue("userJobDescriptions[" + i +"].assignDate", "message.required", "필수 입력 항목 입니다.");
            }

            Optional<JobDescription> optionalJd = jobDescriptionRepository.findById(jd.getJobDescription().getId());
            if(optionalJd.isPresent()) {
                if(!optionalJd.get().isEnabled()) {
                    errors.rejectValue("userJobDescriptions[" + i +"].jobDescription.id", "message.required", "Inactive 된 직무는 선택 할 수 없습니다.");
                }
            }

            i ++;
        }
    }
}
