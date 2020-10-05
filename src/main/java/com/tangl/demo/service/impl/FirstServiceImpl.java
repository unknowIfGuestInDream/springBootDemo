package com.tangl.demo.service.impl;

import com.tangl.demo.service.FirstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author: TangLiang
 * @date: 2020/6/28 14:27
 * @since: 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class FirstServiceImpl implements FirstService {
    @Autowired
    JdbcTemplate txJdbcTemplate;

    @Autowired
    JdbcTemplate tfJdbcTemplate;

    @Cacheable(cacheNames = "user", key = "#ID_", condition = "#ID_ != null && #ID_.length()>0", unless = "#result.size() == 0")
    @Override
    public List<Map<String, Object>> selectTest(String ID_) throws SQLException {
        Map<String, Object> paramMap = new HashMap<>();
        String sql = "select * from tx_realtime_parm_test where 1 = 1";
        if (!StringUtils.isEmpty(ID_)) {
            sql += " and ID_ = :ID_";
            paramMap.put("ID_", ID_);
        }
        if ("MySQL".equals(tfJdbcTemplate.getDataSource().getConnection().getMetaData().getDatabaseProductName())) {
            sql += " limit 0,100";
        } else if ("Oracle".equals(tfJdbcTemplate.getDataSource().getConnection().getMetaData().getDatabaseProductName())) {
            sql = "select * from (select FULLTABLE.*, ROWNUM RN from (" + sql + ") FULLTABLE where ROWNUM <= " + 100 + ") where RN >= " + 0;
        }
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(tfJdbcTemplate);
        return namedParameterJdbcTemplate.queryForList(sql, paramMap);
    }

    @Override
    @Async
    public Future<List<Map<String, Object>>> selectAstncTest(String ID_) {
        Map<String, Object> paramMap = new HashMap<>();
        String sql = "select * from tx_realtime_parm_test where 1 = 1";
        if (!StringUtils.isEmpty(ID_)) {
            sql += " and ID_ = :ID_";
            paramMap.put("ID_", ID_);
        }
        sql += " limit 0,10";

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(tfJdbcTemplate);
        Future<List<Map<String, Object>>> future;
        future = new AsyncResult<>(namedParameterJdbcTemplate.queryForList(sql, paramMap));
        return future;
    }

    @Cacheable(cacheNames = "userCount", key = "#root.method")
    @Override
    public int countTest() {
        String sql = "select count(*) from tx_realtime_parm_test";
        return txJdbcTemplate.queryForObject(sql, Integer.class);
    }

    @CacheEvict(cacheNames = "user", key = "#ID_", beforeInvocation = true)
    @Override
    public int updateTest(String ID_, String CODE_, String NAME_) {
        String sql = "update tx_realtime_parm_test set CODE_ = ?, NAME = ? where ID_ = ?";
        return txJdbcTemplate.update(sql, CODE_, NAME_, ID_);
    }

    @CacheEvict(cacheNames = "user", key = "#ID_", beforeInvocation = true)
    @Override
    public int deleteTest(String ID_) {
        String sql = "delete from tx_realtime_parm_test where ID_ = ?";
        return txJdbcTemplate.update(sql, ID_);
    }
}
