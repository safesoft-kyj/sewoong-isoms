package com.cauh.iso.domain;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.BaseEntity;
import com.cauh.iso.domain.constant.DocumentAccessType;
import com.cauh.iso.domain.constant.TrainingLogType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "s_training_access_log")
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class TrainingAccessLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1298750697837485961L;
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", length = 40)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Account user;

    @Column(name = "training_log_type")
    @Enumerated(EnumType.STRING)
    private TrainingLogType trainingLogType;

    @Column(name = "access_type")
    @Enumerated(EnumType.STRING)
    private DocumentAccessType accessType;

}
