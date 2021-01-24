package com.cauh.iso.domain;


import com.cauh.common.entity.BaseEntity;
import com.cauh.iso.domain.constant.DocumentType;
import com.cauh.iso.domain.constant.ISOType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_iso_training_matrix_file")
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "ISO_TRAINING_MATRIX_FILE_SEQ_GENERATOR", sequenceName = "SEQ_TRAINING_MATRIX_FILE", initialValue = 1, allocationSize = 1)
@Audited(withModifiedFlag = true)
public class ISOTrainingMatrixFile extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -6517035819947273041L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISO_TRAINING_MATRIX_FILE_SEQ_GENERATOR")
    private Integer id;

    @Column(name = "title", columnDefinition = "nvarchar(255)")
    private String title;

    @Column(name = "file_name", columnDefinition = "nvarchar(255)", nullable = false)
    private String fileName;

    @Column(name = "original_file_name", columnDefinition = "nvarchar(255)", nullable = false)
    private String originalFileName;

    @Column(name = "file_type", columnDefinition = "nvarchar(255)", nullable = false)
    private String fileType;

    @Column(name = "iso_type")
    @Enumerated(EnumType.STRING)
    private ISOType isoType;

    @Column(name = "file_size")
    private long fileSize;

    @Transient
    private MultipartFile uploadFile;

    @Builder
    public ISOTrainingMatrixFile(String title, String fileName, String originalFileName, String fileType, ISOType isoType, long fileSize) {
        this.title = title;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
        this.fileType = fileType;
        this.isoType = isoType;
        this.fileSize = fileSize;
    }
}
