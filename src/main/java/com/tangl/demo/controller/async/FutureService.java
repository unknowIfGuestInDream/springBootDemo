package com.tangl.demo.controller.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author: TangLiang
 * @date: 2020/10/5 13:08
 * @since: 1.0
 */
@Service
public class FutureService {
    @Async
    public Future<String> JobOne() throws InterruptedException {
        System.out.println("开始执行任务一");
        long l1 = System.currentTimeMillis();
        Thread.sleep(2000);
        long l2 = System.currentTimeMillis();
        System.out.println("任务一_用时" + (l2 - l1));
        return new AsyncResult<String>("任务一完成");//可以使用try,catch定义在正常完成时返回一个success信息，出现异常时返回error信息。
    }

    @Async
    public Future<String> JobTwo() throws InterruptedException {
        System.out.println("开始执行任务二");
        long l1 = System.currentTimeMillis();
        Thread.sleep(2000);
        long l2 = System.currentTimeMillis();
        System.out.println("任务二_用时" + (l2 - l1));
        return new AsyncResult<String>("任务二完成");
    }


    @Async
    public Future<String> JobThree() throws InterruptedException {
        System.out.println("开始执行任务三");
        long l1 = System.currentTimeMillis();
        Thread.sleep(2000);
        long l2 = System.currentTimeMillis();
        System.out.println("任务三_用时" + (l2 - l1));
        return new AsyncResult<String>("任务三完成");
    }

}
