package com.tangl.demo.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

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
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String contextPath = env.getProperty("management.endpoints.web.base-path");
        if (StringUtils.isEmpty(contextPath)) {
            contextPath = "";
        }
        //authorizeRequests 方法限定只对签名成功的用户请求
        //anyRequest 方法限定所有请求
        //authenticated 方法对所有签名成功的用户允许方法
        http
                .csrf().disable()
                .authorizeRequests()
                //跨域请求放行
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/server/servers", "/druid/*").authenticated()
                //对网站静态资源无授权访问
                .antMatchers(HttpMethod.GET, "/", "/*.html", "/favicon.ico",
                        "/**/*.html", "/**/*.css", "/**/*.js",
                        "/swagger-recources/**", "/v2/api-docs/**").permitAll()
                //actuator请求验证
                .antMatchers("/**" + contextPath + "/**").authenticated()
                //除以上请求其余的放行permitAll/验证authenticated
                .anyRequest().permitAll()
                //修改SpringSecurity的Frame配置.
                //someOrigin : 页面可以在相同域名中被iFrame;deny : 不允许被iFrame;disable : 禁用Frame
                .and().headers().frameOptions().sameOrigin()
                //使用记住我的功能
                .and().rememberMe()
                .tokenRepository(persistentTokenRepository()) // 配置token持久化仓库
                .tokenValiditySeconds(3600)
                //and方法是连接词 formLogin代表使用 Security默认的登录页面
                .and().formLogin()
                //httpBasic方法说明启用HTTP基础认证
                .and().httpBasic();
        // 禁用缓存
        http.headers().cacheControl();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl persistentTokenRepository = new JdbcTokenRepositoryImpl();
        persistentTokenRepository.setDataSource(dataSource);
        return persistentTokenRepository;
    }
}
