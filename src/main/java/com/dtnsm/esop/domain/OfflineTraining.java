package com.dtnsm.esop.domain;

import com.dtnsm.common.entity.BaseEntity;
import com.dtnsm.esop.domain.constant.OfflineTrainingStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_offline_training")
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "OFFLINE_TRAINING_SEQ_GENERATOR", sequenceName = "SEQ_OFFLINE_TRAINING", initialValue = 1, allocationSize = 1)
public class OfflineTraining extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 2129309089355715487L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OFFLINE_TRAINING_SEQ_GENERATOR")
    private Integer id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "training_date")
    private Date trainingDate;

    @Column(name = "organization", columnDefinition = "nvarchar(100)")
    private String organization;

    @Column(name = "emp_no", length = 11)
    private String empNo;

    @Column(name = "status", length = 10)
    @Enumerated(EnumType.STRING)
    private OfflineTrainingStatus status;

    @OneToMany(mappedBy = "offlineTraining")
    private List<OfflineTrainingDocument> offlineTrainingDocuments = new ArrayList<>();

    @OneToMany(mappedBy = "offlineTraining")
    private List<OfflineTrainingAttendee> offlineTrainingAttendees = new ArrayList<>();

    @Transient
    private String[] sopIds;

    @Transient
    private List<DocumentVersion> trainingSOPs = new ArrayList<>();

    @Transient
    private String[] attendees;
}
