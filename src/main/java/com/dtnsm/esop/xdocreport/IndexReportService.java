package com.dtnsm.esop.xdocreport;

import com.dtnsm.esop.component.DocumentAssembly;
import com.dtnsm.esop.domain.Category;
import com.dtnsm.esop.domain.constant.DocumentType;
import com.dtnsm.esop.xdocreport.dto.IndexDTO;
import com.dtnsm.esop.xdocreport.dto.IndexReport;
import com.groupdocs.assembly.DataSourceInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class IndexReportService {
    private final DocumentAssembly documentAssembly;
//    @Value("${file.upload-dir}")
//    private String uploadDir;

    public void generateReport(List<Category> categories, List<IndexReport> docs, DocumentType type, OutputStream os) {
        try {
            // 1) Load Docx file by filling Velocity template engine and cache
            // it to the registry
            InputStream is = IndexReportService.class.getResourceAsStream(type.name() + "_Index_01.docx");
//            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
            IndexDTO dto = new IndexDTO();
            dto.setIssueDate(DateUtils.formatDate(new Date(), "dd/MMM/yyyy").toUpperCase());
            dto.setCategories(categories);
            dto.setDocs(docs);
            DataSourceInfo dataSourceInfo = new DataSourceInfo(dto, "");
            documentAssembly.assembleDocumentAsPdf(is, os, dataSourceInfo);
            // 2) Create fields metadata to manage lazy loop (#foreach velocity) for table row.
//            FieldsMetadata metadata = report.createFieldsMetadata();
            // Old API
            /*
            metadata.addFieldAsList("developers.Name");
            metadata.addFieldAsList("developers.LastName");
            metadata.addFieldAsList("developers.Mail");
            */
            // NEW API
//            metadata.load("categories", Category.class, true);
//            metadata.load("docs", IndexReport.class, true);

            // 3) Create context Java model
//            IContext context = report.createContext();
//            context.put("issueDate", new Date());
//            context.put("categories", categories);
//            context.put("docs", docs);
//            context.put("date", new DateTool());
//            context.put("locale", Locale.ENGLISH);

            // 4) Generate report by merging Java model with the Docx
//            OutputStream out = new FileOutputStream(new File("SOP_RD_Index_Out.docx"));
//            report.process(context, out);

//            Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.XWPF);
//            OutputStream outpdf = new FileOutputStream(new File(uploadDir + File.separator + "SOP_RD_Index_Out.pdf"));
//            report.convert(context, options, os);
        } catch (Exception e) {
            e.printStackTrace();
//        } catch (XDocReportException e) {
//            e.printStackTrace();
        }
    }
}
