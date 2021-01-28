package com.cauh.iso.domain;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.ByteArrayInputStream;
import java.io.Serializable;

@Data
@Entity
@Table(name = "s_iso_training_certification", uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "user_id"})})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ISOTrainingCertification extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1329010014950482889L;

    //수료증 채번 ( 분류 + 년도 + 숫자 )
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Account user;

    @ManyToOne
    @JoinColumn(name = "iso_id", referencedColumnName = "id")
    private ISO iso;

    @ManyToOne
    @JoinColumn(name = "iso_training_log_id", referencedColumnName = "id")
    private ISOTrainingLog isoTrainingLog;

    @Column(name = "cert_html", columnDefinition = "nvarchar(MAX)")
    String certHtml;

    @Column(name = "file_name", columnDefinition = "nvarchar(255)")
    String fileName;

    @Transient
    private String printDate;

    @Transient
    private ByteArrayInputStream sign;


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
