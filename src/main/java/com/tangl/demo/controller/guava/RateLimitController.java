package com.tangl.demo.controller.guava;

import cn.hutool.core.lang.Dict;
import com.tangl.demo.annotation.LogAnno;
import com.tangl.demo.annotation.RateLimiter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 限流
 *
 * @author: TangLiang
 * @date: 2020/12/18 21:43
 * @since: 1.0
 */
@RestController
@RequestMapping("/rateLimit")
@Api(tags = "guava的RateLimit限流")
@Slf4j
public class RateLimitController {

    @ApiOperation("限流请求")
    @RateLimiter(value = 1.0, timeout = 300)
    @GetMapping("/test1")
    @LogAnno(operateType = "限流请求")
    public Dict test1() {
        log.info("【test1】被执行了。。。。。");
        return Dict.create().set("msg", "hello,world!").set("description", "别想一直看到我，不信你快速刷新看看~");
    }

    @ApiOperation("不限流请求")
    @GetMapping("/test2")
    @LogAnno(operateType = "不限流请求")
    public Dict test2() {
        log.info("【test2】被执行了。。。。。");
        return Dict.create().set("msg", "hello,world!").set("description", "我一直都在，卟离卟弃");
    }
}
