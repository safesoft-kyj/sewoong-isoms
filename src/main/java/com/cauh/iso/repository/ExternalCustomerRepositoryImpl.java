package com.cauh.iso.repository;

import com.cauh.iso.domain.DisclosureSOP;
import com.cauh.iso.domain.QDocument;
import com.cauh.iso.domain.QDocumentVersion;
import com.cauh.iso.domain.constant.DocumentStatus;
import com.cauh.iso.domain.report.QRequestedDocument;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class ExternalCustomerRepositoryImpl implements ExternalCustomerRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<DisclosureSOP> getDocumentList(Integer requestFormId, DocumentStatus status, String categoryId, String sopId) {
        QRequestedDocument requestedDocument = QRequestedDocument.requestedDocument;
        QDocumentVersion qDocumentVersion = QDocumentVersion.documentVersion;
        QDocument qDocument = QDocument.document;
        QDocument sopDoc = QDocument.document;
        log.info("@RequestFormId : {}, status : {}", requestFormId, status);
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(requestedDocument.sopDisclosureRequestForm.id.eq(requestFormId));
        builder.and(requestedDocument.documentVersion.status.eq(status));
//        if(!StringUtils.isEmpty(categoryId)) {
//            builder.and(requestedDocument.documentVersion.document.category.id.eq(categoryId).or(sopDoc.category.id.eq(categoryId)));
//        }
        if(!StringUtils.isEmpty(sopId)) {
            builder.and(requestedDocument.documentVersion.document.id.eq(sopId).or(sopDoc.id.eq(sopId)));
        }

        return queryFactory.selectDistinct(Projections.constructor(DisclosureSOP.class,
                requestedDocument.documentVersion,
                requestedDocument.documentVersion.document,
                sopDoc
        ))
        .from(requestedDocument)
                .innerJoin(requestedDocument.documentVersion, qDocumentVersion)
                .innerJoin(qDocumentVersion.document, qDocument)
                .leftJoin(qDocument.sop, sopDoc)
        .where(builder)
//        .and(requestedDocument.documentType.eq(DocumentType.RD)))
                .orderBy(sopDoc.docId.asc(), requestedDocument.documentVersion.document.docId.asc())
        .fetch();
    }
}
