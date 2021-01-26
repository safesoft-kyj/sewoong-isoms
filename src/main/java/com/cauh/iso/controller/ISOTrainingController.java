package com.cauh.iso.controller;

import com.cauh.common.entity.Account;
import com.cauh.common.security.annotation.CurrentUser;
import com.cauh.iso.domain.*;
import com.cauh.iso.domain.constant.ISOType;
import com.cauh.iso.domain.constant.TrainingRequirement;
import com.cauh.iso.domain.constant.TrainingStatus;
import com.cauh.iso.repository.TrainingMatrixRepository;
import com.cauh.iso.service.ISOTrainingLogService;
import com.cauh.iso.utils.DateUtils;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequiredArgsConstructor
@Slf4j
@SessionAttributes({})
public class ISOTrainingController {

    private final TrainingMatrixRepository trainingMatrixRepository;
    private final ISOTrainingLogService isoTrainingLogService;

    @GetMapping("/training/iso/mytraining")
    public String myTraining(@PageableDefault(size = 25) Pageable pageable, @CurrentUser Account user, Model model) {

        Page<MyTraining> isoTrainingMatrices = trainingMatrixRepository.getISOMyTraining(pageable, user);
        model.addAttribute("trainingMatrix", isoTrainingMatrices);

        return "iso/training/trainingList";
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
        QTrainingLog qTrainingLog = QTrainingLog.trainingLog;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qTrainingLog.user.id.eq(user.getId()));
        builder.and(qTrainingLog.status.eq(TrainingStatus.COMPLETED));

        Iterable<ISOTrainingLog> isoTrainingLogs = isoTrainingLogService.findAll(builder, qTrainingLog.completeDate.desc());
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
            dto.setCompletionDate(DateUtils.format(log.getCompleteDate(), "dd-MMM-yyyy"));
            dto.setCourse(log.getTrainingCourse());
            dto.setHour(log.getHour());
            dto.setOrganization(log.getOrganization());

            isoTrainingLogDTOS.add(dto);
        });

        return isoTrainingLogDTOS;
    }

}
