package com.cauh.iso.domain;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.BaseEntity;
import com.cauh.common.entity.constant.UserType;
import com.cauh.iso.domain.report.ExternalCustomer;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_non_disclosure_agreement", uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "created_date"}),
        @UniqueConstraint(columnNames = {"external_customer_id", "created_date"})}
)
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "NON_DISCLOSURE_AGREEMENT_SEQ_GENERATOR", sequenceName = "SEQ_NON_DISCLOSURE_AGREEMENT", initialValue = 1, allocationSize = 1)
public class NonDisclosureAgreement extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1332540590838346951L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NON_DISCLOSURE_AGREEMENT_SEQ_GENERATOR")
    private Integer id;

    @Column(name = "email", nullable = false, length = 64, updatable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name ="internal_user_id", referencedColumnName = "id")
    private Account internalUser; //내부 사용자

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @ManyToOne
    @JoinColumn(name = "external_customer_id", referencedColumnName = "id")
    private ExternalCustomer externalCustomer;

    @Column(name = "base64signature", columnDefinition = "varchar(max)")
    private String base64signature;

    @ManyToOne
    @JoinColumn(name = "document_version_id", referencedColumnName = "id")
    private DocumentVersion documentVersion;
}
