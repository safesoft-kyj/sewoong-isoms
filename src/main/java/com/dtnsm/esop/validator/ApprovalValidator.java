package com.dtnsm.esop.validator;

import com.dtnsm.esop.domain.Approval;
import com.dtnsm.esop.domain.constant.ReportType;
import com.dtnsm.esop.domain.report.*;
import com.dtnsm.esop.repository.DocumentRepository;
import com.dtnsm.esop.service.CategoryService;
import com.dtnsm.esop.service.DocumentService;
import com.dtnsm.esop.utils.NumberUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApprovalValidator implements Validator {
    private final DocumentService documentService;
    private final CategoryService categoryService;
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        Approval approval = (Approval)o;

        if(ObjectUtils.isEmpty(approval.getApprovalLines()) || approval.getApprovalLines().size() == 1) {
            errors.rejectValue("approvalLines", "message.required", "필수 입력 항목입니다.");
        }
        if(approval.getType() == ReportType.SOP_Deviation_Report) {
            SOPDeviationReport r = approval.getSopDeviationReport();
            if (StringUtils.isEmpty(r.getDeviationDetails())) {
                errors.rejectValue("sopDeviationReport.deviationDetails", "message.required", "필수 입력 항목입니다.");
            }
            if (StringUtils.isEmpty(r.getCorrectiveCompletionDate())) {
                errors.rejectValue("sopDeviationReport.correctiveCompletionDate", "message.required", "필수 입력 항목입니다.");
            }
            if (StringUtils.isEmpty(r.getPreventiveCompletionDate())) {
                errors.rejectValue("sopDeviationReport.preventiveCompletionDate", "message.required", "필수 입력 항목입니다.");
            }
            if (StringUtils.isEmpty(r.getCorrectiveAction())) {
                errors.rejectValue("sopDeviationReport.correctiveAction", "message.required", "필수 입력 항목입니다.");
            }
            if (StringUtils.isEmpty(r.getPreventiveAction())) {
                errors.rejectValue("sopDeviationReport.preventiveAction", "message.required", "필수 입력 항목입니다.");
            }
        } else if(approval.getType() == ReportType.SOP_RD_Request_Form) {
            SopRdRequestForm form = approval.getSopRdRequestForm();

            if(form.isNewSOPDevelopment()) {
                int idx = 0;
                for(SopRdDevelopmentDoc sop : form.getSopDevelopmentDocs()) {
                    if(StringUtils.isEmpty(sop.getDocId())) {
                        errors.rejectValue("sopRdRequestForm.sopDevelopmentDocs["+idx+"].docId", "message.required", "필수 입력 항목입니다.");
                    } else {
                        sop.setDocId(sop.getDocId().toUpperCase());

                        if(!sop.getDocId().startsWith("SOP-")) {
                            errors.rejectValue("sopRdRequestForm.sopDevelopmentDocs["+idx+"].docId", "message.required", "Document Id는 \"SOP-\"로 시작 되어야 합니다.");
                        } else {
                            String categoryAndNo = sop.getDocId().substring(sop.getDocId().indexOf("-") + 1);
                            log.debug("@categoryAndNo : {}", categoryAndNo);
                            if(categoryAndNo.length() < 6) {
                                errors.rejectValue("sopRdRequestForm.sopDevelopmentDocs["+idx+"].docId", "message.required", "Document Id 형식이 올바르지 않습니다.");
                            } else {
                                String categoryId = categoryAndNo.substring(0, categoryAndNo.length() - 4);
                                if(!com.dtnsm.esop.utils.StringUtils.isAlphabetOnly(categoryId)) {
                                    errors.rejectValue("sopRdRequestForm.sopDevelopmentDocs["+idx+"].docId", "message.required", "Category Id 형식이 올바르지 않습니다.");
                                } else {
                                    String no = categoryAndNo.substring(categoryAndNo.length() - 4);
                                    if (NumberUtils.isNumberOnly(no)) {
                                        sop.setDocNo(no);

                                        if(documentService.findByDocId(sop.getDocId()).isPresent()) {
                                            errors.rejectValue("sopRdRequestForm.sopDevelopmentDocs["+idx+"].docId", "message.required", "Document Id가 이미 존재합니다.");
                                        }
                                    } else {
                                        errors.rejectValue("sopRdRequestForm.sopDevelopmentDocs[" + idx + "].docId", "message.required", "SOP No는 숫자만 입력해 주세요.");
                                    }

                                    sop.setCategoryId(categoryId);

                                    if(!categoryService.findByShortName(categoryId).isPresent()) {
                                        errors.rejectValue("sopRdRequestForm.sopDevelopmentDocs[" + idx + "].docId", "message.required", "Category["+categoryId+"] 가 존재하지 않습니다. SOP 관리자에게 문의해 주세요.");
                                    }
                                }
                            }
                        }
                    }
                    if(StringUtils.isEmpty(sop.getTitle())) {
                        errors.rejectValue("sopRdRequestForm.sopDevelopmentDocs["+idx+"].title", "message.required", "필수 입력 항목입니다.");
                    }
                    if(StringUtils.isEmpty(sop.getVersion())) {
                        errors.rejectValue("sopRdRequestForm.sopDevelopmentDocs["+idx+"].version", "message.required", "필수 입력 항목입니다.");
                    } else if(!NumberUtils.isNumber(sop.getVersion())) {
                        errors.rejectValue("sopRdRequestForm.sopDevelopmentDocs["+idx+"].version", "message.typeMismatch", "버전정보 형식이 올바르지 않습니다. ex) 1.0");
                    }

                    idx ++;
                }
            }
            if(form.isNewRDDevelopment()) {
                int idx = 0;
                for(SopRdDevelopmentDoc rd : form.getRdDevelopmentDocs()) {
                    if(StringUtils.isEmpty(rd.getDocId())) {
                        errors.rejectValue("sopRdRequestForm.rdDevelopmentDocs["+idx+"].docId", "message.required", "필수 입력 항목입니다.");
                    } else {
                        rd.setDocId(rd.getDocId().toUpperCase());

                        if(!rd.getDocId().startsWith("SOP-")) {
                            errors.rejectValue("sopRdRequestForm.rdDevelopmentDocs[" + idx + "].docId", "message.required", "Document Id는 \"SOP-\"로 시작 되어야 합니다.");
                        } else if(rd.getDocId().indexOf("_") == -1) {
                            errors.rejectValue("sopRdRequestForm.rdDevelopmentDocs["+idx+"].docId", "message.required", "Document Id 형식이 올바르지 않습니다.");
                        } else {
                            String rdNoStr = rd.getDocId().substring(rd.getDocId().indexOf("_") + 1);
                            if(!rdNoStr.startsWith("RD") || rdNoStr.length() != 4) {
                                errors.rejectValue("sopRdRequestForm.rdDevelopmentDocs["+idx+"].docId", "message.required", "Document Id 형식이 올바르지 않습니다.");
                            } else {
                                String no = rdNoStr.substring(rdNoStr.length() - 2);
                                if(NumberUtils.isNumberOnly(no)) {
                                    rd.setDocNo(no);

                                    String sopDocId = rd.getDocId().substring(0, rd.getDocId().indexOf("_"));

                                    if(form.getSopDevelopmentDocs().stream().filter(s -> s.getDocId().equals(sopDocId)).count() == 0 &&
                                            !documentService.findByDocId(sopDocId).isPresent()) {
                                        errors.rejectValue("sopRdRequestForm.rdDevelopmentDocs["+idx+"].docId", "message.required", "[" + sopDocId + "] SOP 정보가 존재하지 않습니다.");
                                    } else {
                                        rd.setSopId(sopDocId);

                                        if(documentService.findByDocId(rd.getDocId()).isPresent()) {
                                            errors.rejectValue("sopRdRequestForm.rdDevelopmentDocs["+idx+"].docId", "message.required", "Document Id가 이미 존재합니다.");
                                        }
                                    }
                                } else {
                                    errors.rejectValue("sopRdRequestForm.rdDevelopmentDocs["+idx+"].docId", "message.required", "RD No는 숫자만 입력해 주세요.");
                                }
                            }
                        }
                    }
                    if(StringUtils.isEmpty(rd.getTitle())) {
                        errors.rejectValue("sopRdRequestForm.rdDevelopmentDocs["+idx+"].title", "message.required", "필수 입력 항목입니다.");
                    }
                    if(StringUtils.isEmpty(rd.getVersion())) {
                        errors.rejectValue("sopRdRequestForm.rdDevelopmentDocs["+idx+"].version", "message.required", "필수 입력 항목입니다.");
                    }  else if(!NumberUtils.isNumber(rd.getVersion())) {
                        errors.rejectValue("sopRdRequestForm.rdDevelopmentDocs["+idx+"].version", "message.typeMismatch", "버전정보 형식이 올바르지 않습니다. ex) 1.0");
                    }

                    idx ++;
                }
            }

            if(form.isNewSOPDevelopment() == false && form.isNewRDDevelopment() == false
                    && form.isSopRevision() == false && form.isRdRevision() == false) {
                errors.rejectValue("sopRdRequestForm.rdRevision", "message.required", "선택된 값이 없습니다.");
            }

            if(form.isSopRevision() && ObjectUtils.isEmpty(form.getSopRevisionIds())) {
                errors.rejectValue("sopRdRequestForm.sopRevisionIds", "message.required", "개정할 SOP를 선택해 주세요.");
            }
            if(form.isRdRevision() && ObjectUtils.isEmpty(form.getRdRevisionIds())) {
                errors.rejectValue("sopRdRequestForm.rdRevisionIds", "message.required", "개정할 RD를 선택해 주세요.");
            }

            if(StringUtils.isEmpty(form.getReasonForTheRequest())) {
                errors.rejectValue("sopRdRequestForm.reasonForTheRequest", "message.required", "필수 입력 항목입니다.");
            }
//        } else if(approval.getType() == ReportType.RD_Approval_Form) {
//            RDApprovalForm form = approval.getRdApprovalForm();
//            if(StringUtils.isEmpty(form.getVersion())) {
//                errors.rejectValue("rdApprovalForm.version", "message.required", "필수 입력 항목입니다.");
//            }
//            if(StringUtils.isEmpty(form.getEffectiveDate())) {
//                errors.rejectValue("rdApprovalForm.effectiveDate", "message.required", "필수 입력 항목입니다.");
//            }
//            if(StringUtils.isEmpty(form.getDescription())) {
//                errors.rejectValue("rdApprovalForm.description", "message.required", "필수 입력 항목입니다.");
//            }

        } else if(approval.getType() == ReportType.SOP_Waiver_Approval_Form) {
            SOPWaiverApprovalForm r = approval.getSopWaiverApprovalForm();
//            if (StringUtils.isEmpty(r.getDeviationDetails())) {
//                errors.rejectValue("sopWaiverApprovalForm.deviationDetails", "message.required", "필수 입력 항목입니다.");
//            }
            if (StringUtils.isEmpty(r.getWaiverStartDate())) {
                errors.rejectValue("sopWaiverApprovalForm.waiverStartDate", "message.required", "필수 입력 항목입니다.");
            }
            if (StringUtils.isEmpty(r.getWaiverEndDate())) {
                errors.rejectValue("sopWaiverApprovalForm.waiverEndDate", "message.required", "필수 입력 항목입니다.");
            }
            if (StringUtils.isEmpty(r.getDeviatedSOPDocument().getId())) {
                errors.rejectValue("sopWaiverApprovalForm.deviatedSOPDocument.id", "message.required", "필수 입력 항목입니다.");
            }

            if (!StringUtils.isEmpty(r.getWaiverStartDate()) && !StringUtils.isEmpty(r.getWaiverEndDate())) {
                if(r.getWaiverStartDate().compareTo(r.getWaiverEndDate()) > 0) {
                    errors.rejectValue("sopWaiverApprovalForm.waiverEndDate", "message.required", "종료일이 시작일보다 이전 입니다.");
                }
            }

            if (StringUtils.isEmpty(r.getDeviationDetails())) {
                errors.rejectValue("sopWaiverApprovalForm.deviationDetails", "message.required", "필수 입력 항목입니다.");
            }
        } else if(approval.getType() == ReportType.SOP_Disclosure_Request_Form) {
            SOPDisclosureRequestForm r = approval.getSopDisclosureRequestForm();

            if (StringUtils.isEmpty(r.getCompanyNameOrInstituteName())) {
                errors.rejectValue("sopDisclosureRequestForm.companyNameOrInstituteName", "message.required", "필수 입력 항목입니다.");
            }
            if (StringUtils.isEmpty(r.getNameOfRequester())) {
                errors.rejectValue("sopDisclosureRequestForm.nameOfRequester", "message.required", "필수 입력 항목입니다.");
            }
            if(ObjectUtils.isEmpty(r.getExternalCustomers()) == false) {
                for(int i = 0; i < r.getExternalCustomers().size(); i ++) {
                    ExternalCustomer c = r.getExternalCustomers().get(i);
                    if (StringUtils.isEmpty(c.getName())) {
                        errors.rejectValue("sopDisclosureRequestForm.externalCustomers["+i+"].name", "message.required", "필수 입력 항목입니다.");
                    }
                    if (StringUtils.isEmpty(c.getEmail())) {
                        errors.rejectValue("sopDisclosureRequestForm.externalCustomers["+i+"].email", "message.required", "필수 입력 항목입니다.");
                    } else {
//                        Pattern p = Pattern.compile("^[_0-9a-zA-Z-]+@[0-9a-zA-Z-]+(.[_0-9a-zA-Z-]+)*$");
                        Pattern p = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
                        Matcher m = p.matcher(c.getEmail());
                        if (!m.matches()) {
                            errors.rejectValue("sopDisclosureRequestForm.externalCustomers["+i+"].email", "message.required", "이메일 형식이 올바르지 않습니다.");
                        } else {
                            if(r.getExternalCustomers().size() != r.getExternalCustomers().stream().map(customer -> customer.getEmail()).distinct().count()) {
                                errors.rejectValue("sopDisclosureRequestForm.externalCustomers["+i+"].email", "message.required", "중복 이메일 주소가 있습니다.");
                            }
                        }
                    }
                }
            }
            if (ObjectUtils.isEmpty(r.getRequestStartDate())) {
                errors.rejectValue("sopDisclosureRequestForm.requestStartDate", "message.required", "필수 입력 항목입니다.");
            }
            if (ObjectUtils.isEmpty(r.getRequestEndDate())) {
                errors.rejectValue("sopDisclosureRequestForm.requestEndDate", "message.required", "필수 입력 항목입니다.");
            }

            if(r.getDocumentAccess() == DocumentAccess.OTHER && StringUtils.isEmpty(r.getDocumentAccessOther())) {
                errors.rejectValue("sopDisclosureRequestForm.documentAccessOther", "message.required", "필수 입력 항목입니다.");
            }
            if(r.getPurposeOfDisclosure() == PurposeOfDisclosure.OTHER && StringUtils.isEmpty(r.getPurposeOfDisclosureOther())) {
                errors.rejectValue("sopDisclosureRequestForm.purposeOfDisclosureOther", "message.required", "필수 입력 항목입니다.");
            }

            if(ObjectUtils.isEmpty(r.getSopIds()) && ObjectUtils.isEmpty(r.getRdIds())) {
                errors.rejectValue("sopDisclosureRequestForm.sopIds", "message.required", "필수 입력 항목입니다.");
            }
        } else if(approval.getType() == ReportType.SOP_RD_Retirement_Form) {
            RetirementApprovalForm r = approval.getRetirementApprovalForm();

            if(ObjectUtils.isEmpty(r.getSopIds()) && ObjectUtils.isEmpty(r.getRdIds())) {
//                errors.rejectValue("retirementApprovalForm.sopIds", "message.required", "필수 입력 항목입니다.");
                errors.rejectValue("retirementApprovalForm.rdIds", "message.required", "SOP/RD 중 하나는 선택 되어야 합니다.");
            }

            if(StringUtils.isEmpty(r.getReason())) {
                errors.rejectValue("retirementApprovalForm.reason", "message.required", "필수 입력 항목입니다.");
            }
//            if(ObjectUtils.isEmpty(r.getRetirementDate())) {
//                errors.rejectValue("retirementApprovalForm.retirementDate", "message.required", "필수 입력 항목입니다.");
//            }
        }
    }

    public static void main(String[] args) {
//        String s = "SOP-AD0001_RD11";

//        System.out.println(s.substring(0, s.indexOf("_")));

        Pattern p1 = Pattern.compile("^[_0-9a-zA-Z-]+@[0-9a-zA-Z-]+(.[_0-9a-zA-Z-]+)*$");
        Pattern p2 = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");

        String email = "ab.c-bb_b@ddd.com";

        Matcher m = p1.matcher(email);
        System.out.println(m.matches());

        Matcher m2 = p2.matcher(email);
        System.out.println(m2.matches());

    }
}
