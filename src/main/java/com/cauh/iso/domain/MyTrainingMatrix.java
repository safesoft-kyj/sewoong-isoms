package com.cauh.iso.domain;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.io.Serializable;

@Data
public class MyTrainingMatrix implements Serializable {
    private static final long serialVersionUID = 593702660903555618L;

    private Document document;

    private DocumentVersion documentVersion;

    @QueryProjection
    public MyTrainingMatrix(Document document, DocumentVersion documentVersion) {
        this.document = document;
        this.documentVersion = documentVersion;
    }
}
