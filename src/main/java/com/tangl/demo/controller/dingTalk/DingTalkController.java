package com.tangl.demo.controller.dingTalk;

import com.tangl.demo.common.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author: TangLiang
 * @date: 2020/12/12 0:11
 * @since: 1.0
 */
@Controller
@Api(tags = "钉钉报警业务")
public class DingTalkController {

    @GetMapping("dingTalk")
    @ApiOperation("钉钉捕捉异常报警")
    public Map dingTalk() {
        int i = 1 / 0;
        return AjaxResult.success(i);
    }

    @GetMapping("CommonException")
    @ApiOperation("钉钉捕捉异常报警")
    public Map CommonException() {
        throw new CommonException(504,"捕捉到未知异常");
    }

    @GetMapping("HttpMessageNotReadableException")
    @ApiOperation("钉钉捕捉异常报警")
    public Map HttpMessageNotReadableException() {
        throw new HttpMessageNotReadableException("abc", new HttpInputMessage() {
            @Override
            public InputStream getBody() throws IOException {
                return null;
            }

            @Override
            public HttpHeaders getHeaders() {
                return null;
            }
        });
    }

}
