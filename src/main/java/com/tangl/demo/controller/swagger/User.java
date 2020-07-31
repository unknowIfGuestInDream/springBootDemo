package com.tangl.demo.controller.swagger;

import java.io.Serializable;

/**
 * @author: TangLiang
 * @date: 2020/7/29 10:51
 * @since: 1.0
 */
public class User implements Serializable {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 年龄
     */
    private Integer age;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
