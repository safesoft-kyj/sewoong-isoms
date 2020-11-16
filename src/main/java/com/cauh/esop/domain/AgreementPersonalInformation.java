package com.cauh.esop.domain;

import com.cauh.common.entity.BaseEntity;
import com.cauh.esop.domain.report.ExternalCustomer;
import com.cauh.esop.domain.report.SOPDisclosureRequestForm;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

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
public class AgreementPersonalInformation extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 8353552912714083258L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AGREEMENT_PERSONAL_INFO_SEQ_GENERATOR")
    private Integer id;

    @Column(name = "email", nullable = false, length = 64, updatable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "external_customer_id", referencedColumnName = "id")
    private ExternalCustomer externalCustomer;

    @Column(name = "agree")
    private boolean agree;

    @Column(name = "base64signature", columnDefinition = "varchar(max)")
    private String base64signature;

    @ManyToOne
    @JoinColumn(name = "document_version_id", referencedColumnName = "id")
    private DocumentVersion documentVersion;

    @Builder
    public AgreementPersonalInformation(DocumentVersion documentVersion, ExternalCustomer externalCustomer) {
        this.documentVersion = documentVersion;
        this.externalCustomer = externalCustomer;
    }
}
