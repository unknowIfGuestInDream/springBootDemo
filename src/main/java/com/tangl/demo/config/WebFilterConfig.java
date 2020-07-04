package com.tangl.demo.config;

import com.tangl.demo.filter.MyFilter2;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import java.time.format.DateTimeFormatter;
import java.util.Collections;

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
    public ConversionService conversionService() {
        FormattingConversionServiceFactoryBean factory = new FormattingConversionServiceFactoryBean();
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setDateFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        factory.setFormatterRegistrars(Collections.singleton(registrar));
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new MyFilter2());
        registration.addUrlPatterns("/hello");
        registration.setName("myFilter2");
        return registration;
    }
}
