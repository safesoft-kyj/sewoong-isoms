package com.cauh.iso.domain;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "s_agreements_withdrawal")
@SequenceGenerator(name = "AGREEMENT_WITHDRAWAL_SEQ_GENERATOR", sequenceName = "SEQ_AGREEMENT_WITHDRAWAL", initialValue = 1, allocationSize = 1)
@Slf4j
@NoArgsConstructor
public class AgreementsWithdrawal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AGREEMENT_WITHDRAWAL_SEQ_GENERATOR")
    private Integer id;

    //@Column(name = "email", nullable = false, length = 64, updatable = false)
    //private String email;

    @ManyToOne
    @JoinColumn(name ="user_id", referencedColumnName = "id")
    private Account user;

    @Column(name = "withdrawal_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date withdrawalDate;

    @Transient
    private String email;

}
