package com.dtnsm.esop.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PreDestroy;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@Slf4j
public class AsyncConfig {
    private ThreadPoolTaskExecutor executor;

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(30);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("Executor-SOP-Async-");
        executor.initialize();
        return executor;
    }

    @PreDestroy
    public void destroy() {
        log.info("==> ThreadPoolTaskExecutor Destroy.");
        executor.destroy();
    }
}
