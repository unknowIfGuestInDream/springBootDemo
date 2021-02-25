package com.tangl.demo.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * @author: TangLiang
 * @date: 2021/2/25 19:51
 * @since: 1.0
 */
@Component
@Slf4j
public class AsyncCountDown {
    @Async
    public void dealNoReturnTask(CountDownLatch startLatch) {
        log.info("返回值为void的异步调用开始" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("返回值为void的异步调用结束" + Thread.currentThread().getName());
        startLatch.countDown();
    }

    @Async
    public Future<String> dealHaveReturnTask(int i, CountDownLatch startLatch) {
        log.info("asyncInvokeReturnFuture, parementer=" + i);
        Future<String> future;
        try {
            Thread.sleep(1000 * i);
            future = new AsyncResult<String>("success:" + i);
        } catch (InterruptedException e) {
            future = new AsyncResult<String>("error");
        }
        log.info("返回值为String的异步调用结束" + Thread.currentThread().getName());
        startLatch.countDown();
        return future;
    }
}
