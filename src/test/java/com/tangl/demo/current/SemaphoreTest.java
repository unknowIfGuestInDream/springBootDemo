package com.tangl.demo.current;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author: TangLiang
 * @date: 2021/2/26 13:31
 * @since: 1.0
 */
@SpringBootTest
public class SemaphoreTest {
    /*

    Semaphore 是 synchronized 的加强版，作用是控制线程的并发数量。
    就这一点而言，单纯的synchronized 关键字是实现不了的。
    常用于限制可以访问某些资源的线程数量，例如通过 Semaphore 限流。
     */

    @Test
    public void test1() {
        ExecutorService executorService = Executors.newCachedThreadPool();

        //信号量，只允许 3个线程同时访问
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 10; i++) {
            final long num = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        //获取许可
                        semaphore.acquire();
                        //执行
                        System.out.println("Accessing: " + num);
                        Thread.sleep(new Random().nextInt(5000)); // 模拟随机执行时长
                        //释放
                        semaphore.release();
                        System.out.println("Release..." + num);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        executorService.shutdown();
    }
}
