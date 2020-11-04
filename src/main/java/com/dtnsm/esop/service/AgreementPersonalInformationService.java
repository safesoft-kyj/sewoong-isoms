package com.dtnsm.esop.service;

import com.dtnsm.esop.domain.AgreementPersonalInformation;
import com.dtnsm.esop.domain.QAgreementPersonalInformation;
import com.dtnsm.esop.repository.AgreementPersonalInformationRepository;
import com.dtnsm.esop.utils.DateUtils;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgreementPersonalInformationService {
    private final AgreementPersonalInformationRepository agreementPersonalInformationRepository;

    public Optional<AgreementPersonalInformation> findById(Integer id) {
        return agreementPersonalInformationRepository.findById(id);
    }

    public Page<AgreementPersonalInformation> findAll(Pageable pageable) {
        return agreementPersonalInformationRepository.findAll(pageable);
    }

    public Optional<AgreementPersonalInformation> findOneAgreementPersonalInformation(String email) {
        QAgreementPersonalInformation qAgreementPersonalInformation = QAgreementPersonalInformation.agreementPersonalInformation;
        Date today = new Date();
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qAgreementPersonalInformation.email.eq(email));
        builder.and(qAgreementPersonalInformation.agree.eq(true));
        builder.and(qAgreementPersonalInformation.createdDate.goe(new Timestamp(DateUtils.addDay(today, -1825).getTime())));//5년(365*5)

        return agreementPersonalInformationRepository.findOne(builder);
    }

    public AgreementPersonalInformation save(AgreementPersonalInformation agreementPersonalInformation) {
        return agreementPersonalInformationRepository.save(agreementPersonalInformation);
    }
}
