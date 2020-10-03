package com.tangl.demo;

import co.elastic.apm.attach.ElasticApmAttacher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

//开启工作流 activiti
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, org.activiti.spring.boot.SecurityAutoConfiguration.class})
@SpringBootApplication
//@EnableScheduling //对定时任务支持
@EnableCaching
@EnableAsync
@ServletComponentScan(basePackages = {"com.tangl.demo.filter", "com.tangl.demo.listener"})
public class SpringBootDemoApplication {

    public static void main(String[] args) {
        //ElasticApmAttacher.attach();
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }

}
