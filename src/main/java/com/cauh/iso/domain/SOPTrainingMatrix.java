package com.cauh.iso.domain;

import com.cauh.common.entity.BaseEntity;
import com.cauh.common.entity.JobDescription;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_sop_training_matrix",
        uniqueConstraints = @UniqueConstraint(columnNames = {"document_version_id", "job_description_id"}),
        indexes = @Index(columnList = "training_all,job_description_id")
)
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "TRAINING_MATRIX_SEQ_GENERATOR", sequenceName = "SEQ_TRAINING_MATRIX", initialValue = 1, allocationSize = 1)
public class SOPTrainingMatrix extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAINING_MATRIX_SEQ_GENERATOR")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "document_version_id", referencedColumnName = "id")
    private DocumentVersion documentVersion;

    @Column(name = "training_all")
    private boolean trainingAll;

    @ManyToOne
    @JoinColumn(name = "job_description_id", referencedColumnName = "id")
    private JobDescription jobDescription;


    @Builder
    public SOPTrainingMatrix(DocumentVersion documentVersion, boolean trainingAll, JobDescription jobDescription) {
        this.documentVersion = documentVersion;
        this.trainingAll = trainingAll;
        this.jobDescription = jobDescription;
    }
}
