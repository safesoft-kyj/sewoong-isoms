package com.cauh.iso.validator;

import com.cauh.iso.admin.domain.constant.SOPAction;
import com.cauh.iso.domain.Document;
import com.cauh.iso.domain.DocumentVersion;
import com.cauh.iso.domain.constant.DocumentStatus;
import com.cauh.iso.domain.constant.DocumentType;
import com.cauh.iso.service.DocumentService;
import com.cauh.iso.service.DocumentVersionService;
import com.cauh.iso.utils.NumberUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class DocumentVersionValidator implements Validator {
    private final DocumentService documentService;
    private final DocumentVersionService documentVersionService;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        DocumentVersion documentVersion = (DocumentVersion)o;

        if(documentVersion.getDocument().getType() == DocumentType.SOP) {
            if(StringUtils.isEmpty(documentVersion.getDocument().getCategory().getShortName())) {
                errors.rejectValue("document.category.shortName", "message.required", "Category 를 선택해 주세요.");
            }
        } else {
            if(StringUtils.isEmpty(documentVersion.getDocument().getSop().getDocId())) {
                errors.rejectValue("document.sop.docId", "message.required", "SOP 를 선택해 주세요.");
            }
        }

        if(StringUtils.isEmpty(documentVersion.getDocument().getTitle())) {
            errors.rejectValue("document.title", "message.required", "필수 입력 항목 입니다.");
        }
        if (StringUtils.isEmpty(documentVersion.getDocument().getDocumentNo())) {
            errors.rejectValue("document.documentNo", "message.required", "필수 입력 항목 입니다.");
        } else {
            int maxLength = documentVersion.getDocument().getType() == DocumentType.SOP ? 3 : 2;
            if(documentVersion.getDocument().getDocumentNo().length() != maxLength) {
                errors.rejectValue("document.documentNo", "message.length", maxLength+"자리에 맞춰 입력해 주세요.");
            } else if(!NumberUtils.isNumberOnly(documentVersion.getDocument().getDocumentNo())) {
                errors.rejectValue("document.documentNo", "message.typeMismatch", "숫자만 입력해 주세요.");
            }
        }
        if (StringUtils.isEmpty(documentVersion.getDocument().getDocId())) {
            errors.rejectValue("document.docId", "message.required", "필수 입력 항목 입니다.");
        } else {
            if(StringUtils.isEmpty(documentVersion.getDocument().getId())) {//신규 입력
                Optional<Document> optionalDocument = documentService.findByDocId(documentVersion.getDocument().getDocId());
                if(optionalDocument.isPresent()) {
                    errors.rejectValue("document.docId", "message.exist", "이미 사용중인 Document Id 입니다.");
                }
            }
        }

        if(StringUtils.isEmpty(documentVersion.getVersion())) {
            errors.rejectValue("version", "message.required", "필수 입력 항목 입니다.");
        } else {
            if(!NumberUtils.isNumber(documentVersion.getVersion())) {
                errors.rejectValue("version", "message.typeMismatch", "버전정보 형식이 올바르지 않습니다. ex) 1.1");
            } else {
                if(ObjectUtils.isEmpty(documentVersion.getAction()) == false) {
                    DocumentVersion docVer = documentVersionService.findById(documentVersion.getId());
                    if (documentVersion.getAction() == SOPAction.revision) {
                        if (Double.parseDouble(docVer.getVersion()) >= Double.parseDouble(documentVersion.getVersion())) {
                            errors.rejectValue("version", "message.prev_version", "이전 버전과 같거나 작을수 없습니다.");
                        }

                    }
                }
            }
        }

        if(StringUtils.isEmpty(documentVersion.getEffectiveDate())) {
            errors.rejectValue("effectiveDate", "message.required", "필수 입력 항목 입니다.");
        } else {
            Date now = new Date();

            if(documentVersion.getStatus() == DocumentStatus.APPROVED) {
                if(now.compareTo(documentVersion.getEffectiveDate()) >= 0) {
                    errors.rejectValue("effectiveDate", "message.past.effectiveDate", "Effective Date는 과거 일 수 없습니다.");
                }
            } else if(documentVersion.getStatus() == DocumentStatus.EFFECTIVE) {
                if(now.compareTo(documentVersion.getEffectiveDate()) < 0) {
                    errors.rejectValue("effectiveDate", "message.past.effectiveDate", "Effective Date는 미래 일 수 없습니다.");
                }
            }
        }


        if(documentVersion.getDocument().getType() == DocumentType.SOP) {
            if (documentVersion.isTrainingAll() == false && ObjectUtils.isEmpty(documentVersion.getJdIds())) {
                errors.rejectValue("jdIds", "message.required", "필수 입력 항목 입니다.");
            }
            if(StringUtils.isEmpty(documentVersion.getOriginalFileName()) || documentVersion.getAction() == SOPAction.revision) {
                if (ObjectUtils.isEmpty(documentVersion.getUploadSopDocFile()) || documentVersion.getUploadSopDocFile().isEmpty()) {
                    errors.rejectValue("uploadSopDocFile", "message.required", "필수 입력 항목 입니다.");
                }
            }
        } else {
            log.debug(" ==> RF check");
            if((StringUtils.isEmpty(documentVersion.getRfEngOriginalFileName()) && StringUtils.isEmpty(documentVersion.getRfKorOriginalFileName()))
                    || documentVersion.getAction() == SOPAction.revision) {
                if ((ObjectUtils.isEmpty(documentVersion.getUploadRfEngFile()) || documentVersion.getUploadRfEngFile().isEmpty()) &&
                        (ObjectUtils.isEmpty(documentVersion.getUploadRfKorFile()) || documentVersion.getUploadRfKorFile().isEmpty())) {
                    errors.rejectValue("uploadRfKorFile", "message.required", "[KOR/ENG] 중 하나는 필수 등록 되어야 합니다.");
                }
            }
        }
    }
}
