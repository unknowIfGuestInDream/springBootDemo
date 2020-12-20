package com.tangl.demo.job.batch.entity;

import lombok.Data;
import lombok.ToString;

/**
 * 在文本数据读取的过程中，我们需要将读取的数据转换为POJO对象存储
 *
 * @author: TangLiang
 * @date: 2020/12/20 20:04
 * @since: 1.0
 */
@Data
@ToString
public class TestData {
    private int id;
    private String field1;
    private String field2;
    private String field3;
}
