package com.cauh.iso.service;

import com.cauh.common.entity.JobDescription;
import com.cauh.common.entity.QJobDescription;
import com.cauh.common.repository.JobDescriptionRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobDescriptionService {
    private final JobDescriptionRepository jobDescriptionRepository;

    public JobDescription save(JobDescription jobDescription) {
        return jobDescriptionRepository.save(jobDescription);
    }

    public JobDescription findById(Integer id) {
        Optional<JobDescription> optionalJobDescription = jobDescriptionRepository.findById(id);
        return optionalJobDescription.isPresent() ? optionalJobDescription.get() : null;
    }

    public Optional<JobDescription> findByShortName(String shortName) {
        QJobDescription qJobDescription = QJobDescription.jobDescription;
        return jobDescriptionRepository.findOne(qJobDescription.shortName.eq(shortName));
    }

    public Optional<JobDescription> findByShortNameAndIdNot(String shortName, Integer id) {
        QJobDescription qJobDescription = QJobDescription.jobDescription;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qJobDescription.shortName.eq(shortName));
        builder.and(qJobDescription.id.ne(id));
        log.info("@중복 체크 shortName : {}, JD Id : {}", shortName, id);
        return jobDescriptionRepository.findOne(builder);
    }

    public List<JobDescription> getJobDescriptionList() {
        return jobDescriptionRepository.findAll(Sort.by(Sort.Direction.ASC, "shortName"));
    }
}