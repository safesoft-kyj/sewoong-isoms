package com.cauh.esop.domain;

import com.cauh.common.entity.BaseEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_notice_attach_file")
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class NoticeAttachFile extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -6940320014950482889L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne
    @JoinColumn(name = "notice_id", referencedColumnName = "id")
    private Notice notice;

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
    public NoticeAttachFile(Notice notice, String fileName, String originalFileName, String fileType, long fileSize) {
        this.notice = notice;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }
}
