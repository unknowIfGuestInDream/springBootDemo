package com.tangl.demo.context;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author: TangLiang
 * @date: 2020/6/28 10:05
 * @since: 1.0
 */
@SpringBootTest
public class contextTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void context(){
        System.out.println(jdbcTemplate.queryForObject("select count(*) from tx_realtime_parm_test",Integer.class));
    }

    @Test
    void context1(){
        System.out.println(jdbcTemplate.queryForList("select * from cb_code"));
    }
}
