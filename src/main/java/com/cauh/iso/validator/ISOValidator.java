package com.cauh.iso.validator;

import com.cauh.iso.domain.ISO;
import com.cauh.iso.domain.ISOAttachFile;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ISOValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        ISO iso = (ISO)o;

        if(StringUtils.isEmpty(iso.getTitle())) {
            errors.rejectValue("title", "message.required", "제목을 입력해 주세요.");
        }

        if(StringUtils.isEmpty(iso.getContent())) {
            errors.rejectValue("content", "message.required", "내용을 입력해 주세요.");
        }

        //attatchFile이 존재하고 1개 이상의 파일이 업로드 된 경우,
        if(!ObjectUtils.isEmpty(iso.getUploadFileNames())) {

            //new 일 때,
            if(ObjectUtils.isEmpty(iso.getAttachFiles())) {
                //filename에 pdf 확장자가 없는 경우.
                for(String fileName : iso.getUploadFileNames()) {
                    if(!fileName.endsWith(".pdf")) {
                        errors.rejectValue("attachFiles", "message.file.no.pdf", "파일 형식이 맞지 않습니다.(PDF 파일)");
                        break;
                    }
                }
            }else if(!ObjectUtils.isEmpty(iso.getAttachFiles())) {
                //filename에 pdf 확장자가 없는 경우.
                for(String fileName : iso.getUploadFileNames()) {
                    if(!fileName.endsWith(".pdf")) {
                        errors.rejectValue("attachFiles[0].deleted", "message.file.no.pdf", "파일 형식이 맞지 않습니다.(PDF 파일)");
                        break;
                    }
                }
            }
        }
    }
}