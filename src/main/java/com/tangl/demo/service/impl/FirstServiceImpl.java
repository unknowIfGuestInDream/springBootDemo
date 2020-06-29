package com.tangl.demo.service.impl;

import com.tangl.demo.service.FirstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: TangLiang
 * @date: 2020/6/28 14:27
 * @since: 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class FirstServiceImpl implements FirstService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> selectTest() {
        Map<String, Object> paramMap = new HashMap<>();
        String sql = "select * from tx_realtime_parm_test limit 0,100";
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        return namedParameterJdbcTemplate.queryForList(sql, paramMap);
    }

    @Override
    public int countTest() {
        String sql = "select count(*) from tx_realtime_parm_test";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
