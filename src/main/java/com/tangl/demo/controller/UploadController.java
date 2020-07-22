package com.tangl.demo.controller;

import com.tangl.demo.annotation.LogAnno;
import lombok.extern.log4j.Log4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: TangLiang
 * @date: 2020/7/21 13:40
 * @since: 1.0
 */
@Log4j
@RestController
public class UploadController {

    //,produces = "application/json;charset=UTF-8"
    @PostMapping(value = "uploadFiles")
    @LogAnno(operateType = "多文件上传")
    public void uploadFiles(@RequestParam(name = "file") MultipartFile[] multipartFiles, String text, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException {
        Map<String, Object> result = new HashMap<String, Object>();
        log.info("进入uploadFiles方法");
        for (int i = 0; i < multipartFiles.length; i++) {

            //生成文件名
            String fileName = multipartFiles[i].getOriginalFilename();
            //生成路径
            File file = new File("E://uploadTest//" + fileName);
            //按照指定路径创建该文件！
            file.mkdirs();
            //文件上传到服务器
            try {
                multipartFiles[i].transferTo(file);
            } catch (IOException e) {
                result.put("success", false);
            }
        }
        result.put("success", true);

        //return result;
    }

    @GetMapping(value = "downloadFile")
    @ResponseBody
    @LogAnno(operateType = "文件下载")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException, IOException {
        log.info("进入downloadFile方法");
        String filename = "下载测试.txt";
        File file = new File("E://uploadTest//" + filename);
        if (file.exists()) {

            //编码
            String agent = (String) request.getHeader("USER-AGENT");
            if (agent != null && agent.toLowerCase().indexOf("firefox") > 0) {// 兼容火狐中文文件名下载
                filename = "=?UTF-8?B?" + (new String(Base64.encodeBase64(filename.getBytes("UTF-8")))) + "?=";
            } else {
                filename = java.net.URLEncoder.encode(filename, "UTF-8");
            }
            //response.setHeader("Content-disposition", "attachment; filename=" + new String(filename.getBytes("UTF-8"), "ISO8859-1"));
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                bis.close();
                fis.close();
            }
        }
    }
}
