package com.cauh.iso.service;

import com.cauh.iso.domain.ISOTrainingLog;
import com.cauh.iso.domain.TrainingLog;
import com.cauh.iso.repository.ISOTrainingLogRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ISOTrainingLogService {

    private final ISOTrainingLogRepository isoTrainingLogRepository;

    public Iterable<ISOTrainingLog> findAll(Predicate predicate, OrderSpecifier<?> order) {
        return isoTrainingLogRepository.findAll(predicate, order);
    }

    public Page<ISOTrainingLog> findAll(Predicate predicate, Pageable pageable) {
        return isoTrainingLogRepository.findAll(predicate, pageable);
    }

    public Optional<ISOTrainingLog> findOne(Predicate predicate) {
        return isoTrainingLogRepository.findOne(predicate);
    }

}
