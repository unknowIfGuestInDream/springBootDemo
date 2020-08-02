package com.tangl.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.tangl.demo.redis.StringRedisService;
import com.tangl.demo.service.UmsMemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author: TangLiang
 * @date: 2020/8/1 10:32
 * @since: 1.0
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {
    @Autowired
    private StringRedisService redisService;
    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    @Value("${redis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;

    @Override
    public Map generateAuthCode(String telephone) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        //验证码绑定手机号并存储到redis
        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE + telephone, sb.toString());
        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE + telephone, AUTH_CODE_EXPIRE_SECONDS);

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4G6ZehpPRL1MT59QoMoZ", "yFpmuwHsuF630ua1iUBCDa0jcJh0Hy");
        IAcsClient client = new DefaultAcsClient(profile);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", sb.toString());
        String json = JSONObject.toJSONString(jsonObject);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "15566418409");
        request.putQueryParameter("SignName", "ABC商城");
        request.putQueryParameter("TemplateCode", "SMS_198667593");
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

        Map result = new HashMap();
        result.put("success", true);
        return result;
    }


    //对输入的验证码进行校验
    @Override
    public Map verifyAuthCode(String telephone, String authCode) {
        Map result = new HashMap();
        if (StringUtils.isEmpty(authCode)) {
            result.put("success", false);
            result.put("message", "请输入验证码");
            return result;
        }
        String realAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        boolean accurate = authCode.equals(realAuthCode);
        if (accurate) {
            result.put("success", true);
            result.put("message", "验证码校验成功");
        } else {
            result.put("success", false);
            result.put("message", "验证码不正确");
        }
        return result;
    }
}
