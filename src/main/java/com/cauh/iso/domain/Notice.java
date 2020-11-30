package com.cauh.iso.domain;

import com.cauh.common.entity.BaseEntity;
import com.cauh.iso.domain.constant.NoticeStatus;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditMappedBy;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_notice")
@Slf4j
@SequenceGenerator(name = "SOP_NOTICE_SEQ_GENERATOR", sequenceName = "SEQ_SOP_NOTICE", initialValue = 1, allocationSize = 1)
@ToString(of = {"id", "title"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Audited(withModifiedFlag = true)
//@Convert(attributeName = "topViewEndDate", converter = TimestampConverter.class)
public class Notice extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 896163268708693720L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOP_NOTICE_SEQ_GENERATOR")
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

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private NoticeStatus noticeStatus;

    @OneToMany(mappedBy = "notice")
    @AuditMappedBy(mappedBy = "notice")
    private List<NoticeAttachFile> attachFiles;

    @Transient
    private List<String> removeFiles;

    @Builder
    public Notice(String title, String content, Date topViewEndDate, NoticeStatus noticeStatus) {
        this.title = title;
        this.content = content;
        this.topViewEndDate = topViewEndDate;
        this.noticeStatus = noticeStatus;
    }
}
