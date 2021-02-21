package com.cauh.iso.domain;

import com.cauh.common.entity.BaseEntity;
import com.cauh.iso.domain.constant.TrainingStatus;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_iso_training_test_log")
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "ISO_TRAINING_TEST_LOG_SEQ_GENERATOR", sequenceName = "SEQ_ISO_TRAINING_TEST_LOG", initialValue = 1, allocationSize = 1)
@Audited(withModifiedFlag = true)
public class ISOTrainingTestLog extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -8701587768958983931L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISO_TRAINING_TEST_LOG_SEQ_GENERATOR")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "iso_training_log_id", referencedColumnName = "id")
    private ISOTrainingLog isoTrainingLog;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 15)
    private TrainingStatus status;

    @Column(name = "score")
    private Integer score;

    @Column(name = "quiz", columnDefinition = "nvarchar(max)")
    private Quiz quiz;

    @Builder
    public ISOTrainingTestLog(ISOTrainingLog isoTrainingLog, TrainingStatus status, Integer score, Quiz quiz) {
        this.isoTrainingLog = isoTrainingLog;
        this.status = status;
        this.score = score;
        this.quiz = quiz;
    }
}
