package com.tangl.demo.config;

import com.tangl.demo.filter.MyFilter2;
import com.tangl.demo.filter.XssFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import javax.servlet.DispatcherType;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置过滤器
 *
 * @author: TangLiang
 * @date: 2020/6/29 14:19
 * @since: 1.0
 */
@Configuration
public class WebFilterConfig {
    @Value("${xss.enabled}")
    private String enabled;
    @Value("${xss.excludes}")
    private String excludes;
    @Value("${xss.urlPatterns}")
    private String urlPatterns;

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

    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns(StringUtils.split(urlPatterns, ","));
        registration.setName("xssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("excludes", excludes);
        initParameters.put("enabled", enabled);
        registration.setInitParameters(initParameters);
        return registration;
    }
}
