package com.cauh.iso.validator;

import com.cauh.iso.domain.ISO;
import com.cauh.iso.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Slf4j
public class ISOValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        ISO iso = (ISO) o;

        if (StringUtils.isEmpty(iso.getTitle())) {
            errors.rejectValue("title", "message.iso.title.required", "제목을 입력해 주세요.");
        }

        if (StringUtils.isEmpty(iso.getContent())) {
            errors.rejectValue("content", "message.iso.content.required", "내용을 입력해 주세요.");
        }

        //attatchFile이 존재하고 1개 이상의 파일이 업로드 된 경우,
        if (!ObjectUtils.isEmpty(iso.getUploadFileName())) {
            //New Case
            if (ObjectUtils.isEmpty(iso.getAttachFiles())) {
                //filename에 pdf 확장자가 없는 경우.
                if (!iso.getUploadFileName().endsWith(".pdf")) {
                    errors.rejectValue("attachFiles", "message.file.no.pdf", "파일 형식이 맞지 않습니다.(PDF 파일)");
                }
            } else if (!ObjectUtils.isEmpty(iso.getAttachFiles())) { //Edit Case
                //filename에 pdf 확장자가 없는 경우.
                if (!iso.getUploadFileName().endsWith(".pdf")) {
                    errors.rejectValue("attachFiles[0].deleted", "message.file.no.pdf", "파일 형식이 맞지 않습니다.(PDF 파일)");
                }
            }
        } else if(ObjectUtils.isEmpty(iso.getAttachFiles()) && ObjectUtils.isEmpty(iso.getUploadFileName())) { // 처음 글 올릴 때 파일이 없으면
            log.info("@@@@@@@@Check");
            errors.rejectValue("attachFiles", "message.file.no.upload", "파일을 첨부해 주세요.");
        }

        if (iso.isTraining()) { //iso training으로 등록 할 경우,
            if (ObjectUtils.isEmpty(iso.getStartDate())) {
                errors.rejectValue("startDate", "message.iso.startDate.required", "시작 날짜를 입력해주세요.");
            }

            if (ObjectUtils.isEmpty(iso.getEndDate())) {
                errors.rejectValue("endDate", "message.iso.endDate.required", "종료 날짜를 입력해주세요.");
            }

            //endDate가 startDate보다 앞에 있을경우,
            if (!(ObjectUtils.isEmpty(iso.getStartDate()) || ObjectUtils.isEmpty(iso.getEndDate())) && iso.getStartDate().after(iso.getEndDate())) {
                errors.rejectValue("endDate", "message.iso.startDate.futured", "종료 날짜는 시작 날짜보다 과거일 수 없습니다.");
            }

            if (ObjectUtils.isEmpty(iso.getHour())) {
                errors.rejectValue("hour", "message.iso.endDate.required", "교육 시간을 입력해주세요");
            }

            if (!iso.isTrainingAll() && ObjectUtils.isEmpty(iso.getUserIds())) {
                errors.rejectValue("userIds", "message.iso.userIds.required", "교육 참석자를 등록해주세요");
            }

        } else { //ISO Training이 아닌 경우, training 관련 필드 null처리
            iso.setHour(null);
        }

    }
}