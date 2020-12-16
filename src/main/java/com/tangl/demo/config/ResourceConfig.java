package com.tangl.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 虚拟路径
 *
 * @author: TangLiang
 * @date: 2020/12/16 11:43
 * @since: 1.0
 */
@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    @Value("${word.picture.memory}")
    private String memory;

    @Value("${word.picture.request}")
    private String request;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(request + "/**").addResourceLocations(memory);//linux,注意Linux中不要有//
    }
}
