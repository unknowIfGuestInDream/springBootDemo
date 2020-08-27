package com.tangl.demo.es;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;

/**
 * @author: TangLiang
 * @date: 2020/8/27 12:51
 * @since: 1.0
 */
@SpringBootTest
@Slf4j
public class ESTest {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    public void insert() {
    }
}
