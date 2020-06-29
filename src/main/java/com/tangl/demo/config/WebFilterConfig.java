package com.tangl.demo.config;

import com.tangl.demo.filter.MyFilter2;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置过滤器
 *
 * @author: TangLiang
 * @date: 2020/6/29 14:19
 * @since: 1.0
 */
@Configuration
public class WebFilterConfig {

    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new MyFilter2());
        registration.addUrlPatterns("/hello");
        registration.setName("myFilter2");
        return registration;
    }
}
