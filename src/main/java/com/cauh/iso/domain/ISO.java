package com.cauh.iso.domain;

import com.cauh.common.entity.BaseEntity;
import com.cauh.iso.domain.constant.ISOType;
import com.cauh.iso.domain.constant.PostStatus;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditMappedBy;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_iso")
@Slf4j
@SequenceGenerator(name = "ISO_SEQ_GENERATOR", sequenceName = "SEQ_ISO", initialValue = 1, allocationSize = 1)
@ToString(of = {"id", "title"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Audited(withModifiedFlag = true)
public class ISO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 765987308708693720L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISO_SEQ_GENERATOR")
    private Integer id;

    @Column(name = "title", columnDefinition = "nvarchar(255)", nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "nvarchar(MAX)", nullable = false)
    private String content;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "top_view_end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date topViewEndDate;

    @Enumerated(EnumType.STRING)
    private ISOType isoType;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    @OneToMany(mappedBy = "iso")
    @AuditMappedBy(mappedBy = "iso")
    private List<ISOAttachFile> attachFiles;

    @Transient
    private List<String> removeFiles;

    @Builder
    public ISO(String title, String content, Date topViewEndDate, PostStatus postStatus) {
        this.title = title;
        this.content = content;
        this.topViewEndDate = topViewEndDate;
        this.postStatus = postStatus;
    }
}
