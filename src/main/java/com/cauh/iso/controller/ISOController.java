package com.cauh.iso.controller;

import com.cauh.common.entity.Account;
import com.cauh.common.security.annotation.CurrentUser;
import com.cauh.common.service.UserService;
import com.cauh.iso.domain.*;
import com.cauh.iso.domain.constant.*;
import com.cauh.iso.security.annotation.IsAdmin;
import com.cauh.iso.service.*;
import com.cauh.iso.validator.ISOValidator;
import com.cauh.iso.validator.QuizValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@SessionAttributes({"iso", "quiz"})
@RequiredArgsConstructor
public class ISOController {

    private final ISOService isoService;
    private final ISOAccessLogService isoAccessLogService;
    private final ISOValidator isoValidator;
    private final FileStorageService fileStorageService;
    private final UserService userService;
    private final QuizValidator quizValidator;


    @GetMapping("/iso-14155")
    public String ISOlist(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 15) Pageable pageable, @CurrentUser Account user, Model model) {
        QISO qISO = QISO.iSO;

        //공지사항 리스트
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qISO.deleted.eq(false));
        model.addAttribute("isoList", isoService.getPage(builder, pageable));

        //공지사항(상단공지)
        Date today = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        builder.and(qISO.topViewEndDate.goe(Date.valueOf(format.format(today))));
        model.addAttribute("topISOList", isoService.getTopISOs(builder));

