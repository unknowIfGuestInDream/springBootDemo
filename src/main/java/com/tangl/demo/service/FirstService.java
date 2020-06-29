package com.tangl.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author: TangLiang
 * @date: 2020/6/28 14:26
 * @since: 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public interface FirstService {

    List<Map<String, Object>> selectTest();

    int countTest();
}
