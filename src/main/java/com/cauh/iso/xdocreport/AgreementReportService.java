package com.cauh.iso.xdocreport;


import com.cauh.common.utils.Base64Utils;
import com.cauh.iso.component.DocumentAssembly;
import com.cauh.iso.domain.AgreementPersonalInformation;
import com.cauh.iso.utils.DateUtils;
import com.cauh.iso.xdocreport.dto.AgreementDTO;
import com.groupdocs.assembly.DataSourceInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class AgreementReportService {
//    @Value("${file.upload-dir}")
//    private String fileUploadDir;
//    private IXDocReport report;
    private final DocumentAssembly documentAssembly;


//    @PostConstruct
//    public void init() throws Exception {
//        report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
//        FieldsMetadata metadata = report.createFieldsMetadata();
//         NEW API
//        metadata.addFieldAsImage("sign");//서명
//    }

//    @PreDestroy
//    public void destroy() {
//        try {
//            if(in != null) {
//                in.close();
//                in = null;
//            }
//            report = null;
//        } catch (IOException e) {
//        }
//    }

    public void generateReport(AgreementPersonalInformation agreement, HttpServletResponse response) {
        try {
            // 1) Load Docx file by filling Velocity template engine and cache
            // it to the registry
//            log.debug("@User : {} Employee Training Log 생성 Template 가져오는중..", user.getEmpNo());

//            log.debug("@User : {} Employee Training Log 생성 Template Load 성공..", user.getEmpNo());
            // 2) Create fields metadata to manage lazy loop (#foreach velocity) for table row.


            // 3) Create context Java model
            InputStream in = TrainingLogReportService.class.getResourceAsStream("SOP_Agreement_01.docx");

            StringBuilder jobTitleAndCompany = new StringBuilder();
            if (!StringUtils.isEmpty(agreement.getExternalCustomer().getRole())) {
                jobTitleAndCompany.append(agreement.getExternalCustomer().getRole());
            }
            if (jobTitleAndCompany.length() != 0 && StringUtils.isEmpty(agreement.getExternalCustomer().getSopDisclosureRequestForm().getCompanyNameOrInstituteName()) == false) {
                jobTitleAndCompany.append("/").append(agreement.getExternalCustomer().getSopDisclosureRequestForm().getCompanyNameOrInstituteName());
            }

            AgreementDTO dto = new AgreementDTO();
            dto.setCustomerName(agreement.getExternalCustomer().getName());
            dto.setJobTitleAndCompany(jobTitleAndCompany.toString());
            dto.setSign(new ByteArrayInputStream(Base64Utils.decodeBase64ToBytes(agreement.getBase64signature())));
            dto.setAgreementDate(DateUtils.format(agreement.getCreatedDate(), "dd-MMM-yyyy").toUpperCase());

            // 4) Generate report by merging Java model with the Docx
//            OutputStream out = new FileOutputStream(new File("SOP_RD_Index_Out.docx"));
//            report.process(context, out);

//            OutputStream outpdf = new FileOutputStream(new File(uploadDir + File.separator + "SOP_RD_Index_Out.pdf"));
            OutputStream os = response.getOutputStream();
            DataSourceInfo dataSourceInfo = new DataSourceInfo(dto, "");
            documentAssembly.assembleDocumentAsPdf(in, os, dataSourceInfo);
        } catch (Exception e) {
            log.error("Error : {}", e.getMessage());
        }
    }
}

