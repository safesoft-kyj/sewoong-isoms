package com.dtnsm.esop.repository;

import com.dtnsm.common.entity.QAccount;
import com.dtnsm.common.entity.Account;
import com.dtnsm.common.entity.QUserJobDescription;
import com.dtnsm.common.entity.UserJobDescription;
import com.dtnsm.common.entity.constant.JobDescriptionStatus;
import com.dtnsm.common.repository.UserRepository;
import com.dtnsm.esop.domain.*;
import com.dtnsm.esop.domain.constant.*;
import com.dtnsm.esop.utils.DateUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.SQLExpressions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.dtnsm.esop.domain.QSOPTrainingMatrix.sOPTrainingMatrix;
import static com.querydsl.sql.SQLExpressions.min;

@Slf4j
@RequiredArgsConstructor
public class SOPTrainingMatrixRepositoryImpl implements SOPTrainingMatrixRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private final UserRepository userRepository;

    public Page<MyTrainingMatrix> getMyTrainingMatrix(Pageable pageable, List<UserJobDescription> userJobDescriptions) {

        BooleanBuilder builder = new BooleanBuilder();
        if(ObjectUtils.isEmpty(userJobDescriptions)) {
            builder.and(sOPTrainingMatrix.trainingAll.eq(true));
        } else {
            List<Integer> jobDescriptionIds = userJobDescriptions.stream().map(d -> d.getJobDescriptionVersion().getJobDescription().getId()).collect(Collectors.toList());
            builder.and(sOPTrainingMatrix.trainingAll.eq(true).or(sOPTrainingMatrix.jobDescription.id.in(jobDescriptionIds)));
        }

        QueryResults<MyTrainingMatrix> results = queryFactory.selectDistinct(Projections.constructor(MyTrainingMatrix.class, sOPTrainingMatrix.documentVersion.document, sOPTrainingMatrix.documentVersion))
                .from(sOPTrainingMatrix)
                .where(sOPTrainingMatrix.documentVersion.document.type.eq(DocumentType.SOP))
                .where(sOPTrainingMatrix.documentVersion.status.eq(DocumentStatus.EFFECTIVE))
                .where(builder)
                .orderBy(sOPTrainingMatrix.documentVersion.document.docId.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    public List<MyTrainingMatrix> getMyTrainingMatrix(List<UserJobDescription> userJobDescriptions) {
        if(!ObjectUtils.isEmpty(userJobDescriptions)) {
            List<Integer> jobDescriptionIds = userJobDescriptions.stream().map(d -> d.getJobDescriptionVersion().getJobDescription().getId()).collect(Collectors.toList());
            List<MyTrainingMatrix> results = queryFactory.selectDistinct(Projections.constructor(MyTrainingMatrix.class, sOPTrainingMatrix.documentVersion.document, sOPTrainingMatrix.documentVersion))
                    .from(sOPTrainingMatrix)
                    .where(sOPTrainingMatrix.documentVersion.document.type.eq(DocumentType.SOP))
                    .where(sOPTrainingMatrix.documentVersion.status.eq(DocumentStatus.EFFECTIVE))
                    .where(sOPTrainingMatrix.trainingAll.eq(true).or(sOPTrainingMatrix.jobDescription.id.in(jobDescriptionIds)))
                    .orderBy(sOPTrainingMatrix.documentVersion.document.docId.asc())
                    .fetch();

            return results;
        } else {
            return Collections.emptyList();
        }
    }

    public Page<MyTraining> getMyTraining(TrainingRequirement requirement, Pageable pageable, Account user) {
        if(requirement == TrainingRequirement.optional) {
            List<Integer> jobDescriptionIds = new ArrayList<>();
            if(ObjectUtils.isEmpty(user.getJobDescriptions()) == false) {
                jobDescriptionIds.addAll(user.getJobDescriptions().stream().map(d -> d.getJobDescriptionVersion().getJobDescription().getId()).collect(Collectors.toList()));
            }
            /**
             * Case 확인
             * 1. 입사 8주 이내..
             * 2. 입사 8주 이상..
             */
            QTrainingPeriod qTrainingPeriod = QTrainingPeriod.trainingPeriod;
            QTrainingLog qTrainingLog = QTrainingLog.trainingLog;
            QDocumentVersion qDocumentVersion = QDocumentVersion.documentVersion;

            BooleanBuilder builder = new BooleanBuilder();

            if (requirement == TrainingRequirement.mandatory) {
                builder.and(sOPTrainingMatrix.trainingAll.eq(true));
                if(ObjectUtils.isEmpty(jobDescriptionIds) == false) {
                    builder.or(sOPTrainingMatrix.jobDescription.id.in(jobDescriptionIds));
                }
            } else {//optional
                BooleanBuilder optionalBuilder = new BooleanBuilder();
                optionalBuilder.and(sOPTrainingMatrix.trainingAll.eq(true));
                if(ObjectUtils.isEmpty(jobDescriptionIds) == false) {
                    optionalBuilder.or(sOPTrainingMatrix.jobDescription.id.in(jobDescriptionIds));
                }
                builder.and(sOPTrainingMatrix.documentVersion.id.notIn(
                        JPAExpressions.selectDistinct(sOPTrainingMatrix.documentVersion.id)
                                .from(sOPTrainingMatrix)
                                .where(optionalBuilder)
                        )
                );
            }

            QueryResults<MyTraining> results = queryFactory
                    .selectDistinct(Projections.constructor(MyTraining.class,
                            sOPTrainingMatrix.documentVersion.document,
                            sOPTrainingMatrix.documentVersion,
                            qTrainingPeriod,
                            qTrainingLog,
                            qTrainingPeriod.startDate,
                            qTrainingPeriod.endDate)
                    )
                    .from(sOPTrainingMatrix)
                    .where(builder)
                    .join(qTrainingPeriod)
                    .on(sOPTrainingMatrix.documentVersion.id.eq(qTrainingPeriod.documentVersion.id)
                            .and(qTrainingPeriod.trainingType.eq(TrainingType.SELF))
                            .and(qTrainingPeriod.startDate.loe(new Date()))
                            .or(sOPTrainingMatrix.documentVersion.id.eq(qTrainingPeriod.documentVersion.id)
                                    .and(qTrainingPeriod.trainingType.eq(TrainingType.REFRESH))
                                    .and(qTrainingPeriod.startDate.goe(user.getIndate())))
                    )
                    .leftJoin(qTrainingLog).on(qTrainingPeriod.id.eq(qTrainingLog.trainingPeriod.id).and(qTrainingLog.user.id.eq(user.getId())).and(qTrainingLog.reportStatus.ne(DeviationReportStatus.REJECTED)))
                    .where(sOPTrainingMatrix.documentVersion.document.type.eq(DocumentType.SOP))
                    .where(sOPTrainingMatrix.documentVersion.status.in(DocumentStatus.EFFECTIVE, DocumentStatus.APPROVED))
                    .where(sOPTrainingMatrix.documentVersion.id.notIn(
                            JPAExpressions.selectDistinct(qDocumentVersion.parentVersion.id)
                                    .from(qDocumentVersion)
                                    .where(qDocumentVersion.document.type.eq(DocumentType.SOP)
                                            .and(qDocumentVersion.status.eq(DocumentStatus.APPROVED))
                                            .and(qDocumentVersion.parentVersion.isNotNull())
                                            .and(qDocumentVersion.effectiveDate.loe(DateUtils.addDay(user.getIndate(), 56))))
                    ))
                    .orderBy(qTrainingLog.completeDate.asc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetchResults();

            return new PageImpl<>(results.getResults(), pageable, results.getTotal());
        } else {
            log.info("@MyTraining(Mandatory) : {}", user.getUsername());
            BooleanBuilder docStatus = new BooleanBuilder();
            QDocumentVersion qDocumentVersion = QDocumentVersion.documentVersion;
            docStatus.and(qDocumentVersion.status.in(DocumentStatus.APPROVED, DocumentStatus.EFFECTIVE));
            return getMyMandatoryTrainingList(user.getDeptCode(), user.getTeamCode(), user.getId(), null, null, pageable, docStatus);
        }
    }

    /**
     * 필수 트레이닝 이며, Training을 완료한 경우는 제외하고 리스트를 가져온다.
     */
    private JPAQuery getMyMandatoryTrainingListJpaQuery(String deptCode, String teamCode, Integer userId, String docId, Account loginUser, BooleanBuilder docStatus) {
        QUserJobDescription qUserJobDescription = QUserJobDescription.userJobDescription;
        QDocumentVersion qDocumentVersion = QDocumentVersion.documentVersion;
        QTrainingPeriod qTrainingPeriod = QTrainingPeriod.trainingPeriod;
        QAccount qUser = QAccount.account;
        QTrainingLog qTrainingLog = QTrainingLog.trainingLog;
        QUserMatrix qUserMatrix = QUserMatrix.userMatrix;
        //
//        if(ObjectUtils.isEmpty(employees)) {
//            User user = userRepository.findById(userId).get();
//            employees.add(user.getUsername());
//        }

//        User user = userRepository.findById(userId).get();
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qUser.enabled.eq(true));
        builder.and(qUser.indate.isNotNull());
        builder.and(qUser.comNum.isNotNull());
//        if(ObjectUtils.isEmpty(loginUser) == false) builder.and(qUser.id.ne(loginUser.getId()));
        if (!StringUtils.isEmpty(deptCode)) {
            builder.and(qUser.deptCode.eq(deptCode));
        }
        if (!StringUtils.isEmpty(teamCode)) {
            builder.and(qUser.teamCode.eq(teamCode));
        }
        if(!ObjectUtils.isEmpty(userId)) {
            builder.and(qUser.id.eq(userId));
        }

        if(!StringUtils.isEmpty(docId)) {
            log.info("@DocId : {}", docId);
            if(!docId.toUpperCase().startsWith("SOP-")) {
                docId = "SOP-" + docId;
            }
            builder.and(qDocumentVersion.document.docId.like(docId.toUpperCase() + "%"));
        }

        JPAQuery<MyTraining> jpaQuery = queryFactory.select(Projections.constructor(MyTraining.class,
                qUser,
                qDocumentVersion.document,
                qDocumentVersion,
                qTrainingPeriod,
                qTrainingLog,
                qTrainingPeriod.startDate,
                qTrainingPeriod.endDate,
                ExpressionUtils.as(JPAExpressions.select(min(qUserJobDescription.assignDate))
                        .from(qUserJobDescription).where(qUserJobDescription.username.eq(qUser.username).and(qUserJobDescription.status.eq(JobDescriptionStatus.APPROVED))), "assignDate")
        ))
        .from(qUser)
//        .leftJoin(qUserJobDescription).on(qUser.username.eq(qUserJobDescription.username).and(qUserJobDescription.status.in(statusList)))
//        .join(sOPTrainingMatrix).on(sOPTrainingMatrix.trainingAll.eq(true).or(qUserJobDescription.jobDescriptionVersion.jobDescription.id.eq(sOPTrainingMatrix.jobDescription.id)))

//                .join(sOPTrainingMatrix)
//                .on(sOPTrainingMatrix.id.in(JPAExpressions.select(SQLExpressions.max(sOPTrainingMatrix.id))
//                        .from(sOPTrainingMatrix)
//                        .where(sOPTrainingMatrix.trainingAll.eq(true).or(sOPTrainingMatrix.jobDescription.id.in(JPAExpressions
//                                .select(qUserJobDescription.jobDescriptionVersion.jobDescription.id)
//                                .from(qUserJobDescription)
//                                .where(qUserJobDescription.username.eq(qUser.username))
////                                .where(qUserJobDescription.username.in(employees))
//                        ))).groupBy(sOPTrainingMatrix.documentVersion.id)
//                ))
                .join(qUserMatrix).on(qUser.id.eq(qUserMatrix.userId))
                .join(qTrainingPeriod).on(qUserMatrix.trainingPeriodId.eq(qTrainingPeriod.id))
                .join(qDocumentVersion).on(qTrainingPeriod.documentVersion.id.eq(qDocumentVersion.id).and(docStatus))
//        .on(qTrainingPeriod.documentVersion.id.eq(qDocumentVersion.id))
//        .on(
//            qTrainingPeriod.trainingType.eq(TrainingType.SELF).and(qTrainingPeriod.startDate.loe(new Date()))
//            .or(qTrainingPeriod.trainingType.eq(TrainingType.REFRESH).and(qTrainingPeriod.startDate.goe(qUser.inDate)))
//            .or(qTrainingPeriod.trainingType.eq(TrainingType.RE_TRAINING).and(qTrainingPeriod.retrainingUser.id.eq(qUser.id)))
//        )
        .leftJoin(qTrainingLog)
                .on(qTrainingLog.trainingPeriod.id.eq(qUserMatrix.trainingPeriodId).and(qTrainingLog.user.id.eq(qUserMatrix.userId)))
                .on(qTrainingLog.id.in(
                        JPAExpressions.select(qTrainingLog.id).from(qTrainingLog)
                        .where(qTrainingLog.reportStatus.notIn(DeviationReportStatus.REJECTED, DeviationReportStatus.DELETED))))
        .where(builder)
        .where(qTrainingLog.status.isNull().or(qTrainingLog.status.ne(TrainingStatus.COMPLETED)))
        .orderBy(qDocumentVersion.document.docId.asc());

        return jpaQuery;
    }

    private JPAQuery getTrainingListJpaQuery(String deptCode, String teamCode, Integer userId, String docId, Account loginUser, BooleanBuilder docStatus, List<JobDescriptionStatus> statusList) {
        QUserJobDescription qUserJobDescription = QUserJobDescription.userJobDescription;
        QDocumentVersion qDocumentVersion = QDocumentVersion.documentVersion;
        QTrainingPeriod qTrainingPeriod = QTrainingPeriod.trainingPeriod;
        QAccount qUser = QAccount.account;
        QTrainingLog qTrainingLog = QTrainingLog.trainingLog;

//        User user = userRepository.findById(userId).get();
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qUser.enabled.eq(true));
        builder.and(qUser.indate.isNotNull());
        builder.and(qUser.comNum.isNotNull());
        if(ObjectUtils.isEmpty(loginUser) == false) builder.and(qUser.id.ne(loginUser.getId()));
        if (!StringUtils.isEmpty(deptCode)) {
            builder.and(qUser.deptCode.eq(deptCode));
        }
        if (!StringUtils.isEmpty(teamCode)) {
            builder.and(qUser.teamCode.eq(teamCode));
        }
        if(!ObjectUtils.isEmpty(userId)) {
            builder.and(qUser.id.eq(userId));
        }

        if(!StringUtils.isEmpty(docId)) {
            log.info("@DocId : {}", docId);
            if(!docId.toUpperCase().startsWith("SOP-")) {
                docId = "SOP-" + docId;
            }
            builder.and(sOPTrainingMatrix.documentVersion.document.docId.like(docId.toUpperCase() + "%"));
        }

        JPAQuery<MyTraining> jpaQuery = queryFactory.selectDistinct(Projections.constructor(MyTraining.class,
                    qUser,
                    sOPTrainingMatrix.documentVersion.document,
                    sOPTrainingMatrix.documentVersion,
                    qTrainingPeriod,
                    qTrainingLog,
                    qTrainingPeriod.startDate,
                    qTrainingPeriod.endDate,
                    ExpressionUtils.as(JPAExpressions.select(min(qUserJobDescription.assignDate))
                    .from(qUserJobDescription).where(qUserJobDescription.username.eq(qUser.username).and(qUserJobDescription.status.eq(JobDescriptionStatus.APPROVED))), "assignDate")
                ))
                .from(qUser)
                .leftJoin(qUserJobDescription).on(qUser.username.eq(qUserJobDescription.username).and(qUserJobDescription.status.in(statusList)))
                .leftJoin(sOPTrainingMatrix).on(sOPTrainingMatrix.trainingAll.eq(true).or(qUserJobDescription.jobDescriptionVersion.jobDescription.id.eq(sOPTrainingMatrix.jobDescription.id)))
                .join(qDocumentVersion).on(sOPTrainingMatrix.documentVersion.id.eq(qDocumentVersion.id).and(docStatus))
                .join(qTrainingPeriod).on(qTrainingPeriod.documentVersion.id.eq(qDocumentVersion.id))
//                        .and(qTrainingPeriod.trainingType.eq(TrainingType.SELF))
//                        .and(qTrainingPeriod.startDate.loe(new Date()))
//                        .or(sOPTrainingMatrix.documentVersion.id.eq(qTrainingPeriod.documentVersion.id)
//                                .and(qTrainingPeriod.trainingType.eq(TrainingType.REFRESH))
//                                .and(qTrainingPeriod.startDate.goe(qUser.inDate))))

                .on(
                        qTrainingPeriod.trainingType.eq(TrainingType.SELF).and(qTrainingPeriod.startDate.loe(new Date()))
                                .or(qTrainingPeriod.trainingType.eq(TrainingType.REFRESH).and(qTrainingPeriod.startDate.goe(qUser.indate)))
                                .or(qTrainingPeriod.trainingType.eq(TrainingType.RE_TRAINING).and(qTrainingPeriod.retrainingUser.eq(qUser)))
                )
                .leftJoin(qTrainingLog).on(qTrainingLog.trainingPeriod.id.eq(qTrainingPeriod.id).and(qTrainingLog.user.id.eq(qUser.id)))
                .where(qUser.training.eq(true))
                .where(builder)
                .orderBy(sOPTrainingMatrix.documentVersion.effectiveDate.desc());

        return jpaQuery;
    }

    public Page<MyTraining> getMyMandatoryTrainingList(String deptCode, String teamCode, Integer userId, String docId, Account user, Pageable pageable, BooleanBuilder docStatus) {

        JPAQuery<MyTraining> jpaQuery = getMyMandatoryTrainingListJpaQuery(deptCode, teamCode, userId, docId, user, docStatus);
        QueryResults<MyTraining> results = jpaQuery.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    /**
     * 필수 트레이닝 목록
     * @param deptCode
     * @param teamCode
     * @param userId
     * @param docId
     * @param user
     * @param pageable
     * @param docStatus
     * @return
     */
    public Page<MyTraining> getTrainingList(String deptCode, String teamCode, Integer userId, String docId, Account user, Pageable pageable, BooleanBuilder docStatus) {

//        JPAQuery<MyTraining> jpaQuery = getTrainingListJpaQuery(deptCode, teamCode, userId, docId, user, docStatus, statusList);
//        QueryResults<MyTraining> results = jpaQuery.offset(pageable.getOffset())
//        .limit(pageable.getPageSize())
//                .fetchResults();
        JPAQuery<MyTraining> jpaQuery = getMyMandatoryTrainingListJpaQuery(deptCode, teamCode, userId, docId, user, docStatus);
        QueryResults<MyTraining> results = jpaQuery.offset(pageable.getOffset())
        .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    public List<MyTraining> getDownloadTrainingList(String deptCode, String teamCode, Integer userId, String docId, Account user) {
        BooleanBuilder docStatus = new BooleanBuilder();
        QDocumentVersion qDocumentVersion = QDocumentVersion.documentVersion;
        docStatus.and(qDocumentVersion.status.in(DocumentStatus.APPROVED, DocumentStatus.EFFECTIVE));

        JPAQuery<MyTraining> jpaQuery = getMyMandatoryTrainingListJpaQuery(deptCode, teamCode, userId, docId, user, docStatus);
//        BooleanBuilder docStatus = new BooleanBuilder();
//        QDocumentVersion qDocumentVersion = QDocumentVersion.documentVersion;
//        docStatus.and(qDocumentVersion.status.notIn(DocumentStatus.REVISION, DocumentStatus.DEVELOPMENT));
//
//        JPAQuery<MyTraining> jpaQuery = getTrainingListJpaQuery(deptCode, teamCode, userId, docId, user, docStatus, statusList);
        return jpaQuery.fetch();
    }
}
