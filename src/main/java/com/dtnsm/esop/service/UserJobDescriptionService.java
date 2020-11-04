package com.dtnsm.esop.service;

import com.dtnsm.common.entity.QUserJobDescription;
import com.dtnsm.common.entity.UserJobDescription;
import com.dtnsm.common.entity.constant.JobDescriptionStatus;
import com.dtnsm.common.repository.UserJobDescriptionRepository;
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
