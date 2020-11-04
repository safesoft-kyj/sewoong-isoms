package com.dtnsm.esop.component;

import com.groupdocs.assembly.DataSourceInfo;
import com.groupdocs.assembly.DocumentAssembler;
import com.groupdocs.assembly.FileFormat;
import com.groupdocs.assembly.LoadSaveOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;

@Component
@Slf4j
public class DocumentAssembly {
    public boolean assembleDocument(InputStream is, OutputStream os, DataSourceInfo... dataSourceInfo) {
        try {
            DocumentAssembler assembler = new DocumentAssembler();
            return assembler.assembleDocument(is, os, dataSourceInfo);
        } catch (Exception error) {
            log.error("Error : {}", error);
            return false;
        }
    }
    public boolean assembleDocumentAsPdf(InputStream is, OutputStream os, DataSourceInfo... dataSourceInfo) {
        try {
            DocumentAssembler assembler = new DocumentAssembler();
            return assembler.assembleDocument(is, os, new LoadSaveOptions(FileFormat.PDF), dataSourceInfo);
        } catch (Exception error) {
            log.error("Error : {}", error);
            return false;
        }
    }
}
