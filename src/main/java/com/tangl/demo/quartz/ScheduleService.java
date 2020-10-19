package com.tangl.demo.quartz;

import org.quartz.Job;

import java.util.Date;

/**
 * Quartz定时任务操作类
 *
 * @author: TangLiang
 * @date: 2020/10/19 19:42
 * @since: 1.0
 */
public interface ScheduleService {
    /**
     * 通过CRON表达式调度任务
     */
    String scheduleJob(Class<? extends Job> jobBeanClass, String cron, String data);

    /**
     * 调度指定时间的任务
     */
    String scheduleFixTimeJob(Class<? extends Job> jobBeanClass, Date startTime, String data);

    /**
     * 取消定时任务
     */
    Boolean cancelScheduleJob(String jobName);
}
