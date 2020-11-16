package com.cauh.esop.service;

import com.cauh.esop.domain.NonDisclosureAgreement;
import com.cauh.esop.domain.QNonDisclosureAgreement;
import com.cauh.esop.repository.NonDisclosureAgreementRepository;
import com.cauh.esop.utils.DateUtils;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NonDisclosureAgreementService {
    private final NonDisclosureAgreementRepository nonDisclosureAgreementRepository;

    public Optional<NonDisclosureAgreement> findById(Integer id) {
        return nonDisclosureAgreementRepository.findById(id);
    }

    public Page<NonDisclosureAgreement> findAll(Pageable pageable) {
        return nonDisclosureAgreementRepository.findAll(pageable);
    }

    public Optional<NonDisclosureAgreement> findOneNonDisclosureAgreement(String email) {
        QNonDisclosureAgreement qNonDisclosureAgreement = QNonDisclosureAgreement.nonDisclosureAgreement;
        Date today = new Date();
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qNonDisclosureAgreement.email.eq(email));
        builder.and(qNonDisclosureAgreement.createdDate.goe(new Timestamp(DateUtils.addDay(today, -1825).getTime())));//5년(365*5)

        return nonDisclosureAgreementRepository.findOne(builder);
    }

    public NonDisclosureAgreement save(NonDisclosureAgreement nonDisclosureAgreement) {
        return nonDisclosureAgreementRepository.save(nonDisclosureAgreement);
    }
}
