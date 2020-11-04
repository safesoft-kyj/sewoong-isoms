package com.dtnsm.esop.xdocreport;


import com.dtnsm.common.utils.Base64Utils;
import com.dtnsm.esop.component.DocumentAssembly;
import com.dtnsm.esop.domain.NonDisclosureAgreement;
import com.dtnsm.esop.domain.report.SOPDisclosureRequestForm;
import com.dtnsm.esop.utils.DateUtils;
import com.dtnsm.esop.xdocreport.dto.NonDisclosureAgreementDTO;
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
public class NonDisclosureAgreementReportService {
    private final DocumentAssembly documentAssembly;
//    @Value("${file.upload-dir}")
//    private String fileUploadDir;
//    private InputStream in = TrainingLogReportService.class.getResourceAsStream("SOP_Non_Disclosure_Agreement.docx");
//    private IXDocReport report;



    public void generateReport(NonDisclosureAgreement agreement, HttpServletResponse response) {
        try(OutputStream os = response.getOutputStream()) {
            InputStream in = TrainingLogReportService.class.getResourceAsStream("SOP_Non_Disclosure_Agreement_01.docx");

            SOPDisclosureRequestForm sopDisclosureRequestForm = agreement.getExternalCustomer().getSopDisclosureRequestForm();
            StringBuilder jobTitleAndCompany = new StringBuilder();
            if(!StringUtils.isEmpty(agreement.getExternalCustomer().getJobTitle())) {
                jobTitleAndCompany.append(agreement.getExternalCustomer().getJobTitle());
            }

            if(jobTitleAndCompany.length() != 0 && StringUtils.isEmpty(sopDisclosureRequestForm.getCompanyNameOrInstituteName()) == false) {
                jobTitleAndCompany.append("/").append(sopDisclosureRequestForm.getCompanyNameOrInstituteName());
            }

            NonDisclosureAgreementDTO dto = new NonDisclosureAgreementDTO();
            dto.setCustomerName(agreement.getExternalCustomer().getName());
            dto.setJobTitleAndCompany(jobTitleAndCompany.toString());
            dto.setSign(new ByteArrayInputStream(Base64Utils.decodeBase64ToBytes(agreement.getBase64signature())));
            dto.setPurpose(sopDisclosureRequestForm.getPurposeOfDisclosure().name());
            dto.setPurposeOther(sopDisclosureRequestForm.getPurposeOfDisclosureOther());
            dto.setAgreementDate(DateUtils.format(agreement.getCreatedDate(), "dd-MMM-yyyy").toUpperCase());

            DataSourceInfo dataSourceInfo = new DataSourceInfo(dto, "");
            documentAssembly.assembleDocumentAsPdf(in, os, dataSourceInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

