package com.tangl.demo.controller.SMS;

import com.alibaba.fastjson.JSONObject;
import com.tangl.demo.annotation.LogAnno;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 短信测试
 * https://office.ucpaas.com/
 *
 * @author: TangLiang
 * @date: 2020/8/2 10:53
 * @since: 1.0
 */
@RestController
@RequestMapping("/sms")
public class SMSController {
    @Autowired
    private CloseableHttpClient httpClient;
    @Autowired
    private RequestConfig requestConfig;

    /**
     * 单发短信测试
     *
     * @Author: Sans
     * @CreateTime: 2019/4/2 10:06
     */
    @RequestMapping(value = "/sendsmsTest", method = RequestMethod.GET)
    @LogAnno(operateType = "单发短信")
    public String sendsmsTest() throws URISyntaxException {
        //单发短信API
        JSONObject jsonObject = new JSONObject();
        //基础配置，在开发平台认证后获取
        jsonObject.put("appid", "910106016f0541e6913a2d72a0621754");
        jsonObject.put("sid", "fac23d51fd052b72e783af972f022ca3");
        jsonObject.put("token", "7e5b9bcdf62**********5aba6d8621a");
        //模板ID，在开发平台创建模板对应的模板ID
        jsonObject.put("templateid", "559191");
        //模板对应的参数，参数之间拼接用逗号作为间隔符
        jsonObject.put("param", "123456,120");
        //要发送的手机号
        jsonObject.put("mobile", "15566418409");
        //用户透传ID，随状态报告返回,可以不填写
        jsonObject.put("uid", "15619843468");
        String json = JSONObject.toJSONString(jsonObject);

        HttpPost httpPost = new HttpPost("https://open.ucpaas.com/ol/sms/sendsms");
        StringEntity entity = new StringEntity(json, "UTF-8");
        // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        // 响应模型
        CloseableHttpResponse response = null;
        String content = "";
        try {
            httpPost.setConfig(requestConfig);
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            org.apache.http.HttpEntity responseEntity = response.getEntity();

            System.out.println("响应状态为:" + response.getStatusLine());
            //主动设置编码 防止响应乱码
            content = EntityUtils.toString(response.getEntity(), "UTF-8");
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println("响应内容为:" + content);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 群发短信测试
     *
     * @Author: Sans
     * @CreateTime: 2019/4/2 11:23
     */
    @RequestMapping(value = "/sendBatchsmsTest", method = RequestMethod.GET)
    @LogAnno(operateType = "群发短信")
    public String sendBatchsmsTest() {
        //群发短信API
        JSONObject jsonObject = new JSONObject();
        //基础配置，在开发平台认证后获取
        jsonObject.put("sid", "ad024f8****************05d1614");
        jsonObject.put("token", "5ddbf62d4d****************e27402c");
        jsonObject.put("appid", "0ceaca4708****************76ec45f");
        //模板ID，在开发平台创建模板对应的模板ID
        jsonObject.put("templateid", "432116");
        //模板对应的参数，参数之间拼接用逗号作为间隔符
        jsonObject.put("param", "1315,500");
        //群发多个手机号之间要用逗号作为间隔符
        jsonObject.put("mobile", "用户的手机号A,用户的手机号B");
        //用户透传ID，随状态报告返回,可以不填写
        jsonObject.put("uid", "");
        String json = JSONObject.toJSONString(jsonObject);

        HttpPost httpPost = new HttpPost("https://open.ucpaas.com/ol/sms/sendsms_batch");
        StringEntity entity = new StringEntity(json, "UTF-8");
        // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        // 响应模型
        CloseableHttpResponse response = null;
        String content = "";
        try {
            httpPost.setConfig(requestConfig);
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            org.apache.http.HttpEntity responseEntity = response.getEntity();

            System.out.println("响应状态为:" + response.getStatusLine());
            //主动设置编码 防止响应乱码
            content = EntityUtils.toString(response.getEntity(), "UTF-8");
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println("响应内容为:" + content);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
