package com.cauh.iso.repository;

import com.cauh.common.entity.*;
import com.cauh.common.entity.constant.JobDescriptionStatus;
import com.cauh.common.repository.UserRepository;
import com.cauh.iso.domain.*;
import com.cauh.iso.domain.constant.*;
import com.cauh.iso.utils.DateUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.cauh.iso.domain.QTrainingMatrix.trainingMatrix;
import static com.querydsl.sql.SQLExpressions.min;

@Slf4j
@RequiredArgsConstructor
public class TrainingMatrixRepositoryImpl implements TrainingMatrixRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private final UserRepository userRepository;

    public Page<MyTrainingMatrix> getMyTrainingMatrix(Pageable pageable, List<UserJobDescription> userJobDescriptions) {

        BooleanBuilder builder = new BooleanBuilder();
        if(ObjectUtils.isEmpty(userJobDescriptions)) {
            builder.and(trainingMatrix.trainingAll.eq(true));
        } else {
            List<Integer> roleAccountIds = userJobDescriptions.stream().map(d -> d.getJobDescription().getId()).collect(Collectors.toList());
            builder.and(trainingMatrix.trainingAll.eq(true).or(trainingMatrix.jobDescription.id.in(roleAccountIds)));
        }

        QueryResults<MyTrainingMatrix> results = queryFactory.selectDistinct(Projections.constructor(MyTrainingMatrix.class, trainingMatrix.documentVersion.document, trainingMatrix.documentVersion))
                .from(trainingMatrix)
                .where(trainingMatrix.documentVersion.document.type.eq(DocumentType.SOP))
                .where(trainingMatrix.documentVersion.status.eq(DocumentStatus.EFFECTIVE))
                .where(builder)
                .orderBy(trainingMatrix.documentVersion.document.docId.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    public List<MyTrainingMatrix> getMyTrainingMatrix(List<UserJobDescription> userJobDescriptions) {
        if(!ObjectUtils.isEmpty(userJobDescriptions)) {
            List<Integer> jobDescriptionIds = userJobDescriptions.stream().map(d -> d.getJobDescription().getId()).collect(Collectors.toList());
            List<MyTrainingMatrix> results = queryFactory.selectDistinct(Projections.constructor(MyTrainingMatrix.class, trainingMatrix.documentVersion.document, trainingMatrix.documentVersion))
                    .from(trainingMatrix)
                    .where(trainingMatrix.documentVersion.document.type.eq(DocumentType.SOP))
                    .where(trainingMatrix.documentVersion.status.eq(DocumentStatus.EFFECTIVE))
                    .where(trainingMatrix.trainingAll.eq(true).or(trainingMatrix.jobDescription.id.in(jobDescriptionIds)))
                    .orderBy(trainingMatrix.documentVersion.document.docId.asc())
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
                jobDescriptionIds.addAll(user.getJobDescriptions().stream().map(d -> d.getJobDescription().getId()).collect(Collectors.toList()));
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
                builder.and(trainingMatrix.trainingAll.eq(true));
                if(ObjectUtils.isEmpty(jobDescriptionIds) == false) {
                    //수정 필요
//                    builder.or(trainingMatrix.jobDescription.id.in(jobDescriptionIds));
                }
            } else {//optional
                BooleanBuilder optionalBuilder = new BooleanBuilder();
                optionalBuilder.and(trainingMatrix.trainingAll.eq(true));
                if(ObjectUtils.isEmpty(jobDescriptionIds) == false) {
                    //수정 필요
//                    optionalBuilder.or(trainingMatrix.jobDescription.id.in(jobDescriptionIds));
                }
                builder.and(trainingMatrix.documentVersion.id.notIn(
                        JPAExpressions.selectDistinct(trainingMatrix.documentVersion.id)
                                .from(trainingMatrix)
                                .where(optionalBuilder)
                        )
                );
            }

            QueryResults<MyTraining> results = queryFactory
                    .selectDistinct(Projections.constructor(MyTraining.class,
                            trainingMatrix.documentVersion.document,
                            trainingMatrix.documentVersion,
                            qTrainingPeriod,
                            qTrainingLog,
                            qTrainingPeriod.startDate,
                            qTrainingPeriod.endDate)
                    )
                    .from(trainingMatrix)
                    .where(builder)
                    .join(qTrainingPeriod)
                    .on(trainingMatrix.documentVersion.id.eq(qTrainingPeriod.documentVersion.id)
                            .and(qTrainingPeriod.trainingType.eq(TrainingType.SELF))
                            .and(qTrainingPeriod.startDate.loe(new Date()))
                            .or(trainingMatrix.documentVersion.id.eq(qTrainingPeriod.documentVersion.id)
                                    .and(qTrainingPeriod.trainingType.eq(TrainingType.REFRESH))
                                    .and(qTrainingPeriod.startDate.goe(user.getIndate())))
                    )
                    .leftJoin(qTrainingLog).on(qTrainingPeriod.id.eq(qTrainingLog.trainingPeriod.id).and(qTrainingLog.user.id.eq(user.getId())).and(qTrainingLog.reportStatus.ne(DeviationReportStatus.REJECTED)))
                    .where(trainingMatrix.documentVersion.document.type.eq(DocumentType.SOP))
                    .where(trainingMatrix.documentVersion.status.in(DocumentStatus.EFFECTIVE, DocumentStatus.APPROVED))
                    .where(trainingMatrix.documentVersion.id.notIn(
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
        builder.and(qUser.empNo.isNotNull());
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
                        .from(qUserJobDescription).where(qUserJobDescription.user.username.eq(qUser.username).and(qUserJobDescription.status.eq(JobDescriptionStatus.APPROVED))), "assignDate")
        ))
        .from(qUser)
//        .leftJoin(qUserJobDescription).on(qUser.username.eq(qUserJobDescription.username).and(qUserJobDescription.status.in(statusList)))
//        .join(trainingMatrix).on(trainingMatrix.trainingAll.eq(true).or(qUserJobDescription.jobDescriptionVersion.jobDescription.id.eq(trainingMatrix.jobDescription.id)))

//                .join(trainingMatrix)
//                .on(trainingMatrix.id.in(JPAExpressions.select(SQLExpressions.max(trainingMatrix.id))
//                        .from(trainingMatrix)
//                        .where(trainingMatrix.trainingAll.eq(true).or(trainingMatrix.jobDescription.id.in(JPAExpressions
//                                .select(qUserJobDescription.jobDescriptionVersion.jobDescription.id)
//                                .from(qUserJobDescription)
//                                .where(qUserJobDescription.username.eq(qUser.username))
////                                .where(qUserJobDescription.username.in(employees))
//                        ))).groupBy(trainingMatrix.documentVersion.id)
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
        builder.and(qUser.empNo.isNotNull());
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
            builder.and(trainingMatrix.documentVersion.document.docId.like(docId.toUpperCase() + "%"));
        }

        //TODO:: 20201201 - 수정필요
        JPAQuery<MyTraining> jpaQuery = queryFactory.selectDistinct(Projections.constructor(MyTraining.class,
                    qUser,
                trainingMatrix.documentVersion.document,
                trainingMatrix.documentVersion,
                    qTrainingPeriod,
                    qTrainingLog,
                    qTrainingPeriod.startDate,
                    qTrainingPeriod.endDate,
                    ExpressionUtils.as(JPAExpressions.select(min(qUserJobDescription.assignDate))
                    .from(qUserJobDescription).where(qUserJobDescription.user.username.eq(qUser.username).and(qUserJobDescription.status.eq(JobDescriptionStatus.APPROVED))), "assignDate")
                ))
                .from(qUser)
                .leftJoin(qUserJobDescription).on(qUser.username.eq(qUserJobDescription.user.username).and(qUserJobDescription.status.in(statusList)))
//                .leftJoin(trainingMatrix).on(trainingMatrix.trainingAll.eq(true).or(qUserJobDescription.jobDescriptionVersion.jobDescription.id.eq(trainingMatrix.jobDescription.id)))
                .join(qDocumentVersion).on(trainingMatrix.documentVersion.id.eq(qDocumentVersion.id).and(docStatus))
                .join(qTrainingPeriod).on(qTrainingPeriod.documentVersion.id.eq(qDocumentVersion.id))
//                        .and(qTrainingPeriod.trainingType.eq(TrainingType.SELF))
//                        .and(qTrainingPeriod.startDate.loe(new Date()))
//                        .or(trainingMatrix.documentVersion.id.eq(qTrainingPeriod.documentVersion.id)
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
                .orderBy(trainingMatrix.documentVersion.effectiveDate.desc());

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
