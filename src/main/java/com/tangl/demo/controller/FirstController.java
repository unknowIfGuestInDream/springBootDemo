package com.tangl.demo.controller;

import com.alibaba.excel.EasyExcel;
import com.tangl.demo.annotation.LogAnno;
import com.tangl.demo.easyexcel.DemoData;
import com.tangl.demo.easyexcel.DemoDataListener;
import com.tangl.demo.easyexcel.NoModelDataListener;
import com.tangl.demo.service.FirstService;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: TangLiang
 * @date: 2020/6/28 14:02
 * @since: 1.0
 */
@Log4j
@Controller
public class FirstController {
    //private static final Logger logger = Logger.getLogger(FirstController.class);

    @Autowired
    private FirstService firstService;

    @GetMapping(value = "hello")
    public String inserthello(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        return "pages/Hello";
    }

    @GetMapping(value = "errors")
    public String errors(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        return "errorPages/error";
    }

    @PostMapping(value = "selectTest")
    @ResponseBody
    @LogAnno(operateType = "查询Test")
    public Map<String, Object> selectTest(Date time, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException {
        Map<String, Object> result = new HashMap<String, Object>();
        log.info("进入selectTest方法");
        List<Map<String, Object>> deptList = firstService.selectTest();
        int total = firstService.countTest();
        result.put("result", deptList);
        result.put("date", new Date());
        result.put("total", total);
        result.put("success", true);

        return result;
    }

    @PostMapping(value = "insertTest")
    @ResponseBody
    @LogAnno(operateType = "添加Test")
    public Map<String, Object> insertTest(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException {
        log.info("进入insertTest方法");
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> deptList = firstService.selectTest();
        int total = firstService.countTest();
//        if (total > 0) {
//            throw new RuntimeException("运行发生错误");
//        }
        result.put("result", deptList);
        result.put("total", total);
        result.put("success", true);

        return result;
    }

    @PostMapping(value = "selectExcel")
    @ResponseBody
    @LogAnno(operateType = "查询Excel")
    public Map<String, Object> selectExcel(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException {
        Map<String, Object> result = new HashMap<String, Object>();
        String fileName = "E:\\demo.xlsx";
        DemoDataListener ddl = new DemoDataListener();
        EasyExcel.read(fileName, DemoData.class, ddl).sheet().doRead();
        ddl.getList();
        result.put("result", ddl.getList());
        result.put("success", true);

        return result;
    }

    @PostMapping(value = "selectExcelNoModel")
    @ResponseBody
    @LogAnno(operateType = "查询Excel用map装数据")
    public Map<String, Object> selectExcelNoModel(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException {
        Map<String, Object> result = new HashMap<String, Object>();
        String fileName = "E:\\demo.xlsx";
        NoModelDataListener ndl = new NoModelDataListener();
        EasyExcel.read(fileName, ndl).doReadAll();

        result.put("result", ndl.getList());
        result.put("success", true);
        return result;
    }

    @PostMapping("upload")
    @ResponseBody
    public String upload(MultipartFile file) throws IOException {
        //EasyExcel.read(file.getInputStream(), UploadData.class, new UploadDataListener(uploadDAO)).sheet().doRead();
        return "SUCCESS";
    }

}
