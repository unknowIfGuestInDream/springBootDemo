package com.tangl.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 定时任务(多线程)
 *
 * @author: TangLiang
 * @date: 2020/7/10 9:17
 * @since: 1.0
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    /*
     *此处成员变量应该使用@Value从配置中读取
     */
    @Value("10")
    private int corePoolSize;
    @Value("200")
    private int maxPoolSize;
    @Value("10")
    private int queueCapacity;

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.initialize();
        return executor;
    }

}
