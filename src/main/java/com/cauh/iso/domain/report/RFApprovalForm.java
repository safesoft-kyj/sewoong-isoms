package com.cauh.iso.domain.report;

import com.cauh.common.entity.BaseEntity;
import com.cauh.iso.domain.Approval;
import com.cauh.iso.domain.DocumentVersion;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_rf_approval_form", uniqueConstraints = @UniqueConstraint(columnNames = {"id", "approval_id"}))
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "RF_APPROVAL_SEQ_GENERATOR", sequenceName = "SEQ_RF_APPROVAL_FORM", initialValue = 1, allocationSize = 1)
public class RFApprovalForm extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -2434920234671478604L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RF_APPROVAL_SEQ_GENERATOR")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "approval_id", referencedColumnName = "id")
    private Approval approval;

    @ManyToOne
    @JoinColumn(name = "superseded_version_id", referencedColumnName = "id")
    private DocumentVersion supersededVersion;

    @Transient
    private String rdNo;

    @Transient
    private String title;

    @Column(name = "version", length = 5, nullable = false)
    private String version;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "effective_date")
    private Date effectiveDate;

    @Column(name = "description", columnDefinition = "nvarchar(500)")
    private String description;
}
