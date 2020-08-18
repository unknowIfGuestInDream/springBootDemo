package com.tangl.demo.async;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


/**
 * @author: TangLiang
 * @date: 2020/8/18 13:46
 * @since: 1.0
 */
@SpringBootTest
@Slf4j
public class ApplicationTests {
    @Autowired
    private AsyncTask asyncTask;

    @Test
    public void testAsync() throws InterruptedException, ExecutionException {
        asyncTask.dealNoReturnTask();

        Future<String> f = asyncTask.dealHaveReturnTask(5);

        log.info("主线程执行finished");

        log.info(f.get());
        assertThat(f.get(), is("success:" + 5));
    }

}
