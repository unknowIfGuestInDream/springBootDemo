package com.tangl.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableScheduling //对定时任务支持
@EnableCaching
@ServletComponentScan(basePackages = {"com.tangl.demo.filter", "com.tangl.demo.listener"})
public class SpringBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }

}
