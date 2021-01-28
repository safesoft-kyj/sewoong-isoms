package com.cauh.iso.domain;

import com.cauh.common.entity.BaseEntity;
import com.cauh.iso.domain.constant.ISOType;
import com.cauh.iso.domain.constant.OfflineTrainingStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditMappedBy;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_iso_offline_training")
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "ISO_OFFLINE_TRAINING_SEQ_GENERATOR", sequenceName = "SEQ_ISO_COURSE", initialValue = 1, allocationSize = 1)
@Audited(withModifiedFlag = true)
public class ISOOfflineTraining extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 8729435689355715487L;

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

    @Column(name = "iso_type")
    @Enumerated(EnumType.STRING)
    private ISOType isoType;

    @OneToMany(mappedBy = "isoOfflineTraining")
    @AuditMappedBy(mappedBy = "isoOfflineTraining")
    private List<ISOOfflineTrainingDocument> isoOfflineTrainingDocuments = new ArrayList<>();

    @OneToMany(mappedBy = "isoOfflineTraining")
    @AuditMappedBy(mappedBy = "isoOfflineTraining")
    private List<ISOOfflineTrainingAttendee> isoOfflineTrainingAttendees = new ArrayList<>();

    @Transient
    private String[] isoIds;

    @Transient
    private List<ISO> trainingISOs = new ArrayList<>();

    @Transient
    private String[] attendees;

}
