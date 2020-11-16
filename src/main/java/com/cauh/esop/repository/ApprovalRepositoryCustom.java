package com.cauh.esop.repository;

import com.cauh.esop.domain.Approval;

import java.util.List;

public interface ApprovalRepositoryCustom {
    List<Approval> findTop10();
}
