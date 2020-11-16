package com.cauh.esop.xdocreport;

import com.cauh.esop.component.DocumentAssembly;
import com.cauh.esop.domain.ApprovalLine;
import com.cauh.esop.domain.constant.ApprovalLineType;
import com.cauh.esop.domain.report.SOPDeviationReport;
import com.cauh.esop.service.UserJobDescriptionService;
import com.cauh.esop.utils.DateUtils;
import com.cauh.esop.xdocreport.dto.ApprovalLineDTO;
import com.cauh.esop.xdocreport.dto.SOPDeviationReportDTO;
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
public class SOPDeviationReportService {
    private final DocumentAssembly documentAssembly;
    private final UserJobDescriptionService userJobDescriptionService;
//    @Value("${file.upload-dir}")
//    private String fileUploadDir;
//    private InputStream in;
//    private IXDocReport report;


    public void generateReport(SOPDeviationReport form, List<ApprovalLine> approvalLines, OutputStream os) {
        try {
            InputStream in = SOPDeviationReportService.class.getResourceAsStream("SOP_Deviation_Report_01.docx");
            // 1) Load Docx file by filling Velocity template engine and cache
            // it to the registry
//            log.debug("@User : {} Employee Training Log 생성 Template 가져오는중..", user.getEmpNo());

//            log.debug("@User : {} Employee Training Log 생성 Template Load 성공..", user.getEmpNo());
            // 2) Create fields metadata to manage lazy loop (#foreach velocity) for table row.


            // 3) Create context Java model
            List<ApprovalLineDTO> approvalLineDTOList = approvalLines.stream()
                    .map(a -> new ApprovalLineDTO(a.getLineType(), a.getLineType().getLabel(), a.getUser().getEngName(), userJobDescriptionService.getUserShortJobD(a.getUser().getUsername()),
                            a.getBase64signature(),
                            DateUtils.format(ObjectUtils.isEmpty(a.getLastModifiedDate()) ? a.getCreatedDate() : a.getLastModifiedDate(), "dd-MMM-yyyy").toUpperCase(),
                            StringUtils.isEmpty(a.getComments()) ? "" : a.getComments())
                    )
                    .collect(Collectors.toList());

            SOPDeviationReportDTO dto = new SOPDeviationReportDTO();
            dto.setForm(form);
            dto.setReviewer(approvalLineDTOList.stream().filter(a -> a.getLineType() == ApprovalLineType.reviewer).count() > 0);
            dto.setReportedBy(approvalLineDTOList.stream().filter(a -> a.getLineType() == ApprovalLineType.requester).findFirst().get());
            dto.setReviewers(approvalLineDTOList.stream().filter(a -> a.getLineType() == ApprovalLineType.reviewer).collect(Collectors.toList()));
            dto.setConfirmedBy(approvalLineDTOList.stream().filter(a -> a.getLineType() == ApprovalLineType.approver).findFirst().get());
            DataSourceInfo dataSourceInfo = new DataSourceInfo(dto, "");
            documentAssembly.assembleDocumentAsPdf(in, os, dataSourceInfo);
//            log.debug("@User : {} 서명 이미지 설정...", user.getEmpNo());
//            IImageProvider sign = new FileImageProvider(new File(fileUploadDir + File.separator + user.getSignImg()));
//            context.put("sign", sign);
            // 4) Generate report by merging Java model with the Docx
//            OutputStream out = new FileOutputStream(new File("SOP_RD_Index_Out.docx"));
//            report.process(context, out);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
