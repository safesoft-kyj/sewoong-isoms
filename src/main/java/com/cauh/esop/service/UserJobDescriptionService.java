package com.cauh.esop.service;

import com.cauh.common.entity.QUserJobDescription;
import com.cauh.common.entity.UserJobDescription;
import com.cauh.common.entity.constant.JobDescriptionStatus;
import com.cauh.common.repository.UserJobDescriptionRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserJobDescriptionService {
    private final UserJobDescriptionRepository userJobDescriptionRepository;
    public String getUserShortJobD(String username) {
        QUserJobDescription qJob = QUserJobDescription.userJobDescription;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qJob.username.eq(username));
        builder.and(qJob.status.eq(JobDescriptionStatus.APPROVED));
        Iterable<UserJobDescription> userJobs = userJobDescriptionRepository.findAll(builder);
        if(ObjectUtils.isEmpty(userJobs)) {
            return "";
        } else {
            return StreamSupport.stream(userJobs.spliterator(), false)
                    .map(x -> x.getJobDescriptionVersion().getJobDescription().getShortName())
                    .collect(Collectors.joining(","));
        }
    }
}
