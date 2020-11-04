package com.dtnsm.esop.validator;

import com.dtnsm.esop.domain.OfflineTraining;
import com.dtnsm.esop.domain.OfflineTrainingDocument;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class OfflineTrainingValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        OfflineTraining offlineTraining = (OfflineTraining)o;

        if(ObjectUtils.isEmpty(offlineTraining.getTrainingDate())) {
            errors.rejectValue("trainingDate", "message.required", "필수 입력 항목입니다.");
        }

        if(StringUtils.isEmpty(offlineTraining.getOrganization())) {
            errors.rejectValue("organization", "message.required", "필수 입력 항목입니다.");
        }

        if(ObjectUtils.isEmpty(offlineTraining.getSopIds())) {
            errors.rejectValue("sopIds", "message.required", "SOP 정보가 없습니다.");
        } else {
            int i = 0;
            for(OfflineTrainingDocument doc : offlineTraining.getOfflineTrainingDocuments()) {
                if(StringUtils.isEmpty(doc.getHour())) {
                    errors.rejectValue("offlineTrainingDocuments[" + i +"].hour", "message.required", "교육 시간을 입력해 주세요.");
                }
                i ++;
            }
        }


        if(ObjectUtils.isEmpty(offlineTraining.getAttendees())) {
            errors.rejectValue("attendees", "message.required", "참석자 정보를 선택해 주세요.");
        }

    }
}
