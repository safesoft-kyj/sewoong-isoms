package com.cauh.iso.domain;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.BaseEntity;
import com.cauh.iso.domain.constant.ApprovalLineType;
import com.cauh.iso.domain.constant.ApprovalStatus;
import com.cauh.iso.utils.DateUtils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.Audited;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_approval_line",
        indexes = {
            @Index(columnList = "line_type,username")
        },
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"id", "approval_id"}),
            @UniqueConstraint(columnNames = {"id", "line_type", "username"})
        }
)
@Slf4j
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SequenceGenerator(name = "APPROVAL_LINE_SEQ_GENERATOR", sequenceName = "SEQ_APPROVAL_LINE", initialValue = 1, allocationSize = 1)
@Audited(withModifiedFlag = true)
public class ApprovalLine extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 4297913588105294338L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPROVAL_LINE_SEQ_GENERATOR")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_id", referencedColumnName = "id")
    private Approval approval;

    @Column(name = "line_type", length = 25, nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private ApprovalLineType lineType;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    @Column(name = "comments", columnDefinition = "nvarchar(max)")
    private String comments;

    @Column(name = "username", nullable = false, updatable = false)
    private String username;

    @Column(name = "base64signature", columnDefinition = "varchar(max)")
    private String base64signature;

    @Column(name = "display_name", columnDefinition = "nvarchar(50)", nullable = false, updatable = false)
    private String displayName;

    //다음 결재자
    @Transient
    private Integer nextId;

    @Transient
    private Account user;


    public ApprovalLine(Approval approval, ApprovalLine approvalLine) {
        this.approval = approval;
        this.lineType = approvalLine.getLineType();
        this.comments = approvalLine.getComments();
        this.username = approvalLine.getUsername();
        this.base64signature = approvalLine.getBase64signature();
        this.displayName = approvalLine.getDisplayName();
    }

    @Builder
    public ApprovalLine(Integer id, Approval approval, ApprovalLineType lineType, ApprovalStatus status, String comments, String username, String displayName, String base64signature) {
        this.id = id;
        this.approval = approval;
        this.lineType = lineType;
        this.status = status;
        this.comments = comments;
        this.username = username;
        this.displayName = displayName;
        this.base64signature = base64signature;
    }

    public String getStrDate() {
        if(ObjectUtils.isEmpty(lastModifiedDate)) {
            return DateUtils.format(createdDate, "dd-MMM-yyyy").toUpperCase();
        } else {
            return DateUtils.format(lastModifiedDate, "dd-MMM-yyyy").toUpperCase();
        }
    }
}
