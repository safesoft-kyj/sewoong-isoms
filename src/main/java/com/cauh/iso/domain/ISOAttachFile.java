package com.cauh.iso.domain;

import com.cauh.common.entity.BaseEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_iso_attach_file")
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Audited(withModifiedFlag = true)
public class ISOAttachFile extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -8291230014950482889L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne
    @JoinColumn(name = "iso_id", referencedColumnName = "id")
    private ISO iso;

    @Column(name = "file_name", columnDefinition = "nvarchar(255)", nullable = false)
    private String fileName;

    @Column(name = "original_file_name", columnDefinition = "nvarchar(255)", nullable = false)
    private String originalFileName;

    @Column(name = "file_type", columnDefinition = "nvarchar(255)", nullable = false)
    private String fileType;

    @Column(name = "file_size")
    private long fileSize;

    @Column(name = "deleted")
    private boolean deleted;

    @Builder
    public ISOAttachFile(ISO iso, String fileName, String originalFileName, String fileType, long fileSize) {
        this.iso = iso;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }
}
