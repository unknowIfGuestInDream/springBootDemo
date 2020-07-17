package com.tangl.demo.datasource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

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
        String sql = "select count(*) from tx_realtime_parm_test";
        int count = tfJdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println(count);
        count = txJdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println(count);

    }
}