//        Optional<ISOTrainingMatrixFile> optionalTrainingMatrixFile = isotrainingMatrixService.findFirstByOrderByIdDesc();
//        model.addAttribute("isoTrainingMatrixFile", optionalTrainingMatrixFile.isPresent() ? optionalTrainingMatrixFile.get() : null);

        return "iso/iso14155/list";
    }

    @IsAdmin
    @GetMapping("/iso-14155/new")
    public String newISO(Model model) {
        ISO iso = new ISO();
        iso.setTrainingAll(true);
        model.addAttribute("userMap", userService.getUserMap());
        model.addAttribute("iso", iso);

        return "iso/iso14155/edit";
    }

    @IsAdmin
    @GetMapping("/iso-14155/{isoId}/edit")
    public String isoEdit(@PathVariable("isoId") String isoId, Model model, RedirectAttributes attributes) {
        Optional<ISO> isoOptional = isoService.getISO(isoId);
        if (isoOptional.isPresent()) {

            ISO iso = isoOptional.get();
            if (iso.isTraining()) {
                ISOTrainingPeriod isoTrainingPeriod = iso.getIsoTrainingPeriods().size() > 0?iso.getIsoTrainingPeriods().get(0):null;
                iso.setStartDate(isoTrainingPeriod.getStartDate());
                iso.setEndDate(isoTrainingPeriod.getEndDate());

                //TrainingAll이 있으면 trainingAll로 세팅 아니면 유저별 참석
                if(iso.getIsoTrainingMatrix().stream().filter(d -> d.isTrainingAll()).count() > 0) {
                    iso.setTrainingAll(true);
                } else {
                    List<String> ids = iso.getIsoTrainingMatrix().stream().map(im -> Integer.toString(im.getUser().getId())).collect(Collectors.toList());
                    iso.setUserIds(ids.toArray(new String[ids.size()]));
                }
            }

            model.addAttribute("iso", iso);
            model.addAttribute("userMap", userService.getUserMap());
        } else {
            attributes.addFlashAttribute("message", "존재하지 않는 게시물 입니다.");
            return "redirect:/iso-14155";
        }
        return "iso/iso14155/edit";
    }

    @IsAdmin
    @Transactional
    @PostMapping({"/iso-14155/new", "/iso-14155/{isoId}/edit"})
    public String saveISO(@PathVariable(value = "isoId", required = false) String isoId,
                          @ModelAttribute("iso") ISO iso,
                          @RequestParam(value = "uploadingFile") MultipartFile uploadingFile,
                          BindingResult bindingResult, SessionStatus sessionStatus,
                          RedirectAttributes attributes, Model model,
                          HttpServletRequest request) {

        //업로드 진행할 파일의 이름을 넣음.
        iso.setUploadFileName(uploadingFile.getOriginalFilename());
        isoValidator.validate(iso, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("userMap", userService.getUserMap());
            return "iso/iso14155/edit";
        }

        iso.setPostStatus(PostStatus.NONE);
        iso.setIsoType(ISOType.ISO_14155);
        ISO savedISO = isoService.saveISO(iso, uploadingFile);

        sessionStatus.setComplete();

        if (ObjectUtils.isEmpty(isoId)) {
            attributes.addFlashAttribute("message", "ISO 14155가 저장 되었습니다.");
            return "redirect:/iso-14155/" + savedISO.getId() + (StringUtils.isEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString());
        } else {
            attributes.addFlashAttribute("message", "ISO 14155가 수정 되었습니다.");
            return "redirect:/iso-14155/{isoId}" + (StringUtils.isEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString());
        }

    }

    @GetMapping("/iso-14155/{isoId}")
    public String isoView(@PageableDefault(sort = {"createdDate"}, direction = Sort.Direction.DESC, size = 15) Pageable pageable, @PathVariable("isoId") String isoId, Model model, RedirectAttributes attributes) {
        Optional<ISO> isoOptional = isoService.getISO(isoId);
        if (isoOptional.isPresent()) {
            ISO iso = isoOptional.get();
            if (iso.isTraining()) {
                ISOTrainingPeriod isoTrainingPeriod = iso.getIsoTrainingPeriods().size() > 0?iso.getIsoTrainingPeriods().get(0):null;
                iso.setStartDate(isoTrainingPeriod.getStartDate());
                iso.setEndDate(isoTrainingPeriod.getEndDate());

                //TrainingAll이 있으면 trainingAll로 세팅 아니면 유저별 참석
                if(iso.getIsoTrainingMatrix().stream().filter(d -> d.isTrainingAll()).count() > 0) {
                    iso.setTrainingAll(true);
                } else {
                    List<Account> userList = iso.getIsoTrainingMatrix().stream().map(tm -> tm.getUser()).collect(Collectors.toList());
                    Page<Account> userPageList = new PageImpl<>(userList, pageable, userList.size());

                    iso.setTrainingAll(false);
                    model.addAttribute("userPageList", userPageList);
                }
            }
            model.addAttribute("viewIso", iso);
        } else {
            attributes.addFlashAttribute("message", "존재하지 않는 ISO 게시물 입니다.");
            return "redirect:/iso-14155";
        }
        return "iso/iso14155/view";
    }

    @IsAdmin
    @PutMapping("/iso-14155/active")
    @Transactional
    public String isoActive(@RequestParam("isoId") String isoId, RedirectAttributes attributes) {

        Optional<ISO> isoOptional = isoService.getISO(isoId);
        if(isoOptional.isPresent()) {
            ISO iso = isoOptional.get();
            String res = isoService.isoActivate(iso);

            if(!res.equals("success")) {
                attributes.addFlashAttribute("type", "danger");
                attributes.addFlashAttribute("message", res);
                return "redirect:/iso-14155";
            }

            attributes.addFlashAttribute("message", "[" + iso.getTitle() + "] Training이 Active되었습니다.");
        }

        return "redirect:/iso-14155";
    }


    /**
     * 메일전송 대상자에게 메일 전송
     * @param isoId
     * @return
     */
    @IsAdmin
    @GetMapping("/ajax/iso-14155/{isoId}/send")
    @ResponseBody
    public Map<String, String> sendEmail(@PathVariable("isoId") String isoId) {
        Map<String, String> model = new HashMap<>();
        isoService.sendMail(isoId);
        model.put("result", "success");
        model.put("id", isoId);
        return model;
    }

    /**
     * ISO 삭제.
     * @param isoId
     * @param attributes
     * @param request
     * @return
     */
    @IsAdmin
    @DeleteMapping("/iso-14155/{isoId}")
    public String isoRemove(@PathVariable("isoId") String isoId, RedirectAttributes attributes, HttpServletRequest request) {
        Optional<ISO> iso = isoService.getISO(isoId);
        if(iso.isPresent() && iso.get().isActive()){
            attributes.addFlashAttribute("type", "danger");
            attributes.addFlashAttribute("message", "삭제 실패 :: 교육 진행중인 ISO입니다.");
            return "redirect:/iso-14155";
        }else if (iso.isPresent()) {
            isoService.remove(iso.get());
            attributes.addFlashAttribute("message", "ISO-14155 게시물이 삭제 되었습니다.");
            return "redirect:/iso-14155?" + (StringUtils.isEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString());
        } else {
            attributes.addFlashAttribute("type", "danger");
            attributes.addFlashAttribute("message", "존재하지 않는 ISO-14155 게시물 입니다.");
            return "redirect:/iso-14155";
        }
    }

    /**
     * 퀴즈 입력 화면
     * @param isoId
     * @param model
     * @param attributes
     * @return
     */
    @IsAdmin
    @GetMapping("/iso-14155/{isoId}/quiz")
    public String quizEdit(@PathVariable("isoId") String isoId, Model model, RedirectAttributes attributes){
        Optional<ISO> isoOptional = isoService.getISO(isoId);

        if(isoOptional.isEmpty()) {
            attributes.addFlashAttribute("type", "danger");
            attributes.addFlashAttribute("message", "존재하지 않는 ISO입니다.");
            return "redirect:/iso-14155";
        }

        ISO iso = isoOptional.get();
        model.addAttribute("iso", iso);

        Quiz quiz = null;
        if(!StringUtils.isEmpty(iso.getQuiz())) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                quiz = objectMapper.readValue(iso.getQuiz(), Quiz.class);
            } catch (Exception error) {
                log.error("error : ", error);
            }
        } else {
            quiz = new Quiz();
            List<QuizQuestion> questions = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                List<QuizAnswer> quizAnswers = new ArrayList<>();
                for (int x = 0; x < 5; x++) {
                    quizAnswers.add(new QuizAnswer(x + 1));
                }

                QuizQuestion quizQuestion = new QuizQuestion(i + 1);
                quizQuestion.setAnswers(quizAnswers);
                questions.add(quizQuestion);
            }
            quiz.setQuizQuestions(questions);
        }
        model.addAttribute("quiz", quiz);

        return "iso/iso14155/quiz";
    }

    /**
     * 퀴즈 등록
     * @param isoId
     * @param quiz
     * @param result
     * @param status
     * @param request
     * @param attributes
     * @return
     * @throws Exception
     */
    @IsAdmin
    @PostMapping("/iso-14155/{isoId}/quiz")
    public String saveQuiz(@PathVariable("isoId") String isoId,
                           @ModelAttribute("quiz") Quiz quiz,
                           BindingResult result, SessionStatus status,
                           HttpServletRequest request,
                           RedirectAttributes attributes) throws Exception {
        boolean isRemove = WebUtils.hasSubmitParameter(request, "remove");

        if(isRemove) {
            String removeValue = ServletRequestUtils.getStringParameter(request, "remove");
            String[] arr = removeValue.split("\\.");
            quiz.getQuizQuestions().get(Integer.parseInt(arr[0])).getAnswers().remove(Integer.parseInt(arr[1]));
            return "iso/iso14155/quiz";
        }

        quizValidator.validate(quiz, result);

        if(result.hasErrors()) {
            log.debug("Quiz Error : {}", result.getAllErrors());
            return "iso/iso14155/quiz";
        }

        ISO savedIso = isoService.saveQuiz(isoId, quiz);
        status.setComplete();

        if(ObjectUtils.isEmpty(savedIso)) {
            attributes.addFlashAttribute("type", "danger");
            attributes.addFlashAttribute("message", "존재하지 않는 ISO입니다.");
            return "redirect:/iso-14155";
        }

        attributes.addFlashAttribute("message", "[" + savedIso.getTitle() + "] 에 퀴즈 정보가 수정 되었습니다.");
        return "redirect:/iso-14155";
    }

    /**
     * 퀴즈 업로드
     * @param isoId
     * @param model
     * @param attributes
     * @return
     */
    @IsAdmin
    @GetMapping("/iso-14155/{isoId}/quiz/upload")
    public String quizUpload(@PathVariable("isoId") String isoId, Model model, RedirectAttributes attributes) {

//        model.addAttribute("docVerId", docVerId);
        Optional<ISO> isoOptional = isoService.getISO(isoId);

        if(isoOptional.isEmpty()) {
            attributes.addFlashAttribute("type", "danger");
            attributes.addFlashAttribute("message", "존재하지 않는 ISO입니다.");
            return "redirect:/iso-14155";
        }

        model.addAttribute("iso", isoOptional.get());
        model.addAttribute("title", isoOptional.get().getTitle());

        return "iso/iso14155/quiz-upload";
    }


    /**
     * 업로드 된 Template 파일을 토대로 Quiz 풀기.
     * @param isoId
     * @param multipartFile
     * @param model
     * @param attributes
     * @return
     */
    @PutMapping("/iso-14155/{isoId}/quiz")
    public String quizUploaded(@PathVariable("isoId") String isoId,
                               @RequestParam("quizTemplate") MultipartFile multipartFile,
                               Model model, RedirectAttributes attributes) {

        try {
            Quiz quiz = new Quiz();
            List<QuizQuestion> questions = new ArrayList<>();
            InputStream is = multipartFile.getInputStream();
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheetAt(0);

            XSSFRow isoTypeRow = sheet.getRow(2);
            isoTypeRow.getCell(1).setCellType(CellType.STRING);
            String isoType = isoTypeRow.getCell(1).getStringCellValue();

            XSSFRow titleRow = sheet.getRow(3);
            titleRow.getCell(1).setCellType(CellType.STRING);
            String title = titleRow.getCell(1).getStringCellValue();

            log.debug("ISO Type : {}, title : {}", isoType, title);

            //임시로 삭제.
//            DocumentVersion documentVersion = documentVersionService.findById(docVerId);
//            if(!documentVersion.getDocument().getDocId().equals(documentId) || !documentVersion.getVersion().equals(version)) {
//                attributes.addFlashAttribute("message", "Template에 DocumentId/Version 정보와 SOP 정보와 일치하지 않습니다.");
//                return "redirect:/admin/SOP/management/{stringStatus}";
//            }

            XSSFRow qRow = sheet.getRow(7);
            XSSFRow a1Row = sheet.getRow(8);
            XSSFRow a2Row = sheet.getRow(9);
            XSSFRow a3Row = sheet.getRow(10);
            XSSFRow a4Row = sheet.getRow(11);
            XSSFRow a5Row = sheet.getRow(12);
            XSSFRow correctRow = sheet.getRow(13);

            for(int i = 1; i <= 5; i ++) {
                qRow.getCell(i).setCellType(CellType.STRING);
                a1Row.getCell(i).setCellType(CellType.STRING);
                a2Row.getCell(i).setCellType(CellType.STRING);
                a3Row.getCell(i).setCellType(CellType.STRING);
                a4Row.getCell(i).setCellType(CellType.STRING);
                a5Row.getCell(i).setCellType(CellType.STRING);

                String q = qRow.getCell(i).getStringCellValue();
                String a1 = a1Row.getCell(i).getStringCellValue();
                String a2 = a2Row.getCell(i).getStringCellValue();
                String a3 = a3Row.getCell(i).getStringCellValue();
                String a4 = a4Row.getCell(i).getStringCellValue();
                String a5 = a5Row.getCell(i).getStringCellValue();
                correctRow.getCell(i).setCellType(CellType.STRING);
                String correct = correctRow.getCell(i).getStringCellValue();
                log.debug("Q : {}, a1 : {}, a2 : {}, a3 : {}, a4 : {}, a5 : {}, ## correct : {}", q, a1, a2, a3, a4, a5, correct);

                QuizQuestion quizQuestion = new QuizQuestion(i + 1);
                List<QuizAnswer> quizAnswers = new ArrayList<>();
                quizQuestion.setText(q);
                quizAnswers.add(new QuizAnswer(1, a1, eq("1", correct)));
                quizAnswers.add(new QuizAnswer(2, a2, eq("2", correct)));
                if(!StringUtils.isEmpty(a3)) {
                    quizAnswers.add(new QuizAnswer(3, a3, eq("3", correct)));
                }
                if(!StringUtils.isEmpty(a4)) {
                    quizAnswers.add(new QuizAnswer(4, a4, eq("4", correct)));
                }
                if(!StringUtils.isEmpty(a5)) {
                    quizAnswers.add(new QuizAnswer(5, a5, eq("5", correct)));
                }
                quizQuestion.setAnswers(quizAnswers);
                questions.add(quizQuestion);
            }

            quiz.setQuizQuestions(questions);
            model.addAttribute("quiz", quiz);
            return "iso/iso14155/quiz";
        } catch (Exception error) {
            log.error("error : {}", error.getMessage());
            attributes.addFlashAttribute("type", "danger");
            attributes.addFlashAttribute("message", "Quiz Upload 동작 중 에러가 발생하였습니다.");
            return "redirect:/iso-14155/";
        }
    }

    /**
     * 퀴즈를 테스트 하는 화면 진입.
     * @param isoId
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/iso-14155/{isoId}/quiz/test")
    public String quizTest(@PathVariable("isoId") String isoId, Model model) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Quiz quiz = objectMapper.readValue(isoService.getISO(isoId).get().getQuiz(), Quiz.class);
        Collections.shuffle(quiz.getQuizQuestions());
        for(QuizQuestion quizQuestion : quiz.getQuizQuestions()) {
            Collections.shuffle(quizQuestion.getAnswers());
        }
        model.addAttribute("quiz", quiz);
        return "iso/iso14155/quiz-test";
    }

    @GetMapping("/iso-14155/viewer/{isoId}")
    public void viewer(@PathVariable("isoId") String isoId, @RequestParam("page") int page, @CurrentUser Account user,
                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileName = isoId + "-" + page + ".jpg";
        log.info("FileName : {}", fileName);
        
        //ISO 접속 기록 저장
        isoAccessLogService.save(isoId, DocumentAccessType.TRAINING);
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = "image/jpeg";

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName+ "\"");
        response.setContentType(contentType);
        imageDraw(resource.getInputStream(), response.getOutputStream());
    }



    /**
     * 파일 다운로드
     * @param id
     * @param attachFileId
     * @param request
     * @return
     */
    @GetMapping("/iso-14155/{id}/downloadFile/{attachFileId:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") String id, @PathVariable("attachFileId") String attachFileId,
                                                 HttpServletRequest request) {
        // Load file as Resource
        Optional<ISOAttachFile> optionalAttachFile = isoService.getAttachFile(attachFileId);
        if (optionalAttachFile.isPresent()) {
            ISOAttachFile attachFile = optionalAttachFile.get();
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

    /**
     *
     * @param answerIndex
     * @param correct ex) 1,2 or 4
     * @return
     */
    private boolean eq(String answerIndex, String correct) {
        if(correct.trim().length() == 1) {
            return correct.trim().equals(answerIndex);
        }
        List<String> correctList = new ArrayList<>();
        String[] correctArr = correct.split(",");
        for(String str : correctArr) {
            correctList.add(str.trim());
        }
        return correctList.contains(answerIndex);
    }


    /**
     * 이미지 그리기
     * @param inputStream
     * @param os
     */
    public static void imageDraw(InputStream inputStream, OutputStream os) {
        try {
            BufferedImage original = ImageIO.read(inputStream);
            ImageIO.write(original, "jpg", os);
        } catch (Exception error) {
            log.error("Error : ", error);
        } finally {
            log.debug("ISO Viewer 이미지 생성 완료!");
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException ioe) {}
        }
    }
}
