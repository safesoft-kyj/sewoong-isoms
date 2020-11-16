package com.cauh.esop.service;

import com.cauh.esop.domain.OfflineTrainingAttendee;
import com.cauh.esop.repository.OfflineTrainingAttendeeRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfflineTrainingAttendeeService {
    private final OfflineTrainingAttendeeRepository offlineTrainingAttendeeRepository;

    public Page<OfflineTrainingAttendee> findAll(Predicate predicate, Pageable pageable) {
        return offlineTrainingAttendeeRepository.findAll(predicate, pageable);
    }
}
