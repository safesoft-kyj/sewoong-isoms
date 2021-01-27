package com.cauh.iso.domain;

import com.cauh.common.entity.BaseEntity;
import com.cauh.iso.domain.constant.DocumentAccessType;
import com.cauh.iso.domain.constant.ISOType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_iso_access_log")
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class ISOAccessLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4975840968271251391L;
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", length = 40)
    private String id;

    @ManyToOne
    @JoinColumn(name = "iso_id", referencedColumnName = "id")
    private ISO iso;

    @ManyToOne
    @JoinColumn(name = "iso_training_certification_id", referencedColumnName = "id")
    private ISOTrainingCertification isoTrainingCertification;

    @ManyToOne
    @JoinColumn(name = "iso_certification_id", referencedColumnName = "id")
    private ISOCertification isoCertification;

    @Column(name ="iso_type")
    @Enumerated(EnumType.STRING)
    private ISOType isoType;

    @Column(name = "access_type")
    @Enumerated(EnumType.STRING)
    private DocumentAccessType accessType;

    @Builder
    public ISOAccessLog(ISO iso, ISOCertification isoCertification, ISOTrainingCertification isoTrainingCertification, ISOType isoType, DocumentAccessType accessType) {
        this.iso = iso;
        this.isoCertification = isoCertification;
        this.isoTrainingCertification = isoTrainingCertification;
        this.isoType = isoType;
        this.accessType = accessType;
    }
}
