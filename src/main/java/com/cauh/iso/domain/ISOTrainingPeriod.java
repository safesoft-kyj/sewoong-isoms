package com.cauh.iso.domain;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.BaseEntity;
import com.cauh.iso.domain.constant.DocumentType;
import com.cauh.iso.domain.constant.TrainingType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_iso_training_period", indexes = {@Index(columnList = "type,start_date")})
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "ISO_TRAINING_PERIOD_SEQ_GENERATOR", sequenceName = "SEQ_ISO_TRAINING_PERIOD", initialValue = 1, allocationSize = 1)
@Audited(withModifiedFlag = true)
public class ISOTrainingPeriod extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 9199531710029094250L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISO_TRAINING_PERIOD_SEQ_GENERATOR")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "iso_id", referencedColumnName = "id")
    private ISO iso;

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

    @OneToMany(mappedBy = "isoTrainingPeriod")
    @NotAudited
    private List<ISOTrainingLog> isoTrainingLogs;


    @Builder
    private ISOTrainingPeriod(ISO iso, TrainingType trainingType, Date startDate, Date endDate) {
        this.iso = iso;
        this.trainingType = trainingType;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}