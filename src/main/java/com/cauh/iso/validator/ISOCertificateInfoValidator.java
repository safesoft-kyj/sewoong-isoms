package com.cauh.iso.validator;

import com.cauh.iso.domain.ISOTrainingCertificationInfo;
import com.cauh.iso.repository.ISOTrainingCertificationInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.stream.StreamSupport;

@Component
@Slf4j
@RequiredArgsConstructor
public class ISOCertificateInfoValidator implements Validator {

    private final ISOTrainingCertificationInfoRepository isoTrainingCertificationInfoRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ISOTrainingCertificationInfo isoTrainingCertificationInfo = (ISOTrainingCertificationInfo) target;
        Integer infoId = ObjectUtils.isEmpty(isoTrainingCertificationInfo.getId())?-1:isoTrainingCertificationInfo.getId();

        //본인을 제외한 Info 중에 등록된 사용자와 현재 등록하려는 사용자가 동일한 경우
        if(StreamSupport.stream(isoTrainingCertificationInfoRepository.findAll().spliterator(), false)
                .filter(info -> info.getId() != infoId)
                .filter(info -> info.getManager().getId() == isoTrainingCertificationInfo.getManager().getId()).count() > 0) {
            errors.rejectValue("userId", "message.userId.duplicate", "이미 등록 된 사용자입니다.");
        }

        //Info중에서 본인 ID와 중복되는 것을 제외하고  Active된 Info가 있을 경우,
        if(StreamSupport.stream(isoTrainingCertificationInfoRepository.findAll().spliterator(), false)
                .filter(info -> info.getId() != infoId)
                .filter(info -> info.getActive()).count() > 0) {

            //Info설정을 Active로 하려고 하면 에러 처리
            if(isoTrainingCertificationInfo.getActive()) {
                errors.rejectValue("active", "message.active.duplicate", "현재 Active된 수료증 정보가 존재합니다.");
            }
        }

        if(ObjectUtils.isEmpty(isoTrainingCertificationInfo.getBase64signature())) {
            errors.rejectValue("userId", "message.active.duplicate", "해당 사용자의 서명 정보가 없습니다.");
        }

    }
}
