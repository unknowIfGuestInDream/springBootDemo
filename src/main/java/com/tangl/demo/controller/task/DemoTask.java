package com.tangl.demo.controller.task;

import org.springframework.stereotype.Component;

/**
 * @author: TangLiang
 * @date: 2021/1/3 17:52
 * @since: 1.0
 */
@Component("demoTask")
public class DemoTask {
    public void taskWithParams(String params) {
        System.out.println("执行有参示例任务：" + params);
    }

    public void taskNoParams() {
        System.out.println("执行无参示例任务");
    }
}
