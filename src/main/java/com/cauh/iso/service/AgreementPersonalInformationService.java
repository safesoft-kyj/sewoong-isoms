package com.cauh.iso.service;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.JobDescription;
import com.cauh.iso.domain.AgreementPersonalInformation;
import com.cauh.iso.domain.QAgreementPersonalInformation;
import com.cauh.iso.repository.AgreementPersonalInformationRepository;
import com.cauh.iso.utils.DateUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class AgreementPersonalInformationService {
    private final AgreementPersonalInformationRepository agreementPersonalInformationRepository;

    public Optional<AgreementPersonalInformation> findById(Integer id) {
        return agreementPersonalInformationRepository.findById(id);
    }

    public Optional<AgreementPersonalInformation> findByInternalUser(Account user) {
        return agreementPersonalInformationRepository.findByInternalUser(user);
    }

    public Page<AgreementPersonalInformation> findAll(Pageable pageable) {
        return agreementPersonalInformationRepository.findAll(pageable);
    }

    public Page<AgreementPersonalInformation> findAll(Predicate predicate, Pageable pageable) {
        return agreementPersonalInformationRepository.findAll(predicate, pageable);
    }

    public TreeMap<String, String> getAgreeMap(){
        //역순
        Comparator<String> reservedSort = (s1, s2) -> s2.compareTo(s1);
        TreeMap<String, String> resultMap = new TreeMap<>(reservedSort);
        resultMap.put("true", "I agree");
        resultMap.put("false", "I do not agree");

        return resultMap;
    }

    /**
     * 내부 사용자의 개인정보 활용 동의 확인
     * @param account
     * @return
     */
    public Optional<AgreementPersonalInformation> findOneAgreementPersonalInformation(Account account) {
        QAgreementPersonalInformation qAgreementPersonalInformation = QAgreementPersonalInformation.agreementPersonalInformation;
        Date today = new Date();
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qAgreementPersonalInformation.internalUser.id.eq(account.getId()));
        builder.and(qAgreementPersonalInformation.agree.eq(true));
        builder.and(qAgreementPersonalInformation.createdDate.goe(new Timestamp(DateUtils.addDay(today, -1825).getTime())));//5년(365*5)

        return agreementPersonalInformationRepository.findOne(builder);
    }

    /**
     * 외부 사용자의 개인정보 활용 동의 확인
     * @param email
     * @return
     */
    public Optional<AgreementPersonalInformation> findOneAgreementPersonalInformation(String email) {
        QAgreementPersonalInformation qAgreementPersonalInformation = QAgreementPersonalInformation.agreementPersonalInformation;
        Date today = new Date();
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qAgreementPersonalInformation.email.eq(email));
        builder.and(qAgreementPersonalInformation.externalCustomer.isNotNull()); //2021-03-04 :: 외부사용자인경우 추가.
        builder.and(qAgreementPersonalInformation.agree.eq(true));
        builder.and(qAgreementPersonalInformation.createdDate.goe(new Timestamp(DateUtils.addDay(today, -1825).getTime())));//5년(365*5)

        return agreementPersonalInformationRepository.findOne(builder);
    }

    public AgreementPersonalInformation save(AgreementPersonalInformation agreementPersonalInformation) {
        return agreementPersonalInformationRepository.save(agreementPersonalInformation);
    }
}
