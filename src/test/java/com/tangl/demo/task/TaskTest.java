package com.tangl.demo.task;

import com.tangl.demo.controller.task.CronTaskRegistrar;
import com.tangl.demo.controller.task.SchedulingRunnable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 定时任务测试类
 *
 * @author: TangLiang
 * @date: 2021/1/3 17:57
 * @since: 1.0
 */
@SpringBootTest
public class TaskTest {
    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    //无参定时任务
    @Test
    public void task1() {
        SchedulingRunnable task = new SchedulingRunnable("demoTask", "taskNoParams", null);
        cronTaskRegistrar.addCronTask(task, "0/10 * * * * ?");
    }

    //有参定时任务
    @Test
    public void task2() {
        SchedulingRunnable task = new SchedulingRunnable("demoTask", "taskWithParams", "Hello Task");
        cronTaskRegistrar.addCronTask(task, "0/10 * * * * ?");
    }
}
