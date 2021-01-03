package com.tangl.demo.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时任务测试
 * 注解@Async开启多线程定时
 * Schedule 注解有一个缺点，其定时的时间不能动态的改变，
 * 而基于 SchedulingConfigurer 接口的方式可以做到。
 *
 * @author: TangLiang
 * @date: 2020/7/10 9:19
 * @since: 1.0
 */
@Slf4j
//@Component
@Async
public class ScheduledService {
    //https://cron.qqe2.com/

    /*
    fixedRate：定义一个按一定频率执行的定时任务
    上一次开始执行时间点之后多长时间再执行

    fixedRateString fixedRate 意思相同，只是使用字符串的形式。唯一不同的是支持占位符。

    fixedDelay：定义一个按一定频率执行的定时任务，与上面不同的是，改属性可以配合initialDelay，
    上一次执行完毕时间点之后多长时间再执行

    fixedDelayString 与 3. fixedDelay 意思相同，只是使用字符串的形式。唯一不同的是支持占位符。如：
    @Scheduled(fixedDelayString = "5000") //上一次执行完毕时间点之后5秒再执行
    占位符的使用(配置文件中有配置：time.fixedDelay=5000)：
     @Scheduled(fixedDelayString = "${time.fixedDelay}")

     initialDelay第一次延迟多长时间后再执行 如：
     @Scheduled(initialDelay=1000, fixedRate=5000) //第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次
      initialDelayString 与initialDelay 意思相同，只是使用字符串的形式。唯一不同的是支持占位符。
     */

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
