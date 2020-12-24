package com.cauh.iso.domain;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.JobDescription;
import com.cauh.common.entity.UserJobDescription;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import javax.persistence.Transient;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Data
@Slf4j
public class MyJobDescriptionHistory implements Serializable {
    private static final long serialVersionUID = 950382937483613485L;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @QueryProjection
    public MyJobDescriptionHistory(Account user, List<UserJobDescription> prevJobDescriptions, List<UserJobDescription> nextJobDescriptions){
    }

    public String getStringPrevJobDescriptions(){
        if(!ObjectUtils.isEmpty(prevJobDescriptions)){
            return prevJobDescriptions.stream().map(j ->
                    j.getJobDescription().getShortName()).collect(Collectors.joining(", "));
        }
        return "";
    }

    public String getStringNextJobDescriptions(){
        if(!ObjectUtils.isEmpty(nextJobDescriptions)){
            return nextJobDescriptions.stream().map(j ->
                    j.getJobDescription().getShortName()).collect(Collectors.joining(", "));
        }
        return "";
    }

    public String getStringRequestDate(){
        return toString(getRequestDate());
    }


    private String toString(Date date) {
        if(ObjectUtils.isEmpty(date)) {
            return "";
        } else {
            return dateFormat.format(date);
        }
    }


    private Account user; //유저

    private List<UserJobDescription> prevJobDescriptions; // 직무 리스트

    private List<UserJobDescription> nextJobDescriptions; // 직무 리스트

    private Date requestDate; // 요청일자

}
