package com.cauh.iso.xdocreport;

import com.cauh.iso.component.DocumentAssembly;
import com.cauh.iso.domain.ApprovalLine;
import com.cauh.iso.domain.constant.ApprovalLineType;
import com.cauh.iso.domain.report.SopRdRequestForm;
import com.cauh.iso.service.UserJobDescriptionService;
import com.cauh.iso.utils.DateUtils;
import com.cauh.iso.xdocreport.dto.ApprovalLineDTO;
import com.cauh.iso.xdocreport.dto.SopRdRequestFormDTO;
import com.groupdocs.assembly.DataSourceInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SopRdRequestFormService {
    private final DocumentAssembly documentAssembly;
    private final UserJobDescriptionService userJobDescriptionService;

    public void generateReport(SopRdRequestForm form, List<ApprovalLine> approvalLines, OutputStream os) {
        try {
            InputStream in = SopRdRequestFormService.class.getResourceAsStream("SOP_RD_Request_Form_01.docx");

            List<ApprovalLineDTO> approvalLineDTOList = approvalLines.stream()
                    .map(a -> new ApprovalLineDTO(a.getLineType(), a.getLineType().getLabel(), a.getUser().getEngName(), userJobDescriptionService.getUserShortJobD(a.getUser().getUsername()),
                            a.getBase64signature(),
                            DateUtils.format(ObjectUtils.isEmpty(a.getLastModifiedDate()) ? a.getCreatedDate() : a.getLastModifiedDate(), "dd-MMM-yyyy").toUpperCase(),
                            StringUtils.isEmpty(a.getComments()) ? "" : a.getComments())
                    )
                    .collect(Collectors.toList());
//                    .
            SopRdRequestFormDTO dto = new SopRdRequestFormDTO();
            dto.setReviewer(approvalLines.stream().filter(a -> a.getLineType() == ApprovalLineType.reviewer).count() > 0);
            dto.setForm(form);
            dto.setReportedBy(approvalLineDTOList.stream().filter(a -> a.getLineType() == ApprovalLineType.requester).findFirst().get());
            dto.setReviewers(approvalLineDTOList.stream().filter(a -> a.getLineType() == ApprovalLineType.reviewer).collect(Collectors.toList()));
            dto.setConfirmedBy(approvalLineDTOList.stream().filter(a -> a.getLineType() == ApprovalLineType.approver).findFirst().get());

            DataSourceInfo dataSourceInfo = new DataSourceInfo(dto, "");
            documentAssembly.assembleDocumentAsPdf(in, os, dataSourceInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
