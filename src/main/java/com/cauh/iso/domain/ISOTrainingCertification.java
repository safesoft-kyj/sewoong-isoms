package com.cauh.iso.domain;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "s_iso_training_certification")
public class ISOTrainingCertification extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1329010014950482889L;

    //수료증 채번 ( 분류 + 년도 + 숫자 )
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Account user;


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
