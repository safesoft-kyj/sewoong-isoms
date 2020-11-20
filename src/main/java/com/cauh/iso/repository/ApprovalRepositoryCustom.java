package com.cauh.iso.repository;

import com.cauh.iso.domain.Approval;

import java.util.List;

public interface ApprovalRepositoryCustom {
    List<Approval> findTop10();
}
