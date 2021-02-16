package com.cauh.iso.xdocreport;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.QTrainingRecord;
import com.cauh.common.entity.TrainingRecord;
import com.cauh.common.entity.constant.TrainingRecordStatus;
import com.cauh.common.repository.SignatureRepository;
import com.cauh.common.repository.TrainingRecordRepository;
import com.cauh.common.utils.Base64Utils;
import com.cauh.iso.component.DocumentAssembly;
import com.cauh.iso.component.DocumentViewer;
import com.cauh.iso.utils.DateUtils;
import com.cauh.iso.xdocreport.dto.TrainingLogDTO;
import com.cauh.iso.xdocreport.dto.TrainingLogReport;
import com.groupdocs.assembly.DataSourceInfo;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainingLogReportService {
    private final SignatureRepository signatureRepository;
    private final TrainingRecordRepository trainingRecordRepository;
    private final DocumentViewer documentViewer;
    private final DocumentAssembly documentAssembly;
    @Value("${file.training-log-upload-dir}")
    private String fileUploadDir;

//    @PostConstruct
//    public void init() throws Exception {
//        log.info("@PostConstruct ::: TrainingLogReportService Template 로딩...");
//        report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
//        FieldsMetadata metadata = report.createFieldsMetadata();
//        // NEW API
//        log.debug("=> Employee Training Log 생성 Metadata Load ...");
//        metadata.load("trainingLogs", TrainingLogReport.class, true);
//        log.debug("=> Employee Training Log 생성 Metadata Completed ...");
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

    public Boolean sopGenerateReport(List<TrainingLogReport> trainingLogs, Account user, OutputStream os) {
        InputStream in = TrainingLogReportService.class.getResourceAsStream("Training_Log_01.docx");

        TrainingLogDTO dto = new TrainingLogDTO();
        dto.setSign(new ByteArrayInputStream(Base64Utils.decodeBase64ToBytes(signatureRepository.findById(user.getUsername()).get().getBase64signature())));
        dto.setTrainingLogs(trainingLogs);
        dto.setUser(user);
        dto.setBirthDate(ObjectUtils.isEmpty(user.getBirthDate())?"N/A":DateUtils.format(user.getBirthDate(), "dd-MMM-yyyy").toUpperCase());
        dto.setPrintDate(DateUtils.format(new Date(), "dd-MMM-yyyy").toUpperCase());
        DataSourceInfo dataSourceInfo = new DataSourceInfo(dto, "");

        return documentAssembly.assembleDocumentAsPdf(in, os, dataSourceInfo);
    }

    public Boolean isoGenerateReport(List<TrainingLogReport> trainingLogs, Account user, OutputStream os){
        InputStream in = TrainingLogReportService.class.getResourceAsStream("Training_Log_02.docx");

        TrainingLogDTO dto = new TrainingLogDTO();
        dto.setSign(new ByteArrayInputStream(Base64Utils.decodeBase64ToBytes(signatureRepository.findById(user.getUsername()).get().getBase64signature())));
        dto.setTrainingLogs(trainingLogs);
        dto.setUser(user);
        dto.setBirthDate(ObjectUtils.isEmpty(user.getBirthDate())?"N/A":DateUtils.format(user.getBirthDate(), "dd-MMM-yyyy").toUpperCase());
        dto.setPrintDate(DateUtils.format(new Date(), "dd/MMM/yyyy").toUpperCase());
        DataSourceInfo dataSourceInfo = new DataSourceInfo(dto, "");

        return documentAssembly.assembleDocumentAsPdf(in, os, dataSourceInfo);
    }
}
