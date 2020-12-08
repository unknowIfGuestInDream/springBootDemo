package com.tangl.demo.hutool;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 测试hutool json的javaBean
 *
 * @author: TangLiang
 * @date: 2020/12/8 11:55
 * @since: 1.0
 */
@Data
public class UserA {
    private String name;
    private String a;
    private Date date;
    private List<Seq> sqs;
}
