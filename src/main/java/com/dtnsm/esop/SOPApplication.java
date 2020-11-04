package com.dtnsm.esop;

import com.dtnsm.common.component.CustomAuditorAware;
import com.dtnsm.esop.property.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.ErrorPageFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Configuration
@ComponentScan({"com.dtnsm"})
@EnableTransactionManagement
@EnableJpaRepositories({"com.dtnsm.common.repository", "com.dtnsm.esop.repository"})
@EntityScan({"com.dtnsm.common.domain", "com.dtnsm.esop.domain", "com.dtnsm.common.entity"})
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware", modifyOnCreate = false)
@EnableConfigurationProperties({
        FileStorageProperties.class
})
@EnableScheduling
public class SOPApplication {

    @Autowired
    private CustomAuditorAware auditorAware;

    public static void main(String[] args) {
        SpringApplication.run(SOPApplication.class, args);
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return auditorAware;
    }

    @Bean
    public ErrorPageFilter errorPageFilter() {
        return new ErrorPageFilter();
    }

    @Bean
    public FilterRegistrationBean disableSpringBootErrorFile(ErrorPageFilter filter) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(filter);
        filterRegistrationBean.setEnabled(false);
        return filterRegistrationBean;
    }



    /**
     * BATCH - SOP
     * DEVELOPMENT(전자결재 - 승인) -> 관리자 업로드
     * APPROVED -> EFFECTIVE
     * REVISION -> effective / 기존 effective sop -> superseded 처리
     *
     *
     * RETIREMENT -> SUPERSEDED
     */

    /**
     * BATCH - RD
     * DEVELOPMENT(전자결재 - 승인) -> 관리자 업로드
     * APPROVED -> EFFECTIVE
     * REVISION(전자결재 - 승인) -> 1. 관리자 업로드 -> approved 상태로 변경(effective date) -> 기존 rd superseded 상태 변경(과제 아이디 있는 경우 supersed 처리 하지 않음)
     */
}
