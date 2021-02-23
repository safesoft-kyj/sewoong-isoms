package com.cauh.iso.domain;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.ByteArrayInputStream;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_iso_training_certification_info")
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "ISO_TRAINING_CERTIFICATION_MANAGER_SEQ_GENERATOR", sequenceName = "SEQ_ISO_TRAINING_CERTIFICATION_MANAGER", initialValue = 1, allocationSize = 1)
@Audited(withModifiedFlag = true)
public class ISOTrainingCertificationInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8389130231819021141L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISO_TRAINING_CERTIFICATION_MANAGER_SEQ_GENERATOR")
    private Integer id;

    //수료증 prefix 정보
    private String prefix;

    //수료증 suffix 정보
    private String suffix;

    //수료증 담당자
    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Account manager;

    //담당자 표시명
    private String managerName;

    @Column(name = "base64signature", columnDefinition = "varchar(max)")
    private String base64signature;

    //수료증 정보 사용 여부.
    private Boolean active;

    @Transient
    private String userId;

    @Transient
    private ByteArrayInputStream sign;



}
