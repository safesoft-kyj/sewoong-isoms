package com.cauh.iso.service;

import com.cauh.iso.domain.ISOOfflineTrainingAttendee;
import com.cauh.iso.repository.ISOOfflineTrainingAttendeeRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ISOOfflineTrainingAttendeeService {
    private final ISOOfflineTrainingAttendeeRepository isoOfflineTrainingAttendeeRepository;

    public Page<ISOOfflineTrainingAttendee> findAll(Predicate predicate, Pageable pageable) {
        return isoOfflineTrainingAttendeeRepository.findAll(predicate, pageable);
    }
}
