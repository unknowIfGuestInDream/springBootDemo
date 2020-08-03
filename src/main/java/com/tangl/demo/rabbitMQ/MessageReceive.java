package com.tangl.demo.rabbitMQ;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author: TangLiang
 * @date: 2020/8/3 15:20
 * @since: 1.0
 */
@Component
public class MessageReceive {

    @Value("${aliyun.sms.accessKeyId}")
    private String ACCESSKEY_ID;
    @Value("${aliyun.sms.accessKeySecret}")
    private String ACCESSKEY_SECRET;
    @Value("${aliyun.sms.template_code}")
    private String TEMPLATE_CODE;
    @Value("${aliyun.sms.sign_name}")
    private String SIGN_NAME;

    //监听queueSms队列
    @RabbitListener(queuesToDeclare = @Queue("queueSms"))
    public void receiveMessage(Message message) throws UnsupportedEncodingException {
        String msg = new String(message.getBody(), "utf-8");
        Map<String, String> map = JSON.parseObject(msg, Map.class);
        String phone = map.get("phone");
        String code = map.get("code");
        try {
            DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESSKEY_ID, ACCESSKEY_SECRET);
            IAcsClient client = new DefaultAcsClient(profile);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", code);
            String json = JSONObject.toJSONString(jsonObject);

            CommonRequest request = new CommonRequest();
            request.setSysMethod(MethodType.POST);
            request.setSysDomain("dysmsapi.aliyuncs.com");
            request.setSysVersion("2017-05-25");
            request.setSysAction("SendSms");
            request.putQueryParameter("RegionId", "cn-hangzhou");
            request.putQueryParameter("PhoneNumbers", phone);
            request.putQueryParameter("SignName", SIGN_NAME);
            request.putQueryParameter("TemplateCode", TEMPLATE_CODE);
            request.putQueryParameter("TemplateParam", json);
            try {
                CommonResponse response = client.getCommonResponse(request);
                System.out.println(response.getData());
                System.out.println(response.getHttpResponse());
            } catch (ServerException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
