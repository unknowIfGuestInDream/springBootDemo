package com.tangl.demo.controller.minIO;

import com.tangl.demo.annotation.LogAnno;
import com.tangl.demo.common.AjaxResult;
import com.tangl.demo.util.MinioUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: TangLiang
 * @date: 2020/9/4 10:31
 * @since: 1.0
 */
@Api(tags = "MinioController", description = "MinIO对象存储管理")
@Controller
@RequestMapping("/minio")
@Slf4j
public class MinioController {

    @Autowired
    private MinioUtil minioUtil;

    @Value("${minio.endpoint}")
    private String ENDPOINT;
    @Value("${minio.bucketName}")
    private String BUCKET_NAME;
    @Value("${minio.accessKey}")
    private String ACCESS_KEY;
    @Value("${minio.secretKey}")
    private String SECRET_KEY;

    @ApiOperation("文件上传")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    //@LogAnno(operateType = "文件上传minio")
    public AjaxResult upload(@RequestParam("file") MultipartFile file) {
        try {
            minioUtil.makeBucket(BUCKET_NAME);
            String filename = file.getOriginalFilename();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            // 设置存储对象名称
            String objectName = sdf.format(new Date()) + "/" + filename;
            // 使用putObject上传一个文件到存储桶中
            minioUtil.putObject(BUCKET_NAME,objectName,file.getInputStream());
            log.info("文件上传成功!");
            return AjaxResult.success("url: " + ENDPOINT + "/" + BUCKET_NAME + "/" + objectName + " name: " + filename);
        } catch (Exception e) {
            log.info("上传发生错误: {}！", e.getMessage());
        }
        return AjaxResult.error();
    }

    //参数格式
    // fileUrl=http://127.0.0.1:9000/springbd/20200904/%E6%A3%80%E4%BF%AE%E5%86%99%E5%AE%9E%E9%97%AE%E9%A2%980812(1).doc
    @ApiOperation("文件下载")
    @GetMapping("/downloadFile")
    @LogAnno(operateType = "minio文件下载")
    public void downloadFile(String fileUrl, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (StringUtils.isBlank(fileUrl)) {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            String data = "文件下载失败";
            OutputStream ps = response.getOutputStream();
            ps.write(data.getBytes("UTF-8"));
            return;
        }

        // 拿到文件路径
        String url = fileUrl.split("9000/")[1];
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        minioUtil.downloadFile(BUCKET_NAME, fileName, null, response);
    }

    @ApiOperation("文件删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @LogAnno(operateType = "minio文件删除")
    public AjaxResult delete(@RequestParam("objectName") String objectName) {
        try {
            minioUtil.removeObject(BUCKET_NAME, objectName);
            return AjaxResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error();
    }

}
