package com.cauh.iso.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_iso_training")
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "ISO_TRAINING_LOG_SEQ_GENERATOR", sequenceName = "SEQ_ISO_TRAINING_LOG", initialValue = 1, allocationSize = 1)
public class ISOTrainingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISO_TRAINING_LOG_SEQ_GENERATOR")
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "iso_offline_training_id", referencedColumnName = "id")
    private ISOOfflineTraining isoOfflineTraining;


    @ManyToOne
    @JoinColumn(name = "iso_id", referencedColumnName = "id")
    private ISO iso;

}
