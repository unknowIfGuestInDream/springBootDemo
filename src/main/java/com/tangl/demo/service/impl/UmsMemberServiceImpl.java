package com.tangl.demo.service.impl;

import com.tangl.demo.redis.RedisService;
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
    private RedisService redisService;
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
