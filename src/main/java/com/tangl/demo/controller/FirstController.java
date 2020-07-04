package com.tangl.demo.controller;

import com.tangl.demo.service.FirstService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: TangLiang
 * @date: 2020/6/28 14:02
 * @since: 1.0
 */
@Controller
public class FirstController {
    private static Logger logger = Logger.getLogger(FirstController.class);

    @Autowired
    private FirstService firstService;

    @GetMapping(value = "hello")
    public String hello(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        return "pages/Hello";
    }

    @GetMapping(value = "errors")
    public String errors(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        return "errorPages/error";
    }

    @PostMapping(value = "selectTest")
    @ResponseBody
    public Map<String, Object> selectTest(Date time, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Map<String, Object> result = new HashMap<String, Object>();
        System.out.println(time);
        try {
            List<Map<String, Object>> deptList = firstService.selectTest();
            int total = firstService.countTest();
            result.put("result", deptList);
            result.put("date", new Date());
            result.put("total", total);
            result.put("success", true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.put("success", false);
            e.printStackTrace();
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
        }

        return result;
    }


}
