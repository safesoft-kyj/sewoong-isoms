package com.cauh.iso.service;

import com.cauh.common.entity.Account;
import com.cauh.iso.domain.AgreementPersonalInformation;
import com.cauh.iso.domain.ConfidentialityPledge;
import com.cauh.iso.domain.QAgreementPersonalInformation;
import com.cauh.iso.domain.QConfidentialityPledge;
import com.cauh.iso.repository.ConfidentialityPledgeRepository;
import com.cauh.iso.utils.DateUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
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
public class ConfidentialityPledgeService {

    private final ConfidentialityPledgeRepository confidentialityPledgeRepository;

    public Optional<ConfidentialityPledge> findByInternalUser(Account user){
        return confidentialityPledgeRepository.findByInternalUser(user);
    }

    public Page<ConfidentialityPledge> findAll(Pageable pageable) {
        return confidentialityPledgeRepository.findAll(pageable);
    }

    public Page<ConfidentialityPledge> findAll(Predicate predicate, Pageable pageable) {
        return confidentialityPledgeRepository.findAll(predicate, pageable);
    }

    public ConfidentialityPledge save(ConfidentialityPledge confidentialityPledge) {
        return confidentialityPledgeRepository.save(confidentialityPledge);
    }

    /**
     * 내부 사용자의 개인정보 활용 동의 확인
     * @param account
     * @return
     */
    public Optional<ConfidentialityPledge> findOneConfidentialityPledge(Account account) {
        QConfidentialityPledge qConfidentialityPledge = QConfidentialityPledge.confidentialityPledge;

        Date today = new Date();
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qConfidentialityPledge.internalUser.id.eq(account.getId()));
        builder.and(qConfidentialityPledge.agree.eq(true));
        builder.and(qConfidentialityPledge.createdDate.goe(new Timestamp(DateUtils.addDay(today, -1825).getTime())));//5년(365*5)

        return confidentialityPledgeRepository.findOne(builder);
    }
}
