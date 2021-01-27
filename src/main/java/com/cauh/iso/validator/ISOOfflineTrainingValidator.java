package com.cauh.iso.validator;

import com.cauh.iso.domain.ISOOfflineTraining;
import com.cauh.iso.domain.ISOOfflineTrainingDocument;
import com.cauh.iso.domain.OfflineTraining;
import com.cauh.iso.domain.OfflineTrainingDocument;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ISOOfflineTrainingValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        ISOOfflineTraining isoOfflineTraining = (ISOOfflineTraining)o;

        if(ObjectUtils.isEmpty(isoOfflineTraining.getTrainingDate())) {
            errors.rejectValue("trainingDate", "message.required", "필수 입력 항목입니다.");
        }

        if(StringUtils.isEmpty(isoOfflineTraining.getOrganization())) {
            errors.rejectValue("organization", "message.required", "필수 입력 항목입니다.");
        }

        if(ObjectUtils.isEmpty(isoOfflineTraining.getIsoIds())) {
            errors.rejectValue("isoIds", "message.required", "SOP 정보가 없습니다.");
        } else {
            int i = 0;
            for(ISOOfflineTrainingDocument doc : isoOfflineTraining.getIsoOfflineTrainingDocuments()) {
                if(StringUtils.isEmpty(doc.getHour())) {
                    errors.rejectValue("isoOfflineTrainingDocuments[" + i +"].hour", "message.required", "교육 시간을 입력해 주세요.");
                }
                i ++;
            }
        }


        if(ObjectUtils.isEmpty(isoOfflineTraining.getAttendees())) {
            errors.rejectValue("attendees", "message.required", "참석자 정보를 선택해 주세요.");
        }

    }
}
