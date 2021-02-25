package com.tangl.demo.current;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.tangl.demo.Document.LogDocument;
import com.tangl.demo.async.AsyncCountDown;
import com.tangl.demo.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * countDownLatch计数器
 *
 * @author: TangLiang
 * @date: 2021/1/2 10:06
 * @since: 1.0
 */
@SpringBootTest
@Slf4j
public class countDownLatchTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AsyncCountDown asyncCountDown;

    private static int mobile = 0;

    @Test
    public void countDownLatch() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 2000; i++) {
            new Thread(new RunThread(countDownLatch)).start();
        }
        log.info("线程准备完毕");
        //启动多个线程
        countDownLatch.countDown();
    }

    private class RunThread implements Runnable {
        private final CountDownLatch startLatch;

        public RunThread(CountDownLatch startLatch) {
            this.startLatch = startLatch;
        }

        @Override
        public void run() {
            //等待
            try {
                startLatch.await();
                mobile += 1;
                LogDocument logDocument = new LogDocument();
                logDocument.setId(IdUtil.objectId());
                logDocument.setOperateType("测试CountDownLatch");
                logDocument.setBrowser("Firefox");
                logDocument.setVersion("64.0");
                logDocument.setOs("WINDOWS 7");
                logDocument.setIpName("TANGLIANG");
                logDocument.setIp("127.0.0.1");
                logDocument.setUrl("/hello");
                logDocument.setParams("{" + mobile + "}");
                logDocument.setCreateTime(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, new Date()));
                rabbitTemplate.convertAndSend("logToMongo", JSON.toJSONString(logDocument));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    //和异步结合使用
    @Test
    public void async() throws InterruptedException {
        log.info("=====开始了=====");
        CountDownLatch countDownLatch = new CountDownLatch(2);
        asyncCountDown.dealNoReturnTask(countDownLatch);
        asyncCountDown.dealHaveReturnTask(6, countDownLatch);
        log.info("=====方法调用完成=====");
        countDownLatch.await();
        log.info("=====方法结束=====");
    }
}
