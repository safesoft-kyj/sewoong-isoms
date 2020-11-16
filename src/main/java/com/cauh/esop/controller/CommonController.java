package com.cauh.esop.controller;

import com.cauh.esop.component.DocumentViewer;
import com.cauh.esop.domain.DocumentVersion;
import com.cauh.esop.domain.TrainingMatrixFile;
import com.cauh.esop.domain.constant.DocumentStatus;
import com.cauh.esop.domain.constant.DocumentType;
import com.cauh.esop.service.CategoryService;
import com.cauh.esop.service.DocumentVersionService;
import com.cauh.esop.service.FileStorageService;
import com.cauh.esop.service.TrainingMatrixService;
import com.cauh.esop.utils.DateUtils;
import com.cauh.esop.xdocreport.IndexReportService;
import com.cauh.esop.xdocreport.dto.IndexReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.apache.http.client.utils.DateUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommonController {
    private final TrainingMatrixService trainingMatrixService;
    private final FileStorageService fileStorageService;
    private final CategoryService categoryService;
    private final DocumentVersionService documentVersionService;
    private final IndexReportService indexReportService;
    private final DocumentViewer documentViewer;

    @GetMapping("/")
    public String index() {
        return "redirect:/notice";
    }


    @GetMapping("/notifications")
    public String notifications() {
        return "home/notifications/list";
    }

    @GetMapping("/access-denied")
    public String denied() {
        return "home/denied";
    }

    @GetMapping("/common/download/{type}/index")
//    @ResponseBody
    public void indexReport(@PathVariable("type") DocumentType type, HttpServletResponse response) throws Exception {
        response.setContentType("application/octet-stream");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+type.name()+"_index.pdf\"");

        Iterable<DocumentVersion> iterable = documentVersionService.findAll(documentVersionService.getPredicate(type, DocumentStatus.EFFECTIVE, null, null, null));
        List<IndexReport> docs;
        if(type == DocumentType.SOP) {
            docs = StreamSupport.stream(iterable.spliterator(), false)
                .map(d -> IndexReport.builder()
                        .category(d.getDocument().getCategory().getShortName())
                        .sopNo(d.getDocument().getDocumentNo())
                        .title(d.getDocument().getTitle())
                        .docId(d.getDocument().getDocId())
                        .version(d.getVersion())
                        .effectiveDate(DateUtils.format(d.getEffectiveDate(), "dd/MMM/yyyy").toUpperCase())
                .build())
                .collect(Collectors.toList());
        } else {
            docs = StreamSupport.stream(iterable.spliterator(), false)
                    .map(d -> IndexReport.builder()
                            .title(d.getDocument().getTitle())
                            .docId(d.getDocument().getDocId())
                            .version(d.getVersion())
                            .effectiveDate(DateUtils.format(d.getEffectiveDate(), "dd/MMM/yyyy").toUpperCase())
                            .build())
                    .collect(Collectors.toList());

//            log.warn("o : {}, c : {}", d.getEffectiveDate(), DateUtils.formatDate(d.getEffectiveDate(), "dd/MMM/yyyy").toUpperCase());
        }
        indexReportService.generateReport(categoryService.getCategoryList(), docs, type, response.getOutputStream());


//        return "redirect:/";
    }

//    @GetMapping("/common/trainingMatrix/viewer")
//    public String trainingMatrix(Model model) {
//        Optional<TrainingMatrixFile> optionalTrainingMatrixFile = trainingMatrixService.findFirstByOrderByIdDesc();
//        if(optionalTrainingMatrixFile.isPresent()) {
//            model.addAttribute("fileName", optionalTrainingMatrixFile.get().getFileName());
//        }
//        return "home/notice/matrixViewer";
//    }

    @GetMapping(value = "/viewer/trainingMatrix")
    public void trainingMatrixViewer(HttpServletResponse response) throws Exception{
        // Load file as Resource
        response.setContentType("text/html;charset=utf-8");
        OutputStream os = response.getOutputStream();
        Optional<TrainingMatrixFile> optionalTrainingMatrixFile = trainingMatrixService.findFirstByOrderByIdDesc();
        if(optionalTrainingMatrixFile.isPresent()) {
            TrainingMatrixFile attachFile = optionalTrainingMatrixFile.get();
            Resource resource = fileStorageService.loadFileAsResource(attachFile.getFileName());

            // Try to determine file's content type
            //            String contentType = "text/html;charset=utf-8";
            //            try {
            //                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            //            } catch (IOException ex) {
            //                log.info("Could not determine file type.");
            //            }

            // Fallback to the default content type if type could not be determined
            //            if (contentType == null) {
            //                contentType = "application/octet-stream";
            //            }
            ByteArrayOutputStream html = new ByteArrayOutputStream();
            documentViewer.toHTML("xlsx", resource.getInputStream(), html);

            os.write(html.toByteArray());
            os.flush();
            os.close();
        } else {
            os.write("파일이 존재하지 않습니다.".getBytes());
            os.flush();
            os.close();
        }
    }
    @GetMapping("/common/download/trainingMatrix/{trainingMatrixId}")
    public ResponseEntity<Resource> downloadTrainingMatrix(HttpServletRequest request, @PathVariable("trainingMatrixId") Integer id) {
        // Load file as Resource
        Optional<TrainingMatrixFile> optionalTrainingMatrixFile = trainingMatrixService.findById(id);
        if(optionalTrainingMatrixFile.isPresent()) {
            TrainingMatrixFile attachFile = optionalTrainingMatrixFile.get();
            Resource resource = fileStorageService.loadFileAsResource(attachFile.getFileName());

            // Try to determine file's content type
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                log.info("Could not determine file type.");
            }

            // Fallback to the default content type if type could not be determined
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachFile.getOriginalFileName() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.of(Optional.empty());
        }
    }

    @GetMapping("/ajax/keep-session")
    @ResponseBody
    public String keepSession() {
        return "ok";
    }
}
