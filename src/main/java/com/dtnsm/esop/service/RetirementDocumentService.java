package com.dtnsm.esop.service;

import com.dtnsm.esop.domain.DocumentVersion;
import com.dtnsm.esop.domain.constant.DocumentStatus;
import com.dtnsm.esop.domain.constant.DocumentType;
import com.dtnsm.esop.domain.report.QRetirementDocument;
import com.dtnsm.esop.domain.report.RetirementDocument;
import com.dtnsm.esop.repository.DocumentVersionRepository;
import com.dtnsm.esop.repository.RetirementDocumentRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class RetirementDocumentService {
    private final RetirementDocumentRepository retirementDocumentRepository;
    private final DocumentVersionRepository documentVersionRepository;

    public Iterable<RetirementDocument> findRetirementDocs(Date now, DocumentType documentType) {
        QRetirementDocument qRetirementDocument = QRetirementDocument.retirementDocument;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qRetirementDocument.retired.eq(false));
        builder.and(qRetirementDocument.retirementDate.eq(now));
        builder.and(qRetirementDocument.documentType.eq(documentType));

        return retirementDocumentRepository.findAll(builder, qRetirementDocument.documentVersion.document.docId.asc());
    }

    public void retired(Iterable<RetirementDocument> retirementDocuments) {
        for(RetirementDocument retirementDocument : retirementDocuments) {
            log.debug("@Retirement Document Id: {} 폐기 처리.", retirementDocument.getId());
            retirementDocument.setRetired(true);
            retirementDocumentRepository.save(retirementDocument);

            log.debug("@Retirement Document Doc Ver Id : {} Superseded 처리.", retirementDocument.getDocumentVersion().getId());
            retirementDocument.getDocumentVersion().setStatus(DocumentStatus.SUPERSEDED);
            documentVersionRepository.save(retirementDocument.getDocumentVersion());
        }
    }
}
