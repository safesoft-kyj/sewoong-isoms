package com.dtnsm.esop.domain.report;

import com.dtnsm.common.entity.Account;
import com.dtnsm.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_disclosure_digital_binder")
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "SOP_DISCLOSURE_DB_GENERATOR", sequenceName = "SEQ_DISCLOSURE_DB", initialValue = 1, allocationSize = 1)
public class DisclosureDigitalBinder extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -6407142975311925813L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOP_DISCLOSURE_DB_GENERATOR")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "request_form_id", referencedColumnName = "id")
    private SOPDisclosureRequestForm sopDisclosureRequestForm;

//    @Column(name = "username", length = 50, unique = true)
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Account user;

    public DisclosureDigitalBinder(SOPDisclosureRequestForm sopDisclosureRequestForm, DisclosureDigitalBinder disclosureDigitalBinder) {
        this.sopDisclosureRequestForm = sopDisclosureRequestForm;
        this.user = disclosureDigitalBinder.getUser();
    }
}
