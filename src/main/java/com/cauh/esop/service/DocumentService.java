package com.cauh.esop.service;

import com.cauh.esop.domain.*;
import com.cauh.esop.domain.constant.DocumentStatus;
import com.cauh.esop.domain.constant.DocumentType;
import com.cauh.esop.repository.DocumentRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
@Slf4j
public class DocumentService {
    private final DocumentRepository documentRepository;

//    private final DocumentVersionRepository documentVersionRepository;

//    private final FileStorageService fileStorageService;

//    private final CategoryService categoryService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public Iterable<Document> findAll(BooleanBuilder builder) {
        return documentRepository.findAll(builder, Sort.by(Sort.Direction.ASC, "docId"));
    }


    public TreeMap<String, String> getSortedMap(DocumentStatus status) {
        Map<String, String> sopMap = StreamSupport.stream(findAll(getPredicate(status, null, null)).spliterator(), false)
                .collect(Collectors.toMap(d -> d.getDocId(), d -> "[" + d.getDocId() + "] " + d.getTitle()));
        TreeMap treeMap = new TreeMap();
        treeMap.putAll(sopMap);

        return treeMap;
    }

    public Document findById(String id) {
        return documentRepository.findById(id).get();
    }

    public Optional<Document> findByDocId(String docId) {
        QDocument document = QDocument.document;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(document.docId.eq(docId));
        return documentRepository.findOne(builder);
    }

    public Optional<Document> findOne(BooleanBuilder builder) {
        return documentRepository.findOne(builder);
    }

    public long countByCategoryId(String categoryId) {
        QDocument document = QDocument.document;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(document.category.id.eq(categoryId));
        return documentRepository.count(builder);
    }

    public BooleanBuilder getPredicate(DocumentStatus status, String categoryId, String id) {
        QDocument qDocument = QDocument.document;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qDocument.type.eq(DocumentType.SOP));
//        builder.and(qDocument.status.eq(status));
        if(StringUtils.isEmpty(categoryId) == false) {
            builder.and(qDocument.category.eq(Category.builder().id(categoryId).build()));
        }

        if(StringUtils.isEmpty(id) == false) {
            builder.and(qDocument.id.eq(id));
        }

        return builder;
    }

    @Transactional(readOnly = true)
    public Optional<DocumentVersion> findLatestDocument(String docId) {
        BooleanBuilder builder = new BooleanBuilder();
        QDocument qDocument = QDocument.document;
        builder.and(qDocument.docId.eq(docId));

        Optional<Document> optionalDocument = documentRepository.findOne(builder);
        if(optionalDocument.isPresent()) {
            Document document = optionalDocument.get();
            return document.getDocumentVersionList().stream().filter(v -> v.getStatus() == DocumentStatus.EFFECTIVE)
                    .findFirst();
        }

        return Optional.empty();
    }
}
