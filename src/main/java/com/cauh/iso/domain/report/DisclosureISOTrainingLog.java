package com.cauh.iso.domain.report;

import com.cauh.common.entity.Account;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_disclosure_iso_training_log")
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "ISO_DISCLOSURE_TRAINING_LOG_GENERATOR", sequenceName = "SEQ_ISO_DISCLOSURE_TRAINING_LOG", initialValue = 1, allocationSize = 1)
public class DisclosureISOTrainingLog {

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
