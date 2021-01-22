package com.cauh.iso.domain;

import com.cauh.common.entity.Account;
import com.cauh.iso.domain.constant.DeviationReportStatus;
import com.cauh.iso.domain.constant.TrainingStatus;
import com.cauh.iso.domain.constant.TrainingType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_iso_training")
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "ISO_TRAINING_LOG_SEQ_GENERATOR", sequenceName = "SEQ_ISO_TRAINING_LOG", initialValue = 1, allocationSize = 1)
public class ISOTrainingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISO_TRAINING_LOG_SEQ_GENERATOR")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "isotraining_period_id", referencedColumnName = "id")
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

    @Column(name = "progress_percent")
    private double progressPercent;

    @Column(name = "last_page_no")
    private Integer lastPageNo;

    @Column(name = "training_time")
    private Integer trainingTime;

    @Column(name = "score")
    private Integer score;

}
