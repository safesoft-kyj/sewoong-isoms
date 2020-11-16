package com.cauh.esop.repository;

import com.cauh.common.entity.Account;
import com.cauh.esop.domain.Approval;
import com.cauh.esop.domain.ApprovalLine;
import com.cauh.esop.domain.QApproval;
import com.cauh.esop.domain.QApprovalLine;
import com.cauh.esop.domain.constant.ApprovalLineType;
import com.cauh.esop.domain.constant.ApprovalStatus;
import com.cauh.esop.domain.constant.ReportType;
import com.cauh.esop.domain.report.QSOPDeviationReport;
import com.cauh.esop.domain.report.QSOPDisclosureRequestForm;
import com.cauh.esop.domain.report.QSOPWaiverApprovalForm;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ApprovalLineRepositoryImpl implements ApprovalLineRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public Page<Approval> findAll(ApprovalLineType lineType, ApprovalStatus status, Account user, Pageable pageable) {
        log.info("@@ 전자결재 리스트 조회 LineType : {}, approvalStatus : {}, user : {}", lineType, status, user);
        BooleanBuilder builder = new BooleanBuilder();
        QApproval approval = QApproval.approval;
        QApprovalLine approvalLine = QApprovalLine.approvalLine;
        if(ObjectUtils.isEmpty(lineType) == false) {
            builder.and(approvalLine.lineType.eq(lineType));
        }
        if(ObjectUtils.isEmpty(user) == false) {
            builder.and(approvalLine.username.eq(user.getUsername()));
        }
        if(!ObjectUtils.isEmpty(status)) {
            if(status != ApprovalStatus.deleted) {
                if (lineType == ApprovalLineType.requester) {
                    builder.and(approvalLine.approval.status.eq(status));
                } else {//검토함, 승인함 인경우
                    builder.and(approvalLine.status.eq(status));
                }
            }
        } else if(lineType != ApprovalLineType.requester) {
            builder.and(approvalLine.status.in(ApprovalStatus.request, ApprovalStatus.approved, ApprovalStatus.rejected));//검토, 승인함 인 경우 요청, 승인, 반려 상태만 가져온다.
        }
        //삭제건 조회 여부
        builder.and(approvalLine.approval.deleted.eq(status == ApprovalStatus.deleted ? true : false));

        List<Integer> approvalIds = queryFactory.selectDistinct(approvalLine.approval.id)
                .from(approvalLine)
                .where(builder)
//                .orderBy(approvalLine.id.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
                .fetch();

        log.info("Pagination offset : {}, pageSize : {}", pageable.getOffset(), pageable.getPageSize());

        log.info("<== approvalId List : {}", approvalIds);

        BooleanBuilder appBuilder = new BooleanBuilder();
        //요기서 부터 확인 !!(ㅇㅇ)
        appBuilder.and(approval.id.in(approvalIds));
        QueryResults<Approval> results = queryFactory
                .select(Projections.constructor(Approval.class, approval.id, approval.type, approval.status, approval.createdBy, approval.createdDate, approval.lastModifiedBy, approval.lastModifiedDate))
                .from(approval)
                .where(appBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(approval.id.desc())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    public Page<Approval> findAllAdmin(ApprovalStatus status, ReportType reportType, Pageable pageable) {
        log.info("@@ 전자결재(관리자) 리스트 조회 approvalStatus : {}, reportType = {}", status, reportType);
        BooleanBuilder builder = new BooleanBuilder();
        QApproval approval = QApproval.approval;
        QSOPWaiverApprovalForm qsopWaiverApprovalForm = QSOPWaiverApprovalForm.sOPWaiverApprovalForm;
        QSOPDisclosureRequestForm qsopDisclosureRequestForm = QSOPDisclosureRequestForm.sOPDisclosureRequestForm;
        QSOPDeviationReport qsopDeviationReport = QSOPDeviationReport.sOPDeviationReport;

        if(!ObjectUtils.isEmpty(status)) {
            builder.and(approval.status.eq(status));
        } else {
            builder.and(approval.status.in(Arrays.asList(ApprovalStatus.request, ApprovalStatus.progress, ApprovalStatus.approved, ApprovalStatus.rejected)));
        }
        if(!ObjectUtils.isEmpty(reportType)) {
            builder.and(approval.type.eq(reportType));
        }
        builder.and(approval.deleted.eq(false));
        QueryResults<Approval> results = queryFactory
                .select(Projections.constructor(Approval.class,
                        approval.id, approval.type, approval.status, approval.createdBy,
                        approval.createdDate, approval.lastModifiedBy, approval.lastModifiedDate,
                        new CaseBuilder()
                        .when(approval.type.eq(ReportType.SOP_Waiver_Approval_Form)).then(qsopWaiverApprovalForm.projectNo)
                        .when(approval.type.eq(ReportType.SOP_Disclosure_Request_Form)).then(qsopDisclosureRequestForm.companyNameOrInstituteName)
                        .when(approval.type.eq(ReportType.SOP_Deviation_Report)).then(qsopDeviationReport.projectNo)
                        .otherwise("").as("keyword")
                        ))
                .from(approval)
                .leftJoin(qsopWaiverApprovalForm).on(approval.id.eq(qsopWaiverApprovalForm.approval.id))
                .leftJoin(qsopDisclosureRequestForm).on(approval.id.eq(qsopDisclosureRequestForm.approval.id))
                .leftJoin(qsopDeviationReport).on(approval.id.eq(qsopDeviationReport.approval.id))
                .where(builder)
                .orderBy(approval.id.desc())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    public long countApproval(ApprovalLineType lineType, Account user) {
        BooleanBuilder builder = new BooleanBuilder();
        QApproval approval = QApproval.approval;
        QApprovalLine approvalLine = QApprovalLine.approvalLine;
        builder.and(approvalLine.lineType.eq(lineType));
        builder.and(approvalLine.status.eq(ApprovalStatus.request));
        builder.and(approvalLine.username.eq(user.getUsername()));
        builder.and(approval.deleted.eq(false));

        return queryFactory.selectDistinct(approval)
                .from(approval)
                .join(approvalLine)
                .on(approvalLine.approval.id.eq(approval.id))
                .where(builder)
                .fetchCount();
    }
}
