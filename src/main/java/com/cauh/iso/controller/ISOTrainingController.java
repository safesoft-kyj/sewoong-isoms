package com.cauh.iso.controller;

import com.cauh.common.entity.Account;
import com.cauh.common.security.annotation.CurrentUser;
import com.cauh.iso.domain.*;
import com.cauh.iso.domain.constant.TrainingStatus;
import com.cauh.iso.repository.TrainingMatrixRepository;
import com.cauh.iso.service.ISOService;
import com.cauh.iso.xdocreport.ISOTrainingCertificationService;
import com.cauh.iso.service.ISOTrainingLogService;
import com.cauh.iso.service.ISOTrainingPeriodService;
import com.cauh.iso.utils.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequiredArgsConstructor
@Slf4j
@SessionAttributes({"quiz", "isoTrainingLogs"})
public class ISOTrainingController {

    private final TrainingMatrixRepository trainingMatrixRepository;
    private final ISOTrainingLogService isoTrainingLogService;
    private final ISOTrainingPeriodService isoTrainingPeriodService;
    private final ISOTrainingCertificationService isoTrainingCertificationService;
    private final ISOService isoService;

    @GetMapping("/training/iso/mytraining")
    public String myTraining(@PageableDefault(size = 25) Pageable pageable, @CurrentUser Account user, Model model) {

        Page<MyTraining> isoTrainingMatrices = trainingMatrixRepository.getISOMyTraining(pageable, user);
        model.addAttribute("trainingMatrix", isoTrainingMatrices);

        return "iso/training/trainingList";
    }

    @PostMapping("/training/iso/mytraining")
    public String saveTrainingLog(@RequestParam("isoId") String isoId,
                                  @RequestParam("isoTrainingPeriodId") Integer isoTrainingPeriodId,
                                  @RequestParam(value = "isoTrainingLogId", required = false) Integer isoTrainingLogId,
                                  @RequestParam("trainingTime") Integer trainingTime,
                                  @RequestParam("progressPercent") double progressPercent,
                                  @RequestParam("lastPageNo") Integer lastPageNo,
                                  @CurrentUser Account user, RedirectAttributes attributes) throws Exception {
        ISOTrainingLog isoTrainingLog;
        Optional<ISO> isoOptional = isoService.getISO(isoId);
        if(isoOptional.isEmpty()) {
            attributes.addFlashAttribute("type", "danger");
            attributes.addFlashAttribute("message", "ISO Training 교육 처리 과정 중 문제가 발생하였습니다.");
            return "redirect:/training/iso/mytraining";
        }

        ISO iso = isoOptional.get();

        if(ObjectUtils.isEmpty(isoTrainingLogId)) {
            ISOTrainingPeriod isoTrainingPeriod = isoTrainingPeriodService.findById(isoTrainingPeriodId).get();
            isoTrainingLog = new ISOTrainingLog();
            isoTrainingLog.setIso(iso);
            isoTrainingLog.setUser(user);
            isoTrainingLog.setIsoTrainingPeriod(isoTrainingPeriod);
            isoTrainingLog.setType(isoTrainingPeriod.getTrainingType());
        } else {
            isoTrainingLog = isoTrainingLogService.findById(isoTrainingLogId).get();
        }

        isoTrainingLog.setLastPageNo(lastPageNo);
        isoTrainingLog.setProgressPercent(progressPercent);
        isoTrainingLog.setTrainingTime(trainingTime);
        isoTrainingLog.setStatus(progressPercent >= 100 ? (!StringUtils.isEmpty(iso.getQuiz()) ? TrainingStatus.TRAINING_COMPLETED : TrainingStatus.COMPLETED) : TrainingStatus.PROGRESS);

        ISOTrainingLog savedTrainingLog = isoTrainingLogService.saveOrUpdate(isoTrainingLog, null);
        log.debug("=> SavedTrainingLog Id : {}", savedTrainingLog.getId());

        //수료증이 있으면서 교육이 완료되었을 때 -> 수료증 정보 생성
        if(isoTrainingLog.getStatus() == TrainingStatus.COMPLETED && iso.isCertification()) {
            ISOTrainingCertification certification = ISOTrainingCertification.builder()
                    .id(isoTrainingCertificationService.getCertId(iso))
                    .iso(iso).user(user).build();
            isoTrainingCertificationService.createCertificationFile(certification);
        }else if(isoTrainingLog.getStatus() == TrainingStatus.TRAINING_COMPLETED) {
            attributes.addFlashAttribute("message", "Training 완료! Test를 진행 해 주세요.");
            attributes.addFlashAttribute("trainingLogId", savedTrainingLog.getId());
        } else {
            attributes.addFlashAttribute("message", "진행 중인 교육 정보가 업데이트 되었습니다.");
        }

        return "redirect:/training/iso/mytraining";
    }

    @GetMapping("/training/iso/mytraining/completed")
    public String completedTraining() {
//        @PageableDefault(size = 15, sort = {"completeDate"}, direction = Sort.Direction.DESC) Pageable pageable,        @PathVariable("documentType") DocumentType documentType, @CurrentUser Account user, Model model

//        QTrainingLog qTrainingLog = QTrainingLog.trainingLog;
//        BooleanBuilder builder = new BooleanBuilder();
//        builder.and(qTrainingLog.user.id.eq(user.getId()));
//        builder.and(qTrainingLog.status.eq(TrainingStatus.COMPLETED));
//        builder.and(qTrainingLog.documentVersion.document.type.eq(documentType));
//
//        model.addAttribute("trainingLog", trainingLogService.findAll(builder, pageable));
        return "iso/training/completedTraining";
    }

