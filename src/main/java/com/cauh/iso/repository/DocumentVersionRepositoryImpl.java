package com.cauh.iso.repository;

import com.cauh.iso.domain.*;
import com.cauh.iso.domain.constant.DocumentStatus;
import com.cauh.iso.domain.constant.DocumentType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class DocumentVersionRepositoryImpl implements DocumentVersionRepositoryCustomer {
    private final JPAQueryFactory queryFactory;

    /**
     *
     * @param status
     * @return
     */
    @Override
    public List<DocumentVersion> getSOPFoldersByStatus(DocumentStatus status, String categoryId) {
        QDocumentVersion qDocumentVersion = QDocumentVersion.documentVersion;
//        QDocument qDocument = QDocument.document;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qDocumentVersion.document.type.eq(DocumentType.RF));
        builder.and(qDocumentVersion.status.eq(status));


        List<Document> sopDocuments =  queryFactory
                .select(qDocumentVersion.document.sop)
                .from(qDocumentVersion)
                .where(builder)
        .fetch();



        if(!ObjectUtils.isEmpty(sopDocuments)) {
            List<String> sopIdList = sopDocuments.stream()
                    .map(Document::getId)
                    .collect(Collectors.toList());
            log.info("<== status : {}, rd : {}", status, sopIdList);

            BooleanBuilder bb = new BooleanBuilder();
            bb.and(qDocumentVersion.document.id.in(sopIdList));
            if(status == DocumentStatus.EFFECTIVE) {
                bb.and(qDocumentVersion.status.eq(status));
            } else {
                bb.and(qDocumentVersion.status.eq(status).or(qDocumentVersion.parentVersion.id.isNull()));
            }

            if(!StringUtils.isEmpty(categoryId)) {
                bb.and(qDocumentVersion.document.category.id.eq(categoryId));
            }
            return queryFactory.selectFrom(qDocumentVersion)
                    .where(bb)
                    .fetch();
        }


        return Collections.emptyList();
    }
}
