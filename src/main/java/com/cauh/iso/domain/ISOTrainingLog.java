package com.cauh.iso.domain;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.BaseEntity;
import com.cauh.iso.domain.constant.DeviationReportStatus;
import com.cauh.iso.domain.constant.TrainingStatus;
import com.cauh.iso.domain.constant.TrainingType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_iso_training_log")
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "ISO_TRAINING_LOG_SEQ_GENERATOR", sequenceName = "SEQ_ISO_TRAINING_LOG", initialValue = 1, allocationSize = 1)
@Audited(withModifiedFlag = true)
public class ISOTrainingLog extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 7139156238208003683L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISO_TRAINING_LOG_SEQ_GENERATOR")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "iso_training_period_id", referencedColumnName = "id")
    private ISOTrainingPeriod isoTrainingPeriod;

    @ManyToOne
    @JoinColumn(name = "iso_offline_training_id", referencedColumnName = "id")
    private ISOOfflineTraining isoOfflineTraining;

    @ManyToOne
    @JoinColumn(name = "iso_id", referencedColumnName = "id")
    private ISO iso;

    @OneToMany(mappedBy = "isoTrainingLog")
    private List<ISOTrainingTestLog> isoTrainingTestLogs;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Account user;

    @Column(name = "complete_date")
    private Date completeDate;

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


    public String getTrainingCourse() {
        return "[" + iso.getIsoType().getLabel() + "] " + iso.getTitle();
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
