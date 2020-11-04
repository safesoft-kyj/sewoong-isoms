package com.dtnsm.esop.domain;

import com.dtnsm.common.entity.BaseEntity;
import com.dtnsm.common.entity.Account;
import com.dtnsm.esop.domain.constant.DeviationReportStatus;
import com.dtnsm.esop.domain.constant.TrainingStatus;
import com.dtnsm.esop.domain.constant.TrainingType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_training_log", indexes = @Index(columnList = "training_period_id,document_version_id,user_id"))
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "TRAINING_LOG_SEQ_GENERATOR", sequenceName = "SEQ_TRAINING_LOG", initialValue = 1, allocationSize = 1)
public class TrainingLog extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 3302461038208003683L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAINING_LOG_SEQ_GENERATOR")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "training_period_id", referencedColumnName = "id")
    private TrainingPeriod trainingPeriod;

    @ManyToOne
    @JoinColumn(name = "offline_training_id", referencedColumnName = "id")
    private OfflineTraining offlineTraining;

    @ManyToOne(optional = false)
    @JoinColumn(name = "document_version_id", referencedColumnName = "id")
    private DocumentVersion documentVersion;

    @OneToMany(mappedBy = "trainingLog")
    private List<TrainingTestLog> trainingTestLogs;

//    @Column(name = "empNo", length = 10)
//    private String empNo;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Account user;

    @Column(name = "complete_date")
    private Date completeDate;

    @Column(name = "report_status")
    @Enumerated(EnumType.STRING)
    private DeviationReportStatus reportStatus;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TrainingStatus status;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TrainingType type;

    @Column(name = "organization_other", columnDefinition = "nvarchar(100)")
    private String organizationOther;

    @Column(name = "progress_percent")
    private double progressPercent;

    @Column(name = "last_page_no")
    private Integer lastPageNo;

    @Column(name = "training_time")
    private Integer trainingTime;

    @Column(name = "score")
    private Integer score;

    @Transient
    private String importTrainingCourse;
    @Transient
    private boolean matched;//Training Log Import 시 타이틀 정보 정규식 일치 여부

    @Builder
    public TrainingLog(DocumentVersion documentVersion, OfflineTraining offlineTraining, Account user, Date completeDate, DeviationReportStatus reportStatus,
                       TrainingStatus status, TrainingType type, String organizationOther, Integer trainingTime) {
        this.documentVersion = documentVersion;
        this.offlineTraining = offlineTraining;
        this.user = user;
        this.completeDate = completeDate;
        this.reportStatus = reportStatus;
        this.status = status;
        this.type = type;
        this.organizationOther = organizationOther;
        this.trainingTime = trainingTime;
    }

    public String getTrainingCourse() {
        return documentVersion.getDocument().getDocId() + " v" + documentVersion.getVersion() +" " + documentVersion.getDocument().getTitle();
    }

    public String getHour() {
        //off-line 등록 시 0.5 * 3600 == 초로 변경하여 저장
        double t = trainingTime;
        double hr;
        if(type != TrainingType.OTHER) {
            if (t <= 1800) {
                hr = 0.5;
            } else {
                hr = t / 3600;
            }
        } else {
            hr = t / 3600;
        }

        return String.format("%.1f", hr);
    }

    public String getOrganization() {
        if(type == TrainingType.OTHER) {
            return organizationOther;
        }

        //Other 를 제외한 모든 교육 로그는 Self-training으로 출력 되도록 요청(lhj)
        return TrainingType.SELF.getLabel();
    }
}
