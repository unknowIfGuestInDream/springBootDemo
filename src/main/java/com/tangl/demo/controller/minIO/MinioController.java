package com.tangl.demo.controller.minIO;

import com.tangl.demo.annotation.LogAnno;
import com.tangl.demo.common.AjaxResult;
import io.minio.MinioClient;
import io.minio.policy.PolicyType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
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
    private MinioClient minioClient;

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
            boolean isExist = minioClient.bucketExists(BUCKET_NAME);
            if (isExist) {
                log.info("存储桶已经存在！");
            } else {
                //创建存储桶并设置只读权限
                minioClient.makeBucket(BUCKET_NAME);
                minioClient.setBucketPolicy(BUCKET_NAME, "*.*", PolicyType.READ_ONLY);
            }
            String filename = file.getOriginalFilename();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            // 设置存储对象名称
            String objectName = sdf.format(new Date()) + "/" + filename;
            // 使用putObject上传一个文件到存储桶中
            minioClient.putObject(BUCKET_NAME, objectName, file.getInputStream(), file.getContentType());
            log.info("文件上传成功!");
            return AjaxResult.success("url: " + ENDPOINT + "/" + BUCKET_NAME + "/" + objectName + " name: " + filename);
        } catch (Exception e) {
            log.info("上传发生错误: {}！", e.getMessage());
        }
        return AjaxResult.error();
    }

    //http://127.0.0.1/tl/minio/downloadFile?
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
        //编码
        String agent = (String) request.getHeader("USER-AGENT");
        if (agent != null && agent.toLowerCase().indexOf("firefox") > 0) {// 兼容火狐中文文件名下载
            fileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(fileName.getBytes("UTF-8")))) + "?=";
        } else {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        }

        try {

            // 获取文件对象
            InputStream object = minioClient.getObject(BUCKET_NAME, url.substring(url.indexOf("/") + 1));
            byte buf[] = new byte[1024];
            int length = 0;
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");

            OutputStream outputStream = response.getOutputStream();
            // 输出文件
            while ((length = object.read(buf)) > 0) {
                outputStream.write(buf, 0, length);
            }
            // 关闭输出流
            outputStream.close();
        } catch (Exception ex) {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            String data = "文件下载失败";
            OutputStream ps = response.getOutputStream();
            ps.write(data.getBytes("UTF-8"));
        }
    }

    @ApiOperation("文件删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @LogAnno(operateType = "minio文件删除")
    public AjaxResult delete(@RequestParam("objectName") String objectName) {
        try {
            minioClient.removeObject(BUCKET_NAME, objectName);
            return AjaxResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error();
    }

}
