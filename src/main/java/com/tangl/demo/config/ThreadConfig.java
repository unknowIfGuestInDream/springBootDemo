package com.tangl.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池
 *
 * @author: TangLiang
 * @date: 2020/7/10 9:17
 * @since: 1.0
 */
@Configuration
@EnableAsync
public class ThreadConfig {

    @Value("${thread.corePoolSize}")//设置核心线程数
    private int corePoolSize;
    @Value("${thread.maxPoolSize}") //设置最大线程数
    private int maxPoolSize;
    @Value("${thread.queueCapacity}") //设置队列容量
    private int queueCapacity;
    @Value("${thread.keepAliveSeconds}") //设置线程活跃时间（秒）
    private int keepAliveSeconds;
    @Value("${thread.threadNamePrefix}") //设置默认线程名称
    private String threadNamePrefix;

    @Bean
    public Executor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix(threadNamePrefix);
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }

}
