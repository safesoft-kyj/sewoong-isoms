package com.dtnsm.esop.domain;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class DisclosureSOP {
//    private RequestedDocument requestedDocument;
    private DocumentVersion documentVersion;
    private Document document;
    private Document sopDocument;

    @QueryProjection
    public DisclosureSOP(DocumentVersion documentVersion, Document document, Document sopDocument) {
//        this.requestedDocument = requestedDocument;
        this.documentVersion = documentVersion;
        this.document = document;
        this.sopDocument = sopDocument;
    }
}

