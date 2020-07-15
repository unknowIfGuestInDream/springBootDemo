package com.tangl.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author: TangLiang
 * @date: 2020/7/14 17:16
 * @since: 1.0
 */
@Configuration
public class DataSourceConfig {
    @Primary
    @Bean(name = "dataSource")
    @Qualifier("dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource mpDefault() {
        return new DruidDataSource();
    }

    @Bean(name = "tfJdbcTemplate")
    public JdbcTemplate tfJdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "txDataSource")
    @Qualifier("txDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.cluster")
    public DataSource txDataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "txJdbcTemplate")
    public JdbcTemplate txJdbcTemplate(@Qualifier("txDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
