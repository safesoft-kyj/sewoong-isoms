package com.cauh.iso.service;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.JobDescription;
import com.cauh.common.entity.UserJobDescription;
import com.cauh.common.entity.UserJobDescriptionChangeLog;
import com.cauh.common.entity.constant.RoleStatus;
import com.cauh.common.repository.JobDescriptionRepository;
import com.cauh.common.repository.UserJobDescriptionChangeLogRepository;
import com.cauh.common.repository.UserJobDescriptionRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.matcher.CollectionErasureMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserJobDescriptionChangeLogService {

    private final UserJobDescriptionChangeLogRepository userJobDescriptionChangeLogRepository;
    private final UserJobDescriptionRepository userJobDescriptionRepository;
    private final JobDescriptionRepository jobDescriptionRepository;

    public Page<UserJobDescriptionChangeLog> getUserChangeLog(Predicate predicate, Pageable pageable){
        return userJobDescriptionChangeLogRepository.findAll(predicate, pageable);
    }

    public Optional<UserJobDescriptionChangeLog> getById(Integer id) {
        return userJobDescriptionChangeLogRepository.findById(id);
    }

    public Boolean isRequestedRole(Account user) {
        Optional<UserJobDescriptionChangeLog> userJobDescriptionChangeLogOptional =
                userJobDescriptionChangeLogRepository.findByRequesterAndRoleStatus(user , RoleStatus.REQUESTED);
        return userJobDescriptionChangeLogOptional.isPresent();
    }

    public String getJdIdsByShortNames(String commaShortNames) {
        List<String> jdIdList = new ArrayList<>();
        List<String> jdShortNameList = Arrays.asList(commaShortNames.split(","));

        for(String shortName : jdShortNameList) {
            Optional<JobDescription> jobDescription = jobDescriptionRepository.findByShortName(shortName);
            if(jobDescription.isPresent()) {
                jdIdList.add(Integer.toString(jobDescription.get().getId()));
            }
        }
        return jdIdList.stream().collect(Collectors.joining(","));
    }

    public void updateUserJobDescription(Account user, String prevRoles, String nextRoles, Date assignDate) throws RuntimeException{
        List<String> strPrevJDList = Arrays.asList(prevRoles.split(","));
        List<String> strNextJDList = Arrays.asList(nextRoles.split(","));
        List<JobDescription> prevJDList = strPrevJDList.stream().map(jd -> jobDescriptionRepository.findByShortName(jd).get()).collect(Collectors.toList());
        List<JobDescription> nextJDList = strNextJDList.stream().map(jd -> jobDescriptionRepository.findByShortName(jd).get()).collect(Collectors.toList());

        for(JobDescription jd : prevJDList) {
            if(!nextJDList.contains(jd)){ // 변경 후 ROLE에 포함되지 않으면, (삭제 대상)
                Optional<UserJobDescription> userJobDescription
                        = userJobDescriptionRepository.findByUserAndJobDescription(user, jd);
                if(userJobDescription.isPresent()){
                    userJobDescriptionRepository.delete(userJobDescription.get());
                }else {
                    throw new RuntimeException("삭제 대상 데이터가 비정상적입니다.");
                }
            }
        }

        for(JobDescription jd : nextJDList) {
            if(!prevJDList.contains(jd)){ // 변경 전 ROLE에 포함되지 않으면 (추가 대상)
                UserJobDescription userJobDescription = UserJobDescription.builder()
                        .user(user).jobDescription(jd).assignDate(assignDate).build();
                userJobDescriptionRepository.save(userJobDescription);
            }
        }
    }

    public UserJobDescriptionChangeLog saveChangeLog(UserJobDescriptionChangeLog userJobDescriptionChangeLog) {
        return userJobDescriptionChangeLogRepository.save(userJobDescriptionChangeLog);
    }

}
