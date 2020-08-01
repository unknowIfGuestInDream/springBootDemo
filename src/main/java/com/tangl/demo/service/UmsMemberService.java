package com.tangl.demo.service;

import java.util.Map;

/**
 * 会员管理Service
 *
 * @author: TangLiang
 * @date: 2020/8/1 10:32
 * @since: 1.0
 */
public interface UmsMemberService {
    /**
     * 生成验证码
     */
    Map generateAuthCode(String telephone);

    /**
     * 判断验证码和手机号码是否匹配
     */
    Map verifyAuthCode(String telephone, String authCode);
}
