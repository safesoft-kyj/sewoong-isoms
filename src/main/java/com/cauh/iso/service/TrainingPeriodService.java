package com.cauh.iso.service;

import com.cauh.iso.admin.domain.constant.SOPAction;
import com.cauh.iso.domain.DocumentVersion;
import com.cauh.iso.domain.Mail;
import com.cauh.iso.domain.QTrainingPeriod;
import com.cauh.iso.domain.TrainingPeriod;
import com.cauh.iso.domain.constant.DocumentType;
import com.cauh.iso.domain.constant.TrainingType;
import com.cauh.iso.repository.TrainingPeriodRepository;
import com.cauh.iso.utils.DateUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingPeriodService {
    private final TrainingPeriodRepository trainingPeriodRepository;
    private final MailService mailService;

    public Optional<TrainingPeriod> findOne(BooleanBuilder builder) {
        return trainingPeriodRepository.findOne(builder);
    }

    public Optional<TrainingPeriod> findById(Integer id) {
        return trainingPeriodRepository.findById(id);
    }

    public void saveOrUpdateSelfTrainingPeriod(DocumentVersion documentVersion) {
        QTrainingPeriod qTrainingPeriod = QTrainingPeriod.trainingPeriod;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qTrainingPeriod.documentVersion.id.eq(documentVersion.getId()));
        builder.and(qTrainingPeriod.trainingType.eq(TrainingType.SELF));
        Optional<TrainingPeriod> optionalTrainingPeriod = trainingPeriodRepository.findOne(builder);

        TrainingPeriod trainingPeriod;
        if(optionalTrainingPeriod.isPresent()) {
            trainingPeriod = optionalTrainingPeriod.get();
//            trainingPeriod.setDocumentVersion(documentVersion);
        } else {
            trainingPeriod = TrainingPeriod.builder().documentVersion(documentVersion)
                    .trainingType(TrainingType.SELF).build();
        }
        trainingPeriod.setStartDate(DateUtils.addDay(documentVersion.getEffectiveDate(), -57));
        trainingPeriod.setEndDate(DateUtils.addDay(documentVersion.getEffectiveDate(), -1));
        trainingPeriodRepository.save(trainingPeriod);
        log.debug(" ==> DocumentVersionID : {}, Self Training 기간 업데이트.", documentVersion.getId());
    }

    public void saveOrUpdateRefreshTraining(TrainingPeriod trainingPeriod) {
        trainingPeriod.setTrainingType(TrainingType.REFRESH);
        trainingPeriodRepository.save(trainingPeriod);
    }

    public Page<TrainingPeriod> findAll(Predicate predicate, Pageable pageable) {
        return trainingPeriodRepository.findAll(predicate, pageable);
    }

    public void deleteById(Integer id) {
        trainingPeriodRepository.deleteById(id);
    }

    public void refreshNotification(TrainingPeriod trainingPeriod) {

        //계정 Mail 전송 구간.
        HashMap<String, Object> model = new HashMap<>();
        model.put("title", "Refresh Training 등록 알림");
        model.put("docVerInfo", trainingPeriod.getDocumentVersion().getDocInfo());
        model.put("trainingPeriod", trainingPeriod.getStrTrainingDate("yyyy-MM-dd"));
        String title = "";

        if(ObjectUtils.isEmpty(trainingPeriod.getId())) {
            title = "Refresh Training이 등록 되었습니다.";
        } else {
            title = "Refresh Training이 수정 되었습니다.";
        }

        List<String> toList = mailService.getReceiveEmails();

        Mail mail = Mail.builder()
                .to(toList.toArray(new String[toList.size()]))
                .subject(String.format("[ISO-MS/System] %s", title))
                .model(model)
                .templateName("refresh-training-notification")
                .build();

        mailService.sendMail(mail);
        log.debug("Document 정보가 발송되었습니다. {}", mail);
    }
}
