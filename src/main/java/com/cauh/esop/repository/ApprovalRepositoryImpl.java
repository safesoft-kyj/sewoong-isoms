package com.cauh.esop.repository;

import com.cauh.esop.domain.Approval;
import com.cauh.esop.domain.QApproval;
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
