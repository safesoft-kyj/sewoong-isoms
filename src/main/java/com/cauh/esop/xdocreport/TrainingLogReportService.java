package com.cauh.esop.xdocreport;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.QTrainingRecord;
import com.cauh.common.entity.TrainingRecord;
import com.cauh.common.entity.constant.TrainingRecordStatus;
import com.cauh.common.repository.SignatureRepository;
import com.cauh.common.repository.TrainingRecordRepository;
import com.cauh.common.utils.Base64Utils;
import com.cauh.esop.component.DocumentAssembly;
import com.cauh.esop.component.DocumentViewer;
import com.cauh.esop.utils.DateUtils;
import com.cauh.esop.xdocreport.dto.TrainingLogDTO;
import com.cauh.esop.xdocreport.dto.TrainingLogReport;
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

    public void generateReport(List<TrainingLogReport> trainingLogs, Account user, Integer trainingRecordId) throws Exception {

            // 1) Load Docx file by filling Velocity template engine and cache
            // it to the registry
//            log.debug("@User : {} Employee Training Log 생성 Template 가져오는중..", user.getEmpNo());

//            log.debug("@User : {} Employee Training Log 생성 Template Load 성공..", user.getEmpNo());
            // 2) Create fields metadata to manage lazy loop (#foreach velocity) for table row.


            // 3) Create context Java model
//            IContext context = report.createContext();
//            context.put("user", user);
//            context.put("printDate", DateUtils.format(new Date(), "dd-MMM-yyyy").toUpperCase());
//            context.put("trainingLogs", trainingLogs);
//            context.put("date", new DateTool());
//            context.put("locale", Locale.ENGLISH);

            log.debug("@User : {} 서명 이미지 설정...", user.getComNum());
//            IImageProvider sign = new FileImageProvider(new File(fileUploadDir + File.separator + user.getSignImg()));
//            context.put("sign", sign);

//            context.put("sign", sign);
            // 4) Generate report by merging Java model with the Docx
//            OutputStream out = new FileOutputStream(new File("SOP_RD_Index_Out.docx"));
//            report.process(context, out);

//            Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.XWPF);
            String fileName = user.getUsername() + "_sop_"+ System.currentTimeMillis() + ".docx";

            log.debug("@User : {} Word 파일 생성...", user.getComNum());
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            InputStream in = TrainingLogReportService.class.getResourceAsStream("trainingLog_01.docx");
            TrainingLogDTO dto = new TrainingLogDTO();
            dto.setSign(new ByteArrayInputStream(Base64Utils.decodeBase64ToBytes(signatureRepository.findById(user.getUsername()).get().getBase64signature())));
            dto.setTrainingLogs(trainingLogs);
            dto.setUser(user);
            dto.setPrintDate(DateUtils.format(new Date(), "dd/MMM/yyyy").toUpperCase());
            DataSourceInfo dataSourceInfo = new DataSourceInfo(dto, "");
            documentAssembly.assembleDocument(in, os, dataSourceInfo);



//            ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());

            FileOutputStream output = new FileOutputStream(new File(fileUploadDir + File.separator + fileName));
            byte[] bt = os.toByteArray();
            output.write(bt);
            output.flush();
            output.close();


            if(ObjectUtils.isEmpty(trainingRecordId)) {
                QTrainingRecord qTrainingRecord = QTrainingRecord.trainingRecord;
                BooleanBuilder builder = new BooleanBuilder();
                builder.and(qTrainingRecord.status.eq(TrainingRecordStatus.PUBLISHED));
                builder.and(qTrainingRecord.username.eq(user.getUsername()));
                Optional<TrainingRecord> optionalTrainingRecord = trainingRecordRepository.findOne(builder);
                if (optionalTrainingRecord.isPresent()) {
                    log.info("@Userid : {} 배포 진행 중 신규 배포 이력이 존재함//배포 시점엔 published 상태가 없었으나, 중복 클릭으로 발생함.", user.getUsername());
                    trainingRecordId = optionalTrainingRecord.get().getId();
                    log.info("@Userid : {}, Training Record Id : {} 로 업데이트 되도록 변경", user.getUsername(), trainingRecordId);
                }
            }

            TrainingRecord trainingRecord;
            if(ObjectUtils.isEmpty(trainingRecordId)) {
                trainingRecord = new TrainingRecord();
                trainingRecord.setUsername(user.getUsername());
            } else {
                trainingRecord = trainingRecordRepository.findById(trainingRecordId).get();
            }
            trainingRecord.setStatus(TrainingRecordStatus.PUBLISHED);
            trainingRecord.setSopStatus(TrainingRecordStatus.PUBLISHED);
            trainingRecord.setSopPublishDate(new Date());
            trainingRecord.setSopFileName(fileName);

            String html = documentViewer.toHTML(new ByteArrayInputStream(bt));
            trainingRecord.setSopHtmlContent(html);

            trainingRecordRepository.save(trainingRecord);


            log.debug("@User : {} Employee Training Log 생성! -- Completed.", user.getComNum());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (XDocReportException e) {
//            e.printStackTrace();
    }
}
