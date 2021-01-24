package com.cauh.iso.domain;

import com.cauh.iso.domain.constant.OfflineTrainingStatus;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "s_iso_offline_training")
@SequenceGenerator(name = "ISO_OFFLINE_TRAINING_SEQ_GENERATOR", sequenceName = "SEQ_ISO_COURSE", initialValue = 1, allocationSize = 1)
@Audited(withModifiedFlag = true)
public class ISOOfflineTraining {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISO_OFFLINE_TRAINING_SEQ_GENERATOR")
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



}
