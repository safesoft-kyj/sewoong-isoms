package com.cauh.iso.xdocreport;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.Signature;
import com.cauh.common.repository.SignatureRepository;
import com.cauh.common.utils.Base64Utils;
import com.cauh.iso.component.DocumentAssembly;
import com.cauh.iso.component.DocumentViewer;
import com.cauh.iso.domain.ISO;
import com.cauh.iso.domain.ISOTrainingCertificationInfo;
import com.cauh.iso.domain.QISOTrainingCertificationInfo;
import com.cauh.iso.repository.ISOTrainingCertificationInfoRepository;
import com.cauh.iso.xdocreport.dto.ISOCertificationDTO;
import com.cauh.iso.domain.ISOTrainingCertification;
import com.cauh.iso.repository.ISOTrainingCertificationRepository;
import com.cauh.iso.utils.DateUtils;
import com.groupdocs.assembly.DataSourceInfo;
import com.groupdocs.conversion.internal.c.a.ms.System.IO.Directory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ISOTrainingCertificationService {

    private final ISOTrainingCertificationRepository isoTrainingCertificationRepository;
    private final ISOTrainingCertificationInfoRepository isoTrainingCertificationInfoRepository;
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

    public ISOTrainingCertificationInfo getCurrentCertificateInfo() {
        BooleanBuilder builder = new BooleanBuilder();
        QISOTrainingCertificationInfo qisoTrainingCertificationInfo = QISOTrainingCertificationInfo.iSOTrainingCertificationInfo;
        builder.and(qisoTrainingCertificationInfo.active.eq(true));

        Optional<ISOTrainingCertificationInfo> infoOptional = isoTrainingCertificationInfoRepository.findOne(builder);

        return infoOptional.isPresent()?infoOptional.get():null;
    }

    public ISOTrainingCertification findByIsoAndUser(ISO iso, Account user){
        Optional<ISOTrainingCertification> optional = isoTrainingCertificationRepository.findByIsoAndUser(iso, user);
        if(optional.isPresent()) {
            return optional.get();
        } else {
            log.error("?????? ISO??? ?????? ???????????? ?????? : {}", iso.getId());

            return null;
        }
    }

    //?????? Certification ?????? ?????? ??? ??????
    public String getCertNo(ISO iso){
        Integer seqNo = isoTrainingCertificationRepository.countByCreatedDate(new Date()) + 1; //1??? ??????
        String seqTxt = String.format("%03d", seqNo); //3?????? fixed Text
        String result = DateUtils.format(new Date(), "yyyy") + "-" + seqTxt;

        log.info("@Certification Number ?????? : {}", result);

        return result;
    }

    public ISOTrainingCertification saveOrUpdate(ISOTrainingCertification isoTrainingCertification) {
        return isoTrainingCertificationRepository.save(isoTrainingCertification);
    }

    //????????? ?????? ??????
    public void createCertificationFile(ISOTrainingCertification isoTrainingCertification) throws Exception{
//        String fileName = isoTrainingCertification.getUser().getUsername() + "_iso_cert_" + isoTrainingCertification.getId() + ".pdf";
        String fileName = isoTrainingCertification.getUser().getUsername() + "_" + isoTrainingCertification.getCertNo() + ".pdf";
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

        //????????? ????????? ????????? ?????????,
        if(!ObjectUtils.isEmpty(isoTrainingCertification.getIsoTrainingCertificationInfo())) {
            Optional<Signature> signatureOptional = signatureRepository.findById(isoTrainingCertification.getIsoTrainingCertificationInfo().getManager().getUsername());
            //????????? ????????? ??????????????? ?????????,
            if(signatureOptional.isPresent()) {
                Signature signature = signatureOptional.get();
                isoCertificationDTO.setSign(new ByteArrayInputStream(Base64Utils.decodeBase64ToBytes(signature.getBase64signature())));
            }
        }

        DataSourceInfo dataSourceInfo = new DataSourceInfo(isoCertificationDTO, "");
        documentAssembly.assembleDocumentAsPdf(in, os, dataSourceInfo);

//        DocumentAssembler assembler = new DocumentAssembler();
//        assembler.getKnownTypes().add(ISOCertificationDTO.class);
//        assembler.assembleDocument(in, os, new LoadSaveOptions(FileFormat.PDF), dataSourceInfo);

        //?????? ?????? ??? ????????? ??????
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

        //isoTrainingCertification ??????.
        ISOTrainingCertification savedCertification = isoTrainingCertificationRepository.save(isoTrainingCertification);
        log.info("????????? Certification ?????? : {}", savedCertification);
    }
}
