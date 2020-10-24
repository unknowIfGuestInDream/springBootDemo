package com.tangl.demo.controller.quartz;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.tangl.demo.common.AjaxResult;
import com.tangl.demo.quartz.CronProcessJob;
import com.tangl.demo.quartz.ScheduleService;
import com.tangl.demo.quartz.SendEmailJob;
import com.tangl.demo.quartz.SendMessageJob;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 定时任务调度相关接口
 *
 * @author: TangLiang
 * @date: 2020/10/19 19:47
 * @since: 1.0
 */
@Api(tags = "ScheduleController", description = "定时任务调度相关接口")
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private Scheduler scheduler;
    private String defaultGroup = "default_group";

    @ApiOperation("定时发送邮件")
    @PostMapping("/sendEmail")
    public AjaxResult sendEmail(@RequestParam String startTime, @RequestParam String data) {
        Date date = DateUtil.parse(startTime, DatePattern.NORM_DATETIME_FORMAT);
        String jobName = scheduleService.scheduleFixTimeJob(SendEmailJob.class, date, data);
        return AjaxResult.success(jobName);
    }

    @ApiOperation("定时发送站内信")
    @PostMapping("/sendMessage")
    public AjaxResult sendMessage(@RequestParam String startTime, @RequestParam String data) {
        Date date = DateUtil.parse(startTime, DatePattern.NORM_DATETIME_FORMAT);
        String jobName = scheduleService.scheduleFixTimeJob(SendMessageJob.class, date, data);
        return AjaxResult.success(jobName);
    }

    @ApiOperation("通过CRON表达式调度任务")
    @PostMapping("/scheduleJob")
    public AjaxResult scheduleJob(@RequestParam String cron, @RequestParam String data) {
        String jobName = scheduleService.scheduleJob(CronProcessJob.class, cron, data);
        return AjaxResult.success(jobName);
    }

    @ApiOperation("取消定时任务")
    @PostMapping("/cancelScheduleJob")
    public AjaxResult cancelScheduleJob(@RequestParam String jobName) {
        Boolean success = scheduleService.cancelScheduleJob(jobName);
        return AjaxResult.success(success);
    }

    @ApiOperation("quartzAPI")
    @PostMapping("/list")
    public AjaxResult list(@RequestParam String jobName) throws SchedulerException {
        System.out.println(scheduler.getCurrentlyExecutingJobs());
        return AjaxResult.success(true);
    }
}
