package com.cauh.iso.domain;

import com.cauh.common.entity.Account;
import com.cauh.iso.domain.constant.TrainingStatus;
import com.cauh.iso.domain.constant.TrainingType;
import com.cauh.iso.utils.DateUtils;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import static com.cauh.iso.domain.QISOTrainingMatrix.iSOTrainingMatrix;

@Data
@Slf4j
public class MyTraining implements Serializable {
    private static final long serialVersionUID = 850393592890613485L;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH);

    @QueryProjection
    public MyTraining(Document document, DocumentVersion documentVersion, TrainingPeriod trainingPeriod, TrainingLog trainingLog) {
        this.document = document;
        this.documentVersion = documentVersion;
        this.trainingPeriod = trainingPeriod;
        this.trainingLog = trainingLog;
    }
    @QueryProjection
    public MyTraining(Account user, Document document, DocumentVersion documentVersion, TrainingPeriod trainingPeriod, TrainingLog trainingLog) {
        this.user = user;
        this.document = document;
        this.documentVersion = documentVersion;
        this.trainingPeriod = trainingPeriod;
        this.trainingLog = trainingLog;
    }

    @QueryProjection
    public MyTraining(Account user, Document document, DocumentVersion documentVersion, TrainingPeriod trainingPeriod, TrainingLog trainingLog, Date startDate, Date endDate) {
        this.user = user;
        this.document = document;
        this.documentVersion = documentVersion;
        this.trainingPeriod = trainingPeriod;
        this.trainingLog = trainingLog;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    @QueryProjection
    public MyTraining(Account user, Document document, DocumentVersion documentVersion, TrainingPeriod trainingPeriod, TrainingLog trainingLog, Date startDate, Date endDate, Date jobAssignDate) {
        this.user = user;
        this.document = document;
        this.documentVersion = documentVersion;
        this.trainingPeriod = trainingPeriod;
        this.trainingLog = trainingLog;
        this.startDate = startDate;
        this.endDate = endDate;
        this.jobAssignDate = jobAssignDate;
    }
    @QueryProjection
    public MyTraining(Document document, DocumentVersion documentVersion, TrainingPeriod trainingPeriod, TrainingLog trainingLog, Date startDate, Date endDate, Date inDate) {
        this.document = document;
        this.documentVersion = documentVersion;
        this.trainingPeriod = trainingPeriod;
        this.trainingLog = trainingLog;
        this.startDate = startDate;
        this.endDate = endDate;
        this.inDate = inDate;
    }

    @QueryProjection
    public MyTraining(Document document, DocumentVersion documentVersion, TrainingPeriod trainingPeriod, TrainingLog trainingLog, Date startDate, Date endDate) {
        this.document = document;
        this.documentVersion = documentVersion;
        this.trainingPeriod = trainingPeriod;
        this.trainingLog = trainingLog;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @QueryProjection
    public MyTraining(ISO iso, ISOAttachFile isoAttachFile, ISOTrainingPeriod isoTrainingPeriod, ISOTrainingLog isoTrainingLog,  Date startDate, Date endDate){
        this.iso = iso;
        this.isoAttachFile = isoAttachFile;
        this.isoTrainingPeriod = isoTrainingPeriod;
        this.isoTrainingLog = isoTrainingLog;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    @QueryProjection
    public MyTraining(Account user, ISO iso, ISOTrainingPeriod isoTrainingPeriod, ISOTrainingLog isoTrainingLog,  Date startDate, Date endDate){
        this.user = user;
        this.iso = iso;
        this.isoTrainingPeriod = isoTrainingPeriod;
        this.isoTrainingLog = isoTrainingLog;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getInDateOrJobAssignDate() {
        if(ObjectUtils.isEmpty(user)) {
            return null;
        }
        if(ObjectUtils.isEmpty(inDate)) {
            inDate = user.getIndate();
        }

        Optional<TrainingMatrix> optionalSOPTrainingMatrix = documentVersion.getTrainingMatrixList()
                .stream()
                .filter(t -> t.isTrainingAll()).findFirst();

        if(optionalSOPTrainingMatrix.isPresent()) {
            log.debug("<= 입사일을 기준으로 :: {}", inDate);
            return inDate;
        } else {

            if(!ObjectUtils.isEmpty(jobAssignDate)) {
                log.info("@jobAssignDate 반환 : {}", jobAssignDate);
                return DateUtils.truncate(jobAssignDate);
            }

            log.warn(user.getName() + "사용자의 Job Assign Date 정보가 존재하지 않습니다.");

            return inDate;
        }
    }

    public String getStringStatus() {
        if(ObjectUtils.isEmpty(iso)) {
            return ObjectUtils.isEmpty(trainingLog) || ObjectUtils.isEmpty(trainingLog.getStatus()) ? TrainingStatus.NOT_STARTED.getLabel() : trainingLog.getStatus().getLabel();
        } else {
            return ObjectUtils.isEmpty(isoTrainingLog) || ObjectUtils.isEmpty(isoTrainingLog.getStatus()) ? TrainingStatus.NOT_STARTED.getLabel() : isoTrainingLog.getStatus().getLabel();
        }
    }

    public Date getUserStartDate() {
        if(ObjectUtils.isEmpty(iso)) { //SOP 일때만 적용
            if (trainingPeriod.getTrainingType() == TrainingType.SELF) {
                Date inDateOrJobAssignDate = getInDateOrJobAssignDate();
                if (ObjectUtils.isEmpty(inDateOrJobAssignDate)) {
                    return startDate;
                }
                if (this.startDate.compareTo(inDateOrJobAssignDate) == -1) {
                    log.info("@User : {} SOP[{}] Training 시작일[{}]이 입사일/직무 배정일[{}] 보다 이전 입니다. 입사일/직무 배정일로 대체",
                            user.getUsername(), document.getDocId(), startDate, inDateOrJobAssignDate);
                    return inDateOrJobAssignDate;
                }
                if (!ObjectUtils.isEmpty(documentVersion.getApprovedDate())) {
                    if (this.startDate.compareTo(documentVersion.getApprovedDate()) == -1) {
                        log.info("@User : {} SOP[{}] Training 시작일[{}]이 SOP Approved Date[{}] 보다 이전 입니다. Approved Date 로 대체",
                                user.getUsername(), document.getDocId(), startDate, documentVersion.getApprovedDate());

                        return documentVersion.getApprovedDate();
                    }
                }

                if (documentVersion.getEffectiveDate().compareTo(inDateOrJobAssignDate) <= 0) {
                    return DateUtils.addDay(inDateOrJobAssignDate, 1);
                } else {
                    return startDate;
                }
            } else {
                return startDate;
            }
        } else {
            return startDate;
        }
    }

    public Date getUserEndDate() {
        if(ObjectUtils.isEmpty(iso)) { //SOP 일때만 적용
            if(trainingPeriod.getTrainingType() == TrainingType.SELF) {
                Date inDateOrJobAssignDate = getInDateOrJobAssignDate();
                if(ObjectUtils.isEmpty(inDateOrJobAssignDate)) {
                    return endDate;
                }

                Date plusEndDate = DateUtils.addDay(inDateOrJobAssignDate, 57);
                if (documentVersion.getEffectiveDate().compareTo(plusEndDate) <= 0 || documentVersion.getEffectiveDate().compareTo(inDateOrJobAssignDate) <= 0) {
                    log.info("Effective Date 가 입사일/직무배정일 + 57보다 이전인 경우");
                    return plusEndDate;
                } else {
                    return endDate;
                }
            } else {
                return endDate;
            }
        } else {
            return endDate;
        }
    }

    public String getDeviation() {
        if(isUserOutOfPeriod() && (ObjectUtils.isEmpty(trainingLog) || ObjectUtils.isEmpty(trainingLog.getCompleteDate()))) {
            return "Y";
        } else if(ObjectUtils.isEmpty(trainingLog) == false && ObjectUtils.isEmpty(trainingLog.getCompleteDate()) == false) {
            return getUserEndDate().before(DateUtils.truncate(trainingLog.getCompleteDate())) ? "Y" : "N";
        }

        return "N";
    }

    public boolean isUserOutOfPeriod() {
        return getUserEndDate().before(DateUtils.truncate(new Date()));
    }

    public String getEffectiveDate() {
        return toString(documentVersion.getEffectiveDate());
    }

    public String getStringStartDate() {
        return toString(getUserStartDate());
    }

    public String getStringEndDate() {
        return toString(getUserEndDate());
    }

    public String getCompleteDate() {
        if(ObjectUtils.isEmpty(iso)) {
            return ObjectUtils.isEmpty(trainingLog) ? "" : toString(trainingLog.getCompleteDate());
        } else {
            return ObjectUtils.isEmpty(isoTrainingLog) ? "" : toString(isoTrainingLog.getCompleteDate());
        }
    }

    private String toString(Date date) {
        if(ObjectUtils.isEmpty(date)) {
            return "";
        } else {
            return dateFormat.format(date);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MyTraining that = (MyTraining) o;

        return documentVersion.getId().equals(that.getDocumentVersion().getId());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(documentVersion)
                .toHashCode();
    }


    private Account user;

    private Date inDate;

    private Date startDate;

    private Date endDate;

    //SOP Training
    private Date jobAssignDate;

    private Document document;

    private DocumentVersion documentVersion;

    private TrainingPeriod trainingPeriod;

    private TrainingLog trainingLog;


    //ISO Training
    private ISO iso;

    private ISOAttachFile isoAttachFile;

    private ISOTrainingPeriod isoTrainingPeriod;

    private ISOTrainingLog isoTrainingLog;



}
