package com.cauh.iso.domain;

import com.cauh.common.entity.BaseEntity;
import com.cauh.iso.domain.constant.PostStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditMappedBy;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_iso_certification")
@Slf4j
@SequenceGenerator(name = "ISO_CERTIFICATION_SEQ_GENERATOR", sequenceName = "SEQ_ISO_CERT", initialValue = 1, allocationSize = 1)
@ToString(of = {"id", "title"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Audited(withModifiedFlag = true)
public class ISOCertification extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 928374565908693720L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISO_CERTIFICATION_SEQ_GENERATOR")
    private Integer id;

    @Column(name = "title", columnDefinition = "nvarchar(255)", nullable = false)
    private String title;

    @Column(name = "cert_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date certDate; //인증 일자.

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    @OneToMany(mappedBy = "isoCertification")
    @AuditMappedBy(mappedBy = "isoCertification")
    private List<ISOCertificationAttachFile> attachFiles;

    @Transient
    private List<String> removeFiles;

    @Transient
    private MultipartFile[] uploadingFiles;
}
