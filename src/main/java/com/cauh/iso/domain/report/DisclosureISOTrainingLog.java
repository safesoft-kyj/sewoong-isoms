package com.cauh.iso.domain.report;

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

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_disclosure_iso_training_log")
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "ISO_DISCLOSURE_TRAINING_LOG_GENERATOR", sequenceName = "SEQ_ISO_DISCLOSURE_TRAINING_LOG", initialValue = 1, allocationSize = 1)
@Audited(withModifiedFlag = true)
public class DisclosureISOTrainingLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -8495768475311925813L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOP_DISCLOSURE_TRAINING_LOG_GENERATOR")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "request_form_id", referencedColumnName = "id")
    private SOPDisclosureRequestForm sopDisclosureRequestForm;

    //    @Column(name = "username", length = 50, unique = true)
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Account user;

    public DisclosureISOTrainingLog(SOPDisclosureRequestForm sopDisclosureRequestForm, DisclosureISOTrainingLog disclosureISOTrainingLog) {
        this.sopDisclosureRequestForm = sopDisclosureRequestForm;
        this.user = disclosureISOTrainingLog.getUser();
    }


}
