package com.cauh.iso.service;

import com.cauh.iso.domain.ConfidentialityPledge;
import com.cauh.iso.repository.ConfidentialityPledgeRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfidentialityPledgeService {

    private final ConfidentialityPledgeRepository confidentialityPledgeRepository;


    public Page<ConfidentialityPledge> findAll(Pageable pageable) {
        return confidentialityPledgeRepository.findAll(pageable);
    }

    public Page<ConfidentialityPledge> findAll(Predicate predicate, Pageable pageable) {
        return confidentialityPledgeRepository.findAll(predicate, pageable);
    }

    public ConfidentialityPledge save(ConfidentialityPledge confidentialityPledge) {
        return confidentialityPledgeRepository.save(confidentialityPledge);
    }
}
