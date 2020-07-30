package com.tangl.demo.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 只对EndPoint的访问加验证
 *
 * @author: TangLiang
 * @date: 2020/7/30 15:58
 * @since: 1.0
 */
@Configuration
@EnableWebSecurity
public class ActuatorSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    Environment env;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String contextPath = env.getProperty("management.endpoints.web.base-path");
        if (StringUtils.isEmpty(contextPath)) {
            contextPath = "";
        }
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/**" + contextPath + "/**").authenticated()
                .anyRequest().permitAll()
                .and().httpBasic();
    }
}
