package com.tangl.demo;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

//开启工作流 activiti
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, org.activiti.spring.boot.SecurityAutoConfiguration.class})
@SpringBootApplication
@EnableScheduling //对定时任务支持
@EnableCaching
@EnableAsync
@EnableBatchProcessing //对Batch支持
@EnableRedisHttpSession
@ServletComponentScan(basePackages = {"com.tangl.demo.filter", "com.tangl.demo.listener"})
public class SpringBootDemoApplication {

    public static void main(String[] args) {
        //ElasticApmAttacher.attach();
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }

}
