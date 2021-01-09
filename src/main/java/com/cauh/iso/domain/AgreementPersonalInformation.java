package com.cauh.iso.domain;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.BaseEntity;
import com.cauh.common.entity.constant.UserType;
import com.cauh.iso.domain.report.ExternalCustomer;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_agreement_personal_information", uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "created_date"}),
        @UniqueConstraint(columnNames = {"external_customer_id", "created_date"})}
        )
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "AGREEMENT_PERSONAL_INFO_SEQ_GENERATOR", sequenceName = "SEQ_AGREEMENT_PERSONAL_INFO", initialValue = 1, allocationSize = 1)
@Audited(withModifiedFlag = true)
public class AgreementPersonalInformation extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 8353552912714083258L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AGREEMENT_PERSONAL_INFO_SEQ_GENERATOR")
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
    private ExternalCustomer externalCustomer; //외부 사용자.

    @Column(name = "agree")
    private boolean agree;

    @Column(name = "base64signature", columnDefinition = "varchar(max)")
    private String base64signature; //외부 사용자 전용 서명란.

    @ManyToOne
    @JoinColumn(name = "document_version_id", referencedColumnName = "id")
    private DocumentVersion documentVersion;

    @Builder
    public AgreementPersonalInformation(DocumentVersion documentVersion, ExternalCustomer externalCustomer) {
        this.documentVersion = documentVersion;
        this.externalCustomer = externalCustomer;
    }
}
