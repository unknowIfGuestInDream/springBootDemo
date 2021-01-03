package com.tangl.demo.controller.task;

import com.tangl.demo.annotation.LogAnno;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 定时任务管理
 *
 * @author: TangLiang
 * @date: 2021/1/3 18:06
 * @since: 1.0
 */
@Api(tags = "定时任务管理")
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    //无参定时任务
    @PostMapping("/taskNoParams")
    @ApiOperation("无参定时任务")
    @LogAnno(operateType = "无参定时任务")
    public Map task1() {
        Map<String, Object> result = new HashMap<>();
        SchedulingRunnable task = new SchedulingRunnable("demoTask", "taskNoParams", null);
        cronTaskRegistrar.addCronTask(task, "0/10 * * * * ?");
        return result;
    }

    //有参定时任务
    @PostMapping("/taskWithParams")
    @ApiOperation("有参定时任务")
    @LogAnno(operateType = "有参定时任务")
    public Map task2() {
        Map<String, Object> result = new HashMap<>();
        SchedulingRunnable task = new SchedulingRunnable("demoTask", "taskWithParams", "Hello Task");
        cronTaskRegistrar.addCronTask(task, "0/10 * * * * ?");
        return result;
    }
}