    @GetMapping("/ajax/training/iso/mytraining/completed")
    @ResponseBody
    public List<ISOTrainingLogDTO> ajaxCompletedTraining(@CurrentUser Account user) {
        QISOTrainingLog qIsoTrainingLog = QISOTrainingLog.iSOTrainingLog;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qIsoTrainingLog.user.id.eq(user.getId()));
        builder.and(qIsoTrainingLog.status.eq(TrainingStatus.COMPLETED));

        Iterable<ISOTrainingLog> isoTrainingLogs = isoTrainingLogService.findAll(builder, qIsoTrainingLog.completeDate.desc());
        List<ISOTrainingLog> trainingLogList = StreamSupport.stream(isoTrainingLogs.spliterator(), false)
                .collect(Collectors.toList());

        int size = trainingLogList.size();
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.set(size);
        List<ISOTrainingLogDTO> isoTrainingLogDTOS = new ArrayList<>();
        isoTrainingLogs.forEach(log -> {
            ISOTrainingLogDTO dto = new ISOTrainingLogDTO();
            dto.setIndex(atomicInteger.getAndDecrement());
            dto.setId(log.getId());
            dto.setCompletionDate(DateUtils.format(log.getCompleteDate(), "dd-MMM-yyyy").toUpperCase());
            dto.setCourse(log.getTrainingCourse());
            dto.setHour(log.getHour());
            dto.setOrganization(log.getOrganization());

            //ISO에서 수료증을 사용할 경우,
            if(log.getIso().isCertification()) {
                ISOTrainingCertification certification = isoTrainingCertificationService.findByIsoAndUser(log.getIso(), user);
                dto.setCertId(certification.getId());
                dto.setCertHtml(certification.getCertHtml());
            }

            isoTrainingLogDTOS.add(dto);
        });

        return isoTrainingLogDTOS;
    }

    /**
     * Training Test 출력
     * @param isoId
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/ajax/training/iso/mytraining/test")
    public String test(@RequestParam("isoId") String isoId, Model model) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Quiz quiz = objectMapper.readValue(isoService.getISO(isoId).get().getQuiz(), Quiz.class);
        Collections.shuffle(quiz.getQuizQuestions());
        for(QuizQuestion quizQuestion : quiz.getQuizQuestions()) {
            quizQuestion.getAnswers().forEach(a -> a.setCorrect(false));//선택된 답 초기화
            Collections.shuffle(quizQuestion.getAnswers());
        }

        model.addAttribute("quiz", quiz);

        return "iso/training/test";
    }

    @PostMapping("/training/iso/mytraining/test")
    @Transactional
    public String test(@RequestParam("isoTrainingLogId") Integer isoTrainingLogId,
                       @CurrentUser Account user, @ModelAttribute("quiz") Quiz quiz,
                       SessionStatus status, RedirectAttributes attributes) throws Exception{

        double correctCount = 0;
        double questionCount = quiz.getQuizQuestions().size();
        log.info("@User : {} Quiz 정답 체크 시작", user.getUsername());
        for(QuizQuestion q : quiz.getQuizQuestions()) {
            List<Integer> correct = Arrays.asList(q.getCorrect());
            List<Integer> choices = q.getAnswers().stream().filter(a -> a.isCorrect()).map(a -> a.getIndex()).collect(Collectors.toList());
            q.setChoices(choices.toArray(new Integer[choices.size()]));
            boolean match = (correct.size() == choices.size()) && correct.containsAll(choices);
            log.debug("Q.{} 정답:{}, 선택:{}, 일치 여부 : {}", q.getIndex(), correct, choices, match);
            if(match) {
                correctCount ++;
            }
        }
        log.debug("정답 수 : {}", correctCount);
        ISOTrainingLog trainingLog = isoTrainingLogService.findById(isoTrainingLogId).get();

        double score = 0;
        if(correctCount > 0) {
            score = ((correctCount / questionCount) * 100);
            log.debug("점수 : {}", score);
//            log.debug("점수 : {}, 정답수:{} / 전체 문항:{} * 100 = {}", score, correctCount, questionCount, (correctCount / questionCount) * 100);
        }

        log.debug("=> 정답 수 : {}, 점수 : {}", correctCount, score);
        trainingLog.setScore((int)score);
        if(correctCount >= 4) {
            trainingLog.setStatus(TrainingStatus.COMPLETED);
            if(trainingLog.getIso().isCertification()) {
                ISOTrainingCertification certification = ISOTrainingCertification.builder()
                        .id(isoTrainingCertificationService.getCertId(trainingLog.getIso()))
                        .iso(trainingLog.getIso()).user(user).build();

                log.info("@Certification 생성 : {}", certification.getId());
                isoTrainingCertificationService.createCertificationFile(certification);
            }

            trainingLog.setCompleteDate(new Date());
            attributes.addFlashAttribute("message", trainingLog.getScore() + "점("+(int)questionCount+"문제중 정답 "+(int)correctCount+"개)으로 교육 완료 되었습니다.");
        } else {
            trainingLog.setStatus(TrainingStatus.TEST_FAILED);
            attributes.addFlashAttribute("message", "점수는 [" + trainingLog.getScore() + "]입니다.("+(int)questionCount+"문제중 정답 "+(int)correctCount+"개) 80점이상 이수 가능 합니다.");
        }

        isoTrainingLogService.saveOrUpdate(trainingLog, quiz);
        status.setComplete();

        return "redirect:/training/iso/mytraining";
    }

}
