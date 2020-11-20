package com.cauh.iso.domain;

import com.cauh.common.entity.BaseEntity;
import com.cauh.iso.domain.constant.TrainingStatus;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_training_test_log")
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "TRAINING_TEST_LOG_SEQ_GENERATOR", sequenceName = "SEQ_TRAINING_TEST_LOG", initialValue = 1, allocationSize = 1)
public class TrainingTestLog extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -8701587768958983931L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAINING_TEST_LOG_SEQ_GENERATOR")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "training_log_id", referencedColumnName = "id")
    private TrainingLog trainingLog;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 15)
    private TrainingStatus status;

    @Column(name = "score")
    private Integer score;

    @Column(name = "quiz", columnDefinition = "nvarchar(max)")
    private Quiz quiz;

    @Builder
    public TrainingTestLog(TrainingLog trainingLog, TrainingStatus status, Integer score, Quiz quiz) {
        this.trainingLog = trainingLog;
        this.status = status;
        this.score = score;
        this.quiz = quiz;
    }
}
