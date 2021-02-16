package com.cauh.iso.domain.report;

import com.cauh.common.entity.BaseEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
@Data
@NoArgsConstructor
@Entity
@Table(name = "s_external_customer", uniqueConstraints = @UniqueConstraint(columnNames = {"sop_disclosure_request_form_id", "email"}))
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "EXTERNAL_CUSTOMER_SEQ_GENERATOR", sequenceName = "SEQ_EXTERNAL_CUSTOMER", initialValue = 1, allocationSize = 1)
@Audited(withModifiedFlag = true)
public class ExternalCustomer extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -3981219923364485410L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXTERNAL_CUSTOMER_SEQ_GENERATOR")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sop_disclosure_request_form_id", referencedColumnName = "id")
    private SOPDisclosureRequestForm sopDisclosureRequestForm;

    @Column(name = "name", columnDefinition = "nvarchar(50)")
    private String name;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "role", columnDefinition = "nvarchar(50)")
    private String role;

    @Transient
    private Integer size;

    public ExternalCustomer(SOPDisclosureRequestForm sopDisclosureRequestForm, ExternalCustomer externalCustomer) {
        this.sopDisclosureRequestForm = sopDisclosureRequestForm;
        this.name = externalCustomer.getName();
        this.email = externalCustomer.getEmail();
        this.role = externalCustomer.getRole();
    }

    @Builder
    public ExternalCustomer(Integer id) {
        this.id = id;
    }
}
