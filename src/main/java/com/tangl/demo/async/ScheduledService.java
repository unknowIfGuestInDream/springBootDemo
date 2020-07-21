package com.tangl.demo.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务测试
 * 注解@Async开启多线程定时
 *
 * @author: TangLiang
 * @date: 2020/7/10 9:19
 * @since: 1.0
 */
@Slf4j
@Component
@Async
public class ScheduledService {

    //fixedRate：定义一个按一定频率执行的定时任务
    //fixedDelay：定义一个按一定频率执行的定时任务，与上面不同的是，改属性可以配合initialDelay，
    // 定义该任务延迟执行时间。
    //cron：通过表达式来配置任务执行时间
    //@Async
    @Scheduled(cron = "0/5 * * * * *")
    public void scheduled() {
        log.info("=====>>>>>使用cron  {}", System.currentTimeMillis());
    }

    //@Async
    @Scheduled(fixedRate = 5000)
    public void scheduled1() {
        log.info("=====>>>>>使用fixedRate{}", System.currentTimeMillis());
    }

    //@Async
    @Scheduled(fixedDelay = 5000)
    public void scheduled2() {
        log.info("=====>>>>>fixedDelay{}", System.currentTimeMillis());
    }

}
