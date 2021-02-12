package com.cauh.iso.xdocreport;

import com.cauh.common.entity.Account;
import com.cauh.common.repository.SignatureRepository;
import com.cauh.common.utils.Base64Utils;
import com.cauh.iso.component.DocumentAssembly;
import com.cauh.iso.component.DocumentViewer;
import com.cauh.iso.domain.ISO;
import com.cauh.iso.domain.TrainingLog;
import com.cauh.iso.xdocreport.dto.ISOCertificationDTO;
import com.cauh.iso.domain.ISOTrainingCertification;
import com.cauh.iso.repository.ISOTrainingCertificationRepository;
import com.cauh.iso.utils.DateUtils;
import com.groupdocs.assembly.DataSourceInfo;
import com.groupdocs.assembly.DocumentAssembler;
import com.groupdocs.assembly.FileFormat;
import com.groupdocs.assembly.LoadSaveOptions;
import com.groupdocs.conversion.internal.c.a.ms.System.IO.Directory;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ISOTrainingCertificationService {

    private final ISOTrainingCertificationRepository isoTrainingCertificationRepository;
    private final SignatureRepository signatureRepository;
    private final DocumentAssembly documentAssembly;
    private final DocumentViewer documentViewer;

    @Value("${file.certification-upload-dir}")
    private String fileUploadDir;

    public Iterable<ISOTrainingCertification> findAll(Predicate predicate, OrderSpecifier<?> order) {
        return isoTrainingCertificationRepository.findAll(predicate, order);
    }

    public Page<ISOTrainingCertification> findAll(Predicate predicate, Pageable pageable) {
        return isoTrainingCertificationRepository.findAll(predicate, pageable);
    }

    public ISOTrainingCertification findById(Integer certId) {
        return isoTrainingCertificationRepository.findById(certId).get();
    }

    public ISOTrainingCertification findByIsoAndUser(ISO iso, Account user){
        Optional<ISOTrainingCertification> optional = isoTrainingCertificationRepository.findByIsoAndUser(iso, user);
        if(optional.isPresent()) {
            return optional.get();
        } else {
            log.error("해당 ISO에 대한 수료증이 없음 : {}", iso.getId());

            return null;
        }
    }

    //처음 Certification 번호 부여 시 사용
    public String getCertNo(ISO iso){
        Integer seqNo = isoTrainingCertificationRepository.countByIso(iso) + 1; //1로 시작
        String seqTxt = String.format("%03d", seqNo); //3자리 fixed Text
        String result = iso.getCertificationHead() + "-" + DateUtils.format(iso.getCreatedDate(), "yyyy") + "-" + seqTxt;

        log.info("@Certification Number 생성 : {}", result);

        return result;
    }

    public ISOTrainingCertification saveOrUpdate(ISOTrainingCertification isoTrainingCertification) {
        return isoTrainingCertificationRepository.save(isoTrainingCertification);
    }

    //수료증 파일 생성
    public void createCertificationFile(ISOTrainingCertification isoTrainingCertification) throws Exception{
//        String fileName = isoTrainingCertification.getUser().getUsername() + "_iso_cert_" + isoTrainingCertification.getId() + ".pdf";
        String fileName = isoTrainingCertification.getId() + ".pdf";
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        //Certification Template File Load
        InputStream in = ISOTrainingCertificationService.class.getResourceAsStream("ISO_14155_Training_Certificate_01.docx");
        log.info("Input Stream : {}", in);

        isoTrainingCertification.setPrintDate(DateUtils.format(new Date(), "dd/MMM/yyyy").toUpperCase());

        ISOCertificationDTO isoCertificationDTO = new ISOCertificationDTO();

        isoCertificationDTO.setAffiliationDepartment(isoTrainingCertification.getUser().getTeamDept());
        isoCertificationDTO.setDateOfBirth(DateUtils.format(isoTrainingCertification.getUser().getBirthDate(), "dd-MMM-yyyy").toUpperCase());
        isoCertificationDTO.setName(isoTrainingCertification.getUser().getName());
        isoCertificationDTO.setCertificateNo(isoTrainingCertification.getCertNo());
        isoCertificationDTO.setCompletionDate(DateUtils.format(isoTrainingCertification.getIsoTrainingLog().getCompleteDate(), "dd-MMM-yyyy").toUpperCase());
        isoCertificationDTO.setSign(new ByteArrayInputStream(Base64Utils.decodeBase64ToBytes(signatureRepository.findById(isoTrainingCertification.getUser().getUsername()).get().getBase64signature())));

        DataSourceInfo dataSourceInfo = new DataSourceInfo(isoCertificationDTO, "");
        documentAssembly.assembleDocumentAsPdf(in, os, dataSourceInfo);

//        DocumentAssembler assembler = new DocumentAssembler();
//        assembler.getKnownTypes().add(ISOCertificationDTO.class);
//        assembler.assembleDocument(in, os, new LoadSaveOptions(FileFormat.PDF), dataSourceInfo);

        //경로 확인 후 없으면 생성
        if(!Directory.exists(fileUploadDir)) {
            Directory.createDirectory(fileUploadDir);
        }

        FileOutputStream output = new FileOutputStream(new File(fileUploadDir + File.separator + fileName));
        byte[] bt = os.toByteArray();
        output.write(bt);
        output.flush();
        output.close();

        String html = documentViewer.toHTML(new ByteArrayInputStream((bt)));
        isoTrainingCertification.setCertHtml(html);
        isoTrainingCertification.setFileName(fileName);

        //isoTrainingCertification 저장.
        ISOTrainingCertification savedCertification = isoTrainingCertificationRepository.save(isoTrainingCertification);
        log.info("저장된 Certification 정보 : {}", savedCertification);
    }
}
