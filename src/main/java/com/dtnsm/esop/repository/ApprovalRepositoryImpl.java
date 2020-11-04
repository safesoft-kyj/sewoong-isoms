package com.dtnsm.esop.repository;

import com.dtnsm.esop.domain.Approval;
import com.dtnsm.esop.domain.QApproval;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ApprovalRepositoryImpl implements ApprovalRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Approval> findTop10() {
        QApproval qApproval = QApproval.approval;
        return queryFactory.selectFrom(qApproval)
                .where(qApproval.deleted.eq(false))
                .limit(10)
                .orderBy(qApproval.id.desc())
                .fetch();
    }
}
