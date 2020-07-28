package com.tangl.demo.test.HttpClient;

import org.apache.commons.io.FileUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: TangLiang
 * @date: 2020/7/28 20:01
 * @since: 1.0
 */
public class DoPOSTParam {
    public static void main(String[] args) throws Exception {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建http POST请求
        HttpPost httpPost = new HttpPost("http://www.oschina.net/search");
        // 设置2个post参数，一个是scope、一个是q
        List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
        parameters.add(new BasicNameValuePair("scope", "project"));
        parameters.add(new BasicNameValuePair("q", "java"));
        // 构造一个form表单式的实体
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(formEntity);
        //伪装浏览器
        httpPost.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                //内容写入文件
                FileUtils.writeStringToFile(new File("E:\\devtest\\oschina-param.html"), content, "UTF-8");
                System.out.println("内容长度："+content.length());
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
    }
}
