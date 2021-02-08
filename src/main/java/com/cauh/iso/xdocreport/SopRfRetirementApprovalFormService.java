package com.cauh.iso.xdocreport;

import com.cauh.iso.component.DocumentAssembly;
import com.cauh.iso.domain.ApprovalLine;
import com.cauh.iso.domain.DocumentVersion;
import com.cauh.iso.domain.constant.ApprovalLineType;
import com.cauh.iso.domain.report.RetirementApprovalForm;
import com.cauh.iso.admin.service.UserJobDescriptionService;
import com.cauh.iso.utils.DateUtils;
import com.cauh.iso.xdocreport.dto.ApprovalLineDTO;
import com.cauh.iso.xdocreport.dto.RetirementDocumentDTO;
import com.cauh.iso.xdocreport.dto.SopRdRetirementFormDTO;
import com.groupdocs.assembly.DataSourceInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SopRfRetirementApprovalFormService {
    private final DocumentAssembly documentAssembly;
    private final UserJobDescriptionService userJobDescriptionService;

    public void generateReport(RetirementApprovalForm form, List<ApprovalLine> approvalLines, OutputStream os) {
        try {
            InputStream in = SOPWaiverRequestApprovalFormService.class.getResourceAsStream("Retirement_Approval_Form_01.docx");
            List<ApprovalLineDTO> approvalLineDTOList = approvalLines.stream()
                    .map(a -> new ApprovalLineDTO(a.getLineType(), a.getLineType().getLabel(), a.getUser().getEngName(), userJobDescriptionService.getUserShortJobD(a.getUser().getUsername()),
                            a.getBase64signature(),
                            DateUtils.format(ObjectUtils.isEmpty(a.getLastModifiedDate()) ? a.getCreatedDate() : a.getLastModifiedDate(), "dd-MMM-yyyy").toUpperCase(),
                            StringUtils.isEmpty(a.getComments()) ? "" : a.getComments())
                    )
                    .collect(Collectors.toList());

            List<DocumentVersion> retirementDocuments = new ArrayList<>();
            if(!ObjectUtils.isEmpty(form.getRetirementDocumentSOPs())) {
                retirementDocuments.addAll(form.getRetirementDocumentSOPs().stream().map(s -> s.getDocumentVersion()).collect(Collectors.toList()));
            }
            if(!ObjectUtils.isEmpty(form.getRetirementDocumentRFs())) {
                retirementDocuments.addAll(form.getRetirementDocumentRFs().stream().map(s -> s.getDocumentVersion()).collect(Collectors.toList()));
            }

            SopRdRetirementFormDTO dto = new SopRdRetirementFormDTO();
            dto.setDocs(retirementDocuments.stream()
                    .map(v -> RetirementDocumentDTO.builder().type(v.getDocument().getType().name()).docId(v.getDocument().getDocId()).title(v.getDocument().getTitle())
                    .ver(v.getVersion()).effectiveDate(v.getStrEffectiveDate()).reason(form.getReason()).build()).collect(Collectors.toList()));


            long reviewerCount = approvalLines.stream().filter(a -> a.getLineType() == ApprovalLineType.reviewer).count();
            dto.setReviewerTeamManager(reviewerCount == 2);
            dto.setReviewerQA(reviewerCount >= 1);
            dto.setForm(form);
            dto.setReportedBy(approvalLineDTOList.stream().filter(a -> a.getLineType() == ApprovalLineType.requester).findFirst().get());
            List<ApprovalLineDTO> reviewerLists = approvalLineDTOList.stream().filter(a -> a.getLineType() == ApprovalLineType.reviewer).collect(Collectors.toList());
            if(dto.isReviewerTeamManager()) {
                dto.setReviewedByTeamManager(reviewerLists.get(0));
            }
            if(dto.isReviewerQA()) {
                dto.setReviewedByQA(reviewerLists.get(reviewerCount == 2 ? 1 : 0));
            }
            dto.setConfirmedBy(approvalLineDTOList.stream().filter(a -> a.getLineType() == ApprovalLineType.approver).findFirst().get());

            DataSourceInfo dataSourceInfo = new DataSourceInfo(dto, "");
            documentAssembly.assembleDocumentAsPdf(in, os, dataSourceInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
