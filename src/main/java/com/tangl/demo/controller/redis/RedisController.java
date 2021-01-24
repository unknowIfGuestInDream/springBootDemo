package com.tangl.demo.controller.redis;

import com.tangl.demo.annotation.LogAnno;
import com.tangl.demo.redis.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

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
    @Autowired
    private CacheManager redisCacheManager;

    @ApiOperation("获取缓存")
    @RequestMapping(value = "/getCache", method = RequestMethod.GET)
    @ResponseBody
    @LogAnno(operateType = "获取缓存")
    public Map getCache() {
        Map result = new HashMap();
        result.put("success", true);
        result.put("result", redisCacheManager.getCacheNames());
        return result;
    }

    @ApiOperation("获取某个缓存")
    @RequestMapping(value = "/getCacheByName", method = RequestMethod.GET)
    @ResponseBody
    //@LogAnno(operateType = "获取某个缓存")
    public RedisCache getCacheByName(String name) {
//        Map result = new HashMap();
//        result.put("success", true);
//        result.put("result", redisCacheManager.getCache(name));
        return (RedisCache) redisCacheManager.getCache(name);
    }

    @ApiOperation("获取模糊缓存")
    @RequestMapping(value = "/gets", method = RequestMethod.GET)
    @ResponseBody
    @LogAnno(operateType = "获取模糊缓存")
    public Map gets(String name) {
        Map result = new HashMap();
        result.put("success", true);
        result.put("result", redisService.gets(name));
        return result;
    }

    @ApiOperation("测试简单缓存")
    @RequestMapping(value = "/simpleTest", method = RequestMethod.GET)
    @ResponseBody
    @LogAnno(operateType = "测试简单缓存")
    public Map simpleTest() {
        Map result = new HashMap();
        String key = "redis:simple:" + "1001";
        redisService.set(key, 1000);
        result.put("success", true);
        result.put("result", redisService.get(key));
        return result;
    }

    @ApiOperation("获取简单缓存")
    @RequestMapping(value = "/getSimpleTest", method = RequestMethod.GET)
    @ResponseBody
    @LogAnno(operateType = "获取简单缓存")
    public Map getSimpleTest(String name) {
        Map result = new HashMap();
        result.put("success", true);
        result.put("result", redisService.get(name));
        return result;
    }

    @ApiOperation("测试简单缓存递增")
    @RequestMapping(value = "/simpleTestIns", method = RequestMethod.GET)
    @ResponseBody
    @LogAnno(operateType = "测试简单缓存递增")
    public Map simpleTestIns() {
        Map result = new HashMap();
        String key = "redis:simple:" + "1001";
        redisService.incr(key, 1);
        result.put("success", true);
        result.put("result", redisService.get(key));
        return result;
    }

    @ApiOperation("测试Hash结构的缓存")
    @RequestMapping(value = "/hashTest", method = RequestMethod.GET)
    @ResponseBody
    @LogAnno(operateType = "测试Hash结构的缓存")
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

    @ApiOperation("测试Hash结构的缓存&添加一个key")
    @RequestMapping(value = "/hashTestKey", method = RequestMethod.GET)
    @ResponseBody
    @LogAnno(operateType = "测试Hash结构的缓存&添加一个key")
    public Map hashTestKey() {
        Map result = new HashMap();
        String key = "redis:hash:" + "1002";
        redisService.hSet(key, "age", 18);
        result.put("success", true);
        result.put("result", redisService.hGetAll(key));
        return result;
    }

    @ApiOperation("测试Set结构的缓存")
    @RequestMapping(value = "/setTest", method = RequestMethod.GET)
    @ResponseBody
    @LogAnno(operateType = "测试Set结构的缓存")
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
    @LogAnno(operateType = "测试List结构的缓存")
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

    @ApiOperation("测试List结构的缓存&根据索引获取")
    @RequestMapping(value = "/listTestIndex", method = RequestMethod.GET)
    @ResponseBody
    @LogAnno(operateType = "测试List结构的缓存&根据索引获取")
    public Map listTestIndex() {
        String key = "redis:list:all";
        Map result = new HashMap();
        Map<String, Object> value = new HashMap<>();
        value.put("id", "3");
        value.put("name", "唐三");
        redisService.lPush(key, value);
        result.put("success", true);
        result.put("size", redisService.lSize(key));
        result.put("res", redisService.lIndex(key, 3));
        result.put("result", redisService.lRange(key, 0, 3));
        return result;
    }
}
