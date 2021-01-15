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
import java.io.Serializable;


/**
 * 내부사용자 전용 기밀 유지 서약 항목
 */
@Data
@NoArgsConstructor
@Entity
@Table(name ="s_confidentiality_pledge")
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "CONFIDENTIALITY_PLEDGE_SEQ_GENERATOR", sequenceName = "SEQ_CONFIDENTIALITY_PLEDGE", initialValue = 1, allocationSize = 1)
@Audited(withModifiedFlag = true)
public class ConfidentialityPledge extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONFIDENTIALITY_PLEDGE_SEQ_GENERATOR")
    private Integer id;

    @Column(name = "email", nullable = false, length = 64, updatable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name ="internal_user_id", referencedColumnName = "id")
    private Account internalUser; //내부 사용자

    @Column(name = "agree")
    private boolean agree;

//    @Column(name = "base64signature", columnDefinition = "varchar(max)")
//    private String base64signature; //외부 사용자 전용 서명란.

    @ManyToOne
    @JoinColumn(name = "document_version_id", referencedColumnName = "id")
    private DocumentVersion documentVersion;

}
