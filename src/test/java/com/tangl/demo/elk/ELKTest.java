package com.tangl.demo.elk;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: TangLiang
 * @date: 2020/8/31 20:18
 * @since: 1.0
 */
@SpringBootTest
@Slf4j
public class ELKTest {

    @Test
    public void elk() {

        log.error("错误信息为: {}", new RuntimeException("测试错误信息"));
    }

    @Test
    public void elkTest() {
        int a = 1;
        int b = 0;
        try {
            int c = a / b;
        } catch (Exception e) {
            log.error("错误信息: {}", new RuntimeException(e.getMessage()));
        }

    }
}
