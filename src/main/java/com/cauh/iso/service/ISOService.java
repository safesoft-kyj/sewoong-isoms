package com.cauh.iso.service;

import com.cauh.iso.domain.*;
import com.cauh.iso.domain.constant.PostStatus;
import com.cauh.iso.domain.constant.TrainingType;
import com.cauh.iso.repository.ISOAttachFileRepository;
import com.cauh.iso.repository.ISORepository;
import com.cauh.iso.utils.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ISOService {
    private final ISORepository isoRepository;
    private final ISOAttachFileRepository isoAttachFileRepository;
    private final FileStorageService fileStorageService;
    private final MailService mailService;

    private final ISOTrainingMatrixService isoTrainingMatrixService;
    private final ISOTrainingPeriodService isoTrainingPeriodService;

    public Iterable<ISO> getTopISOs(Predicate predicate) {
        return isoRepository.findAll(predicate, Sort.by(Sort.Direction.DESC, "createdDate"));
    }

    public Page<ISO> getPage(Predicate predicate, Pageable pageable) {
        return isoRepository.findAll(predicate, pageable);
    }

    public ISO findById(String isoId) {
        return isoRepository.findById(isoId).isPresent()?isoRepository.findById(isoId).get():null;
    }

    public Iterable<ISO> findAll(Predicate predicate) {
        return isoRepository.findAll(predicate);
    }

    public String isoActivate(ISO iso) {

        List<ISOTrainingPeriod> isoTrainingPeriods = iso.getIsoTrainingPeriods();
        Optional<ISOTrainingPeriod> isoTrainingPeriodOptional = isoTrainingPeriods.stream().filter(p -> p.getTrainingType() == TrainingType.SELF).findFirst();

        if(isoTrainingPeriodOptional.isPresent()) {
            ISOTrainingPeriod isoTrainingPeriod = isoTrainingPeriodOptional.get();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = isoTrainingPeriod.getStartDate();
            //?????? ???????????? ??????
            Date today = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

            if(startDate.before(today)) {
                return "ISO ?????? Active ????????? ?????????????????????.<br>?????? ????????? ??????????????????.(" + df.format(startDate) + "??? TODAY )";
            }
        }

        //ISO ????????? Active??? ??????.
        iso.setActive(true);

        return "success";
    }

    public String isoPeriodExpand(ISO iso, Integer expandCount){

        Optional<ISOTrainingPeriod> isoTrainingPeriodOptional = iso.getIsoTrainingPeriods().stream().findFirst();

        if(isoTrainingPeriodOptional.isPresent()) {
            ISOTrainingPeriod isoTrainingPeriod = isoTrainingPeriodOptional.get();
            isoTrainingPeriod.setEndDate(DateUtils.addDay(isoTrainingPeriod.getEndDate(), expandCount));
            ISOTrainingPeriod savedISOTrainingPeriod = isoTrainingPeriodService.save(isoTrainingPeriod);
            log.debug("@ISO Period Exapnd ?????? : {}", savedISOTrainingPeriod);

        } else {
            return "ISO Training ?????? ????????? ?????????????????????.";
        }

        return "success";
    }

    public ISO saveISO(ISO iso, MultipartFile file) {
        if(StringUtils.isEmpty(iso.getId())) {
            iso.setId(UUID.randomUUID().toString());
            log.debug("ISO Ver Id. ?????? : {}", iso.getId());
        }

        ISO savedISO = isoRepository.save(iso);

        //????????? ISO??? ????????? Training??? ?????? pdf????????? image??? parsing,
        if (!ObjectUtils.isEmpty(file.getOriginalFilename())) {

            //?????? ????????? ???, ????????? ?????? ?????? ??????
            if (!ObjectUtils.isEmpty(iso.getAttachFiles())) {
                iso.getAttachFiles().stream().forEach(removeFile -> isoAttachFileRepository.deleteById(removeFile.getId())); //?????? ??????.
            }

            String fileName = fileStorageService.storeFile(file, "iso_" + savedISO.getId());
            //?????? ????????? ??????
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1);

            ISOAttachFile attacheFile = ISOAttachFile.builder()
                    .iso(savedISO)
                    .fileName(fileName)
                    .originalFileName(file.getOriginalFilename())
                    .ext(ext)
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .build();

            //?????? ??????????????? ????????? ????????????.
            if(savedISO.isTraining()) {
                Integer totalPage = fileStorageService.conversionPdf2Img(file, savedISO.getId());
                attacheFile.setTotalPage(totalPage);
            }

            attacheFile.setExt(ext);
            isoAttachFileRepository.save(attacheFile);
        }

        if(iso.isTraining()) {
            //ISO Training ?????? ??????
            isoTrainingPeriodService.saveAll(savedISO, iso);

            //ISO Training ????????? ??????
            isoTrainingMatrixService.saveAll(savedISO, iso);

        }

        return savedISO;
    }

    public ISO saveQuiz(String isoId, Quiz quiz) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Optional<ISO> isoOptional = getISO(isoId);
        if(isoOptional.isEmpty()){return null;}
        ISO iso = isoOptional.get();

        iso.setQuiz(objectMapper.writeValueAsString(quiz));
        log.info("=> isoId : {}, Quiz ??????!", isoId);
        return isoRepository.save(iso);
    }

    public void remove(ISO iso) {
        iso.setDeleted(true);
        isoRepository.save(iso);
    }

    public Optional<ISO> getISO(String isoId) {
        return isoRepository.findById(isoId);
    }

    public Optional<ISOAttachFile> getAttachFile(String id) {
        return isoAttachFileRepository.findById(id);
    }


    public void sendMail(String isoId) {
        ISO iso = getISO(isoId).get();

        HashMap<String, Object> model = new HashMap<>();
        model.put("title", iso.getTitle());
        model.put("content", "[ISO-MS] ????????? ISO ????????? ?????? ???????????????.");

        List<String> toList = mailService.getReceiveEmails();
        if (ObjectUtils.isEmpty(toList) == false) {

            Mail mail = Mail.builder()
                    .to(toList.toArray(new String[toList.size()]))
                    .subject("[ISO-MS/System/ISO-14155] " + iso.getTitle())
                    .model(model)
                    .templateName("iso-template")
                    .build();
            mailService.sendMail(mail);
        }

        iso.setPostStatus(PostStatus.SENT);
        isoRepository.save(iso);
    }
}
