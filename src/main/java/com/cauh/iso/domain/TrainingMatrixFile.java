package com.cauh.iso.domain;


import com.cauh.common.entity.BaseEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_training_matrix_file")
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "TRAINING_MATRIX_FILE_SEQ_GENERATOR", sequenceName = "SEQ_TRAINING_MATRIX_FILE", initialValue = 1, allocationSize = 1)
public class TrainingMatrixFile extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -6517035819947273041L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAINING_MATRIX_FILE_SEQ_GENERATOR")
    private Integer id;

    @Column(name = "title", columnDefinition = "nvarchar(255)")
    private String title;

    @Column(name = "file_name", columnDefinition = "nvarchar(255)", nullable = false)
    private String fileName;

    @Column(name = "original_file_name", columnDefinition = "nvarchar(255)", nullable = false)
    private String originalFileName;

    @Column(name = "file_type", columnDefinition = "nvarchar(255)", nullable = false)
    private String fileType;

    @Column(name = "file_size")
    private long fileSize;

    @Transient
    private MultipartFile uploadFile;

    @Builder
    public TrainingMatrixFile(String title, String fileName, String originalFileName, String fileType, long fileSize) {
        this.title = title;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }
}
