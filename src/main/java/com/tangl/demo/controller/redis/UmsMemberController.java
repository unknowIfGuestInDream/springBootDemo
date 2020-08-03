package com.tangl.demo.controller.redis;

import com.tangl.demo.annotation.LogAnno;
import com.tangl.demo.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 会员登录注册管理Controller
 *
 * @author: TangLiang
 * @date: 2020/8/1 10:36
 * @since: 1.0
 */
@Controller
@Api(tags = "会员登录注册管理")
@RequestMapping("/sso")
public class UmsMemberController {
    @Autowired
    private UmsMemberService memberService;

    @LogAnno(operateType = "获取验证码")
    @ApiOperation("获取验证码")
    @RequestMapping(value = "/getAuthCode", method = RequestMethod.GET)
    @ResponseBody
    public Map getAuthCode(@RequestParam String telephone) {
        return memberService.generateAuthCode(telephone);
    }

    @LogAnno(operateType = "判断验证码是否正确")
    @ApiOperation("判断验证码是否正确")
    @RequestMapping(value = "/verifyAuthCode", method = RequestMethod.POST)
    @ResponseBody
    public Map updatePassword(@RequestParam String telephone,
                              @RequestParam String authCode) {
        return memberService.verifyAuthCode(telephone, authCode);
    }

    @LogAnno(operateType = "获取验证码RabbitMQ")
    @ApiOperation("获取验证码RabbitMQ")
    @RequestMapping(value = "/getAuthCodeRabbitMQ", method = RequestMethod.GET)
    @ResponseBody
    public Map sendMsgRabbitMQ(@RequestParam String telephone) {
        return memberService.generateAuthCodeRabbitMQ(telephone);
    }
}
