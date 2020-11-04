package com.dtnsm.esop.component;

import com.groupdocs.conversion.config.ConversionConfig;
import com.groupdocs.conversion.handler.ConversionHandler;
import com.groupdocs.conversion.handler.ConvertedDocument;
import com.groupdocs.conversion.options.load.LoadOptions;
import com.groupdocs.conversion.options.load.SpreadsheetLoadOptions;
import com.groupdocs.conversion.options.save.ImageSaveOptions;
import com.groupdocs.conversion.options.save.MarkupSaveOptions;
import com.groupdocs.conversion.options.save.PdfSaveOptions;
import com.groupdocs.conversion.options.save.SaveOptions;
import com.groupdocs.conversion.utils.wrapper.stream.GroupDocsOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;

@Component
@Slf4j
public class DocumentViewer {
//    @Value("${groupdocs.license}")
//    private String license;

    private ConversionConfig config = new ConversionConfig();
    private ConversionHandler conversionHandler;

    @PostConstruct
    public void license() {
        try {
//            com.groupdocs.conversion.License lic = new com.groupdocs.conversion.License();
//            lic.setLicense(new FileInputStream(new File(license)));
//            log.info("@License Loaded.");

//            config.setStoragePath("D:\\");
//            config.setOutputPath("D:\\");

            conversionHandler = new ConversionHandler(config);
        } catch (Exception error) {
            log.error("Error : {}", error.toString());
        }
    }

    public String toHTML(InputStream is) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        SaveOptions saveOption = new MarkupSaveOptions();
        ConvertedDocument convertedDocumentPath = conversionHandler.convert(is, saveOption);
        log.debug("Converted file path is: {}", convertedDocumentPath.getFileType());
        GroupDocsOutputStream outputStream = new GroupDocsOutputStream(os);
        convertedDocumentPath.save(outputStream);

        return os.toString("utf-8");
    }

    protected boolean isExcel(String ext) {
        if("xls".equalsIgnoreCase(ext) || "xlsx".equalsIgnoreCase(ext)) {
            return true;
        } else {
            return false;
        }
    }

    public void toHTML(String ext, InputStream is, OutputStream os) {

        ConvertedDocument convertedDocumentPath;
        SaveOptions markupSaveOption = new MarkupSaveOptions();
        GroupDocsOutputStream outputStream = new GroupDocsOutputStream(os);
        if(isExcel(ext)) {
            SpreadsheetLoadOptions loadOptions = new SpreadsheetLoadOptions();
            loadOptions.setSkipEmptyRowsAndColumns(false);
            loadOptions.setOnePagePerSheet(false);
            loadOptions.setHideComments(true);
            loadOptions.setShowHiddenSheets(false);
            loadOptions.setOptimizePdfSize(false);
            loadOptions.setShowGridLines(false);

            log.info("==> {} to html", ext);
            SaveOptions saveOption = new PdfSaveOptions();
            convertedDocumentPath = conversionHandler.convert(is, loadOptions, saveOption);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GroupDocsOutputStream bout = new GroupDocsOutputStream(out);
            convertedDocumentPath.save(bout);

            ConvertedDocument convertedDocument = conversionHandler.convert(new ByteArrayInputStream(out.toByteArray()), markupSaveOption);
            convertedDocument.save(outputStream);
        } else {

            convertedDocumentPath = conversionHandler.convert(is, markupSaveOption);


            convertedDocumentPath.save(outputStream);
        }
        log.debug("Converted file path is: {}", convertedDocumentPath.getFileType());

    }

    public void pdf2img(InputStream is, OutputStream os) {
        try {
            ImageSaveOptions saveOption = new ImageSaveOptions();
            ConvertedDocument convertedDocumentPath = conversionHandler.convert(is, saveOption);
            log.debug("Converted file path is: {}", convertedDocumentPath.getFileType());
            GroupDocsOutputStream outputStream = new GroupDocsOutputStream(os);
            convertedDocumentPath.save(outputStream);
        } catch (Exception error) {
            log.error("pdf2img error : {}", error);
        }
    }

    public void toPDF(InputStream is, OutputStream os) {
        try {
            SaveOptions saveOption = new PdfSaveOptions();
            ConvertedDocument convertedDocumentPath = conversionHandler.convert(is, saveOption);
            log.debug("Converted file path is: {}", convertedDocumentPath.getFileType());
            GroupDocsOutputStream outputStream = new GroupDocsOutputStream(os);
            convertedDocumentPath.save(outputStream);
        } catch (Exception error) {
            log.error("error : {}", error);
        } finally {
            if(os != null) {
                try {
                    os.flush();
                    os.close();
                } catch(Exception e) {}
            }
            if(is != null) {
                try {
                    is.close();
                } catch (Exception e){}
            }
        }
    }
}
