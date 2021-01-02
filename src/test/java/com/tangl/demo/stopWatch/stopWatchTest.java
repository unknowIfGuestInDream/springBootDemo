package com.tangl.demo.stopWatch;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

/**
 * spring计时工具类 stopWatch
 *
 * @author: TangLiang
 * @date: 2021/1/2 10:00
 * @since: 1.0
 */
@SpringBootTest
public class stopWatchTest {

    @Test
    void test() throws InterruptedException {
        StopWatch sw = new StopWatch("test");
        sw.start("task1");
        // do something
        Thread.sleep(100);
        sw.stop();
        sw.start("task2");
        // do something
        Thread.sleep(200);
        sw.stop();
        System.out.println(sw.prettyPrint());
    }

}
