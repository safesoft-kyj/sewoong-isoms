package com.dtnsm.esop.domain;

import com.dtnsm.common.entity.Account;
import com.dtnsm.common.entity.BaseEntity;
import com.dtnsm.esop.domain.constant.TrainingType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_training_period",
        indexes = {
            @Index(columnList = "type,start_date")
//            @Index(columnList = "type,documentVersionId,startDate")
        }
)//
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "TRAINING_PERIOD_SEQ_GENERATOR", sequenceName = "SEQ_TRAINING_PERIOD", initialValue = 1, allocationSize = 1)
public class TrainingPeriod extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 9199531710029094250L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAINING_PERIOD_SEQ_GENERATOR")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "document_version_id", referencedColumnName = "id")
    private DocumentVersion documentVersion;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TrainingType trainingType;

    @ManyToOne
    @JoinColumn(name = "retraining_user_id", referencedColumnName = "id")
    private Account retrainingUser;

    @OneToMany(mappedBy = "trainingPeriod")
    private List<TrainingLog> trainingLogs;


    @Builder
    private TrainingPeriod(DocumentVersion documentVersion, TrainingType trainingType, Date startDate, Date endDate) {
        this.documentVersion = documentVersion;
        this.trainingType = trainingType;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
