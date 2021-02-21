package com.cauh.iso.admin.service;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.QUserJobDescription;
import com.cauh.common.entity.UserJobDescription;
import com.cauh.common.entity.constant.JobDescriptionStatus;
import com.cauh.common.repository.UserJobDescriptionRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserJobDescriptionService {
    private final UserJobDescriptionRepository userJobDescriptionRepository;

    public String getUserShortJobD(String username) {
        QUserJobDescription qJob = QUserJobDescription.userJobDescription;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qJob.user.username.eq(username));
        builder.and(qJob.status.eq(JobDescriptionStatus.APPROVED));
        Iterable<UserJobDescription> userJobs = userJobDescriptionRepository.findAll(builder);
        if(ObjectUtils.isEmpty(userJobs)) {
            return "";
        } else {
            return StreamSupport.stream(userJobs.spliterator(), false)
                    .map(x -> x.getJobDescription().getShortName())
                    .collect(Collectors.joining(","));
        }
    }

    @Transactional
    public void saveAll(Account user) {
        user.getUserJobDescriptions().stream().filter(u -> u.isDelete()).forEach(u -> {
            u.setStatus(JobDescriptionStatus.REVOKED);
            log.info("@User JD Id : {}, 배정 해제 처리", u.getId());
            userJobDescriptionRepository.save(u);
        });

        List<UserJobDescription> newUserJdList = user.getUserJobDescriptions().stream().filter(u -> ObjectUtils.isEmpty(u.getId())).collect(Collectors.toList());
        if(!ObjectUtils.isEmpty(newUserJdList)) {
            log.info("@User : {} 신규 JD 수 : {}", user.getUsername(), newUserJdList.size());
            for(UserJobDescription newUserJd : newUserJdList) {
                newUserJd.setUser(user);
                newUserJd.setStatus(JobDescriptionStatus.APPROVED);
                userJobDescriptionRepository.save(newUserJd);
            }
        }

        //기존 Role들의 직무 배정일 정보 Setting
        user.getUserJobDescriptions().stream()
                .filter(u -> !ObjectUtils.isEmpty(u.getId())).filter(u -> !u.isDelete())
                .forEach(u -> {userJobDescriptionRepository.save(u);});

    }

}
