package com.cauh.iso.domain;

import com.cauh.common.entity.Account;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Data
@Entity
@Table(name = "s_iso_training_certification")
public class ISOTrainingCertification {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Account user;

    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Account certManager;

//    // 강사 정보
//    private String cerManagerText1;
//
//    // 대표자 정보
//    private String cerManagerText2;
//
//    // 1: 기본 수료증
//    @ColumnDefault("0")
//    private int isActive = 0;
}
