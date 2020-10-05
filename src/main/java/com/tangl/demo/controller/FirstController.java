package com.tangl.demo.controller;

import com.alibaba.excel.EasyExcel;
import com.tangl.demo.annotation.LogAnno;
import com.tangl.demo.annotation.RepeatSubmit;
import com.tangl.demo.easyexcel.DemoData;
import com.tangl.demo.easyexcel.DemoDataListener;
import com.tangl.demo.easyexcel.NoModelDataListener;
import com.tangl.demo.service.FirstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author: TangLiang
 * @date: 2020/6/28 14:02
 * @since: 1.0
 */
@Slf4j
@Controller
public class FirstController {

    @Autowired
    private FirstService firstService;

    @GetMapping(value = "hello")
    public String inserthello(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        Cookie cookie = new Cookie("name", "唐亮");
        response.addCookie(cookie);

        return "pages/Hello";
    }

    @GetMapping(value = "updateT")
    public String updateTest(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        return "pages/updateTest";
    }

    @GetMapping(value = "deleteT")
    public String deleteTest(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        return "pages/deleteTest";
    }

    @GetMapping(value = "errors")
    public String errors(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        return "errorPages/error";
    }

    @GetMapping(value = "upload")
    public String upload(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        return "pages/upload";
    }

    @GetMapping(value = "createErWM")
    public String createErWM(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        return "pages/createErWM";
    }

    @PostMapping(value = "selectTest")
    @ResponseBody
    @LogAnno(operateType = "查询Test")
    //@RepeatSubmit
    public Map<String, Object> selectTest(String ID_, String pwd) throws SQLException {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> deptList = firstService.selectTest(ID_);
        int total = firstService.countTest();
        result.put("result", deptList);
        result.put("date", new Date());
        result.put("total", total);
        result.put("success", true);

        return result;
    }

    @GetMapping(value = "selectAstncTest")
    @ResponseBody
    //@LogAnno(operateType = "查询Test")
    public Map<String, Object> selectAstncTest(String ID_, String pwd) throws SQLException, ExecutionException, InterruptedException {
        Map<String, Object> result = new HashMap<String, Object>();
        Future<List<Map<String, Object>>> deptList = firstService.selectAstncTest(ID_);
        int total = firstService.countTest();
        result.put("result", deptList.get());
        result.put("total", total);
        result.put("success", true);

        return result;
    }

    @PostMapping(value = "updateTest")
    @ResponseBody
    @LogAnno(operateType = "修改Test")
    public Map<String, Object> updateTest(String ID_, String CODE_, String NAME, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException {
        Map<String, Object> result = new HashMap<String, Object>();
        if (firstService.updateTest(ID_, CODE_, NAME) > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }

        return result;
    }

    @PostMapping(value = "deleteTest")
    @ResponseBody
    @LogAnno(operateType = "删除Test")
    public Map<String, Object> deleteTest(String ID_, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException {
        Map<String, Object> result = new HashMap<String, Object>();
        if (firstService.deleteTest(ID_) > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }

        return result;
    }

    @PostMapping(value = "insertTest")
    @ResponseBody
    @LogAnno(operateType = "添加Test")
    public Map<String, Object> insertTest(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> deptList = firstService.selectTest(null);
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
