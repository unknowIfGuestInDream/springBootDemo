package com.tangl.demo.aliyun;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.tangl.demo.util.uuid.IdUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: TangLiang
 * @date: 2020/8/2 20:06
 * @since: 1.0
 */
@SpringBootTest
public class SMSTest {

    @Autowired
    private CloseableHttpClient httpClient;
    @Autowired
    private RequestConfig requestConfig;

    //短信功能
    @Test
    public void sendAliyunSMS() {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4G6ZehpPRL1MT59QoMoZ", "yFpmuwHsuF630ua1iUBCDa0jcJh0Hy");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "15566418409");
        request.putQueryParameter("SignName", "ABC商城");
        request.putQueryParameter("TemplateCode", "SMS_198667593");
        request.putQueryParameter("TemplateParam", "{\"code\":\"978844\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    //易源数据-QQ号测凶吉
    @Test
    public void qqfortuneTelling() throws URISyntaxException {
        // 将参数放入键值对类NameValuePair中,再放入集合中
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("qq", "2960052476"));
        // 设置uri信息,并将参数集合放入uri;
        // 注:这里也支持一个键值对一个键值对地往里面放setParameter(String key, String value)
        URI uri = new URIBuilder().setScheme("http").setHost("qqtest.market.alicloudapi.com").setPath("/qqTest")
                .setParameters(params).build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet(uri);
        // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
        httpGet.setHeader("Authorization", "APPCODE " + "01b182dd0481438388ba01b95630332d");
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(5000)
                    // 设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(5000)
                    // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(5000)
                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(true).build();
            // 将上面的配置信息 运用到这个Get请求里
            httpGet.setConfig(requestConfig);
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            //主动设置编码 防止响应乱码
            String content = EntityUtils.toString(response.getEntity(), "UTF-8");
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println("响应内容为:" + content);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    //企业知识图谱-手机号码归属地查询-艾科瑞特（iCREDIT）
    /*
    {
    "INPUT_MOBILE_NUMBER_STRING":"18888888888",          #输入手机号
    "EXTRACT_MOBILE_NUMBER_STRING":"18888888888",        #抽取手机号
    "STATUS":"OK",                                       #归属地识别状态
    "ENTITY":{                                           #手机号实体信息
        "MOBILE_NUMBER":"18888888888",                   #输入手机号
        "MOBILE_NUMBER_PREFIX":"1888888",                #手机号前缀
        "PROVINCE":"北京",                               #手机号所属省份
        "CITY":"北京",                                   #手机号所属城市
        "ISP":"中国移动",                                #手机号所属运营商
        "AREA_CODE":"010",                               #手机号所属城市区号
        "POST_CODE":"100000",                            #手机号所属城市邮编
        "ADCODE":"110100"                                #手机号所属城市国家行政编码
    }
}
     */
    @Test
    public void telphoneFrom() throws URISyntaxException {
        //API产品路径
        // 将参数放入键值对类NameValuePair中,再放入集合中
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("MOBILE_NUMBER", "13998458603"));
        // 设置uri信息,并将参数集合放入uri;
        // 注:这里也支持一个键值对一个键值对地往里面放setParameter(String key, String value)
        URI uri = new URIBuilder().setScheme("http").setHost("aiphone.market.alicloudapi.com").setPath("/ai_mobile_number_belong_to_china/v1")
                .setParameters(params).build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet(uri);
        // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
        httpGet.setHeader("Authorization", "APPCODE " + "01b182dd0481438388ba01b95630332d");
        httpGet.setHeader("X-Ca-Nonce", IdUtils.fastUUID());
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(5000)
                    // 设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(5000)
                    // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(5000)
                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(true).build();
            // 将上面的配置信息 运用到这个Get请求里
            httpGet.setConfig(requestConfig);
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            //主动设置编码 防止响应乱码
            String content = EntityUtils.toString(response.getEntity(), "UTF-8");
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println("响应内容为:" + content);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

}
