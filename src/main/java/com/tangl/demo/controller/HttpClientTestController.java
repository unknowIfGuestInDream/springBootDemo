package com.tangl.demo.controller;

import com.tangl.demo.test.HttpClient.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: TangLiang
 * @date: 2020/7/28 20:32
 * @since: 1.0
 */
@RestController
public class HttpClientTestController {
    @RequestMapping(value = "doGetControllerOne")
    public String doGetControllerOne(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        return "pages/Hello";
    }

    @RequestMapping(value = "doGetControllerTwo")
    public String doGetControllerTwo(String name, String age, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        return name + "已经" + age + "岁了";
    }

    @PostMapping(value = "doPostControllerOne")
    public Map doPostControllerOne(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        Map map = new HashMap();
        map.put("success", true);
        map.put("result", "无参POST请求");
        return map;
    }

    @PostMapping(value = "doPostControllerFour")
    public String doPostControllerFour(String name, String age, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        return name + "已经" + age + "岁了";
    }

    @PostMapping(value = "doPostControllerTwo")
    public String doPostControllerTwo(@RequestBody User user, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        return user.getName() + "是个" + user.getAge() + "岁的" + user.getSex() + "性";
    }
}
