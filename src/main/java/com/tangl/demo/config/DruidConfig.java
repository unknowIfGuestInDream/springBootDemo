package com.tangl.demo.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 唐亮
 * @date 22:10 2020/6/27
 * @return
 */
//@Configuration
public class DruidConfig {

    //http://localhost:8080/druid/index.html
    //配置druid的监控
    //1.配置一个后台管理的servlet
//    @Bean
//    public ServletRegistrationBean statViewServlet() {
//        //构造ServletRegistrationBean实例
//        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
//        //配置一些属性
//        //1.定义一个map集合将属性定义在里面
//        Map<String, String> initParams = new HashMap<String, String>();
//        //设置账号
//        initParams.put("loginUsername", "admin");
//        //设置密码
//        initParams.put("loginPassword", "admin");
//        //默认就是允许所有访问
//        initParams.put("allow", "");
//        //设置不允许访问对象
//        initParams.put("deny", "");
//        // 是否可以重置数据源，禁用HTML页面上的“Reset All”功能
//        // initParams.put("resetEnable", "false");
//        bean.setInitParameters(initParams);
//        return bean;
//    }
//
//    //2、配置一个web监控的filter
//    @Bean
//    public FilterRegistrationBean webStatFilter() {
//        FilterRegistrationBean bean = new FilterRegistrationBean();
//        bean.setFilter(new WebStatFilter());
//        Map<String, String> initParams = new HashMap<>();
//        //添加不需要忽略的格式信息
//        initParams.put("exclusions", "*.js,*.gif,*.jpg,*.css,/druid/*");
//        bean.setInitParameters(initParams);
//        //所有请求进行监控处理
//        bean.setUrlPatterns(Arrays.asList("/*"));
//        return bean;
//    }
}
