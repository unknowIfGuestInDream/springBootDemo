package com.tangl.demo.datasource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author: TangLiang
 * @date: 2020/7/15 8:34
 * @since: 1.0
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class tlDataSource {

    @Autowired
    JdbcTemplate txJdbcTemplate;

    @Autowired
    JdbcTemplate tfJdbcTemplate;

    @Test
    public void tlTest() {
        String sql = "select count(*) from pms_product";
        int count = tfJdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println(count);
        count = txJdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println(count);
    }

    @Test
    public void maria() {
        String sql = "select * from pms_product";
        List<Map<String, Object>> list = txJdbcTemplate.queryForList(sql);
        System.out.println(list.size());
        list.forEach(System.out::println);
    }
}
