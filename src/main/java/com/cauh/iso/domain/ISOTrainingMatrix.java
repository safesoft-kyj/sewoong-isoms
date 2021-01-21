package com.cauh.iso.domain;

import com.cauh.common.entity.BaseEntity;
import com.cauh.common.entity.JobDescription;
import com.cauh.common.entity.Role;
import com.cauh.iso.domain.constant.DocumentType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditMappedBy;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_iso_training_matrix",
        uniqueConstraints = @UniqueConstraint(columnNames = {"iso_id", "job_description_id"}),
        indexes = @Index(columnList = "training_all, job_description_id")
)
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "ISO_TRAINING_MATRIX_SEQ_GENERATOR", sequenceName = "SEQ_ISO_TRAINING_MATRIX", initialValue = 1, allocationSize = 1)
@Audited(withModifiedFlag = true)
public class ISOTrainingMatrix extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISO_TRAINING_MATRIX_SEQ_GENERATOR")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "iso_id", referencedColumnName = "id")
    @NotAudited
    private ISO iso;

    @Column(name = "training_all")
    private boolean trainingAll;

//    @ManyToOne
//    @JoinColumn(name = "job_description_id", referencedColumnName = "id")
//    private JobDescription jobDescription;

    @ManyToOne
    @JoinColumn(name="job_description_id", referencedColumnName = "id")
    private JobDescription jobDescription;


    @Builder
    public ISOTrainingMatrix(ISO iso, boolean trainingAll, JobDescription jobDescription) {
        this.iso = iso;
        this.trainingAll = trainingAll;
        this.jobDescription = jobDescription;
    }
}
