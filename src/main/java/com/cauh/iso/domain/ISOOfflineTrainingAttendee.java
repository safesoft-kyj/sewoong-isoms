package com.cauh.iso.domain;

import com.cauh.common.entity.BaseEntity;
import com.cauh.common.entity.Account;
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
@Table(name = "s_offline_training_iso_attendee", uniqueConstraints = {@UniqueConstraint(columnNames = {"iso_offline_training_id", "account_id"})})
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "OFF_TRAINING_ISO_ATTENDEE_SEQ_GENERATOR", sequenceName = "SEQ_OFF_TRAINING_ATTENDEE", initialValue = 1, allocationSize = 1)
@Audited(withModifiedFlag = true)
public class ISOOfflineTrainingAttendee extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 2445370839488735764L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OFF_TRAINING_ISO_ATTENDEE_SEQ_GENERATOR")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "iso_offline_training_id", referencedColumnName = "id")
    private ISOOfflineTraining ISOOfflineTraining;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;
}
