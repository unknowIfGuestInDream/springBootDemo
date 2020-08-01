package com.tangl.demo.controller.redis;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.tangl.demo.redis.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.util.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: TangLiang
 * @date: 2020/8/1 12:02
 * @since: 1.0
 */
@Api(tags = "Redis测试")
@Controller
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private RedisService redisService;

    @ApiOperation("测试简单缓存")
    @RequestMapping(value = "/simpleTest", method = RequestMethod.GET)
    @ResponseBody
    public Map simpleTest() {
        Map result = new HashMap();
        String key = "redis:simple:" + "1001";
        redisService.set(key, "测试简单缓存",30);
        result.put("success", true);
        result.put("result", redisService.get(key));
        return result;
    }

    @ApiOperation("测试Hash结构的缓存")
    @RequestMapping(value = "/hashTest", method = RequestMethod.GET)
    @ResponseBody
    public Map hashTest() {
        Map result = new HashMap();
        String key = "redis:hash:" + "1002";
        Map<String, Object> value = new HashMap<>();
        value.put("id", "1");
        value.put("name", "唐三");
        redisService.hSetAll(key, value);
        result.put("success", true);
        result.put("result", redisService.hGetAll(key));
        return result;
    }

    @ApiOperation("测试Set结构的缓存")
    @RequestMapping(value = "/setTest", method = RequestMethod.GET)
    @ResponseBody
    public Map setTest() {
        Map result = new HashMap();
        String key = "redis:set:all";
        Map<String, Object> value = new HashMap<>();
        value.put("id", "1");
        value.put("name", "唐三");
        redisService.sAdd(key, value);
        result.put("success", true);
        result.put("result", redisService.sMembers(key));
        return result;
    }

    @ApiOperation("测试List结构的缓存")
    @RequestMapping(value = "/listTest", method = RequestMethod.GET)
    @ResponseBody
    public Map listTest() {
        String key = "redis:list:all";
        Map result = new HashMap();
        Map<String, Object> value = new HashMap<>();
        value.put("id", "1");
        value.put("name", "唐三");
        redisService.lPushAll(key, value);
        //redisService.lRemove(key, 1, value);
        result.put("success", true);
        result.put("result", redisService.lRange(key, 0, 3));
        return result;
    }
}
