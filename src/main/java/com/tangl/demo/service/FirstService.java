package com.tangl.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author: TangLiang
 * @date: 2020/6/28 14:26
 * @since: 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public interface FirstService {

    List<Map<String, Object>> selectTest(String ID_) throws SQLException;

    Future<List<Map<String, Object>>> selectAstncTest(String ID_);

    int countTest();

    int updateTest(String ID_, String CODE_, String NAME_);

    int deleteTest(String ID_);
}
