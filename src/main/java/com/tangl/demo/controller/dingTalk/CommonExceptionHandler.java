package com.tangl.demo.controller.dingTalk;

import com.tangl.demo.common.AjaxResult;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @author: TangLiang
 * @date: 2020/12/12 0:42
 * @since: 1.0
 */
@ControllerAdvice
@Component
@Slf4j
public class CommonExceptionHandler {

    @Autowired
    private DingTalkUtils dingTalkUtils;

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Map handle(Exception exception) throws ApiException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        if (exception instanceof CommonException) {
            log.error(exception.getMessage(), exception);
            CommonException e = (CommonException) exception;
            log.error("捕获到异常,异常代码为{},异常信息为{}", e.getErrCode(), e.getErrMsg());
            //发送异常信息到钉钉机器人
            globalMsg(exception);
            return AjaxResult.error(e.getErrMsg());
        } else if (exception instanceof HttpMessageNotReadableException) {
            log.error("前台传入JSON格式错误");
            //发送异常信息到钉钉机器人
            globalMsg(exception);
            return AjaxResult.error("前台传入JSON格式错误");
        } else {
            exception.printStackTrace();
            return AjaxResult.error(exception.getMessage());
        }
    }

    //可单独提出来作为工具类
    public void globalMsg(Exception exception) throws ApiException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        exception.printStackTrace(pw);
        String stackTraceString = sw.getBuffer().toString();
        dingTalkUtils.sendMsg(stackTraceString);
    }
}