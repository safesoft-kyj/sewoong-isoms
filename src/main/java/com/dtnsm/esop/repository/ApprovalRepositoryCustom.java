package com.dtnsm.esop.repository;

import com.dtnsm.esop.domain.Approval;

import java.util.List;

public interface ApprovalRepositoryCustom {
    List<Approval> findTop10();
}
