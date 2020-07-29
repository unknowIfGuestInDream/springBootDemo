package com.tangl.demo.controller.swagger;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: TangLiang
 * @date: 2020/7/29 10:50
 * @since: 1.0
 */
@RestController
@RequestMapping(path = "/user")
@Api(tags = "用户")
public class UserController {

    /**
     * 获取用户信息
     *
     * @param userName
     * @return
     */
    @GetMapping("/getUserInfo")
    @ApiOperation(value = "获取用户信息", notes = "获取之后返回对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", dataType = "String", paramType = "query", example = "xingguo"),
            @ApiImplicitParam(name = "age", value = "用户名", dataType = "int", paramType = "query", example = "17")})
    @ApiResponse(code = 400, message = "参数没有填好")
    public Map getUserInfo(@RequestParam(name = "userName", defaultValue = "张三") String userName,
                           @RequestParam(name = "age") Integer age) {
        Map result = new HashMap();
        User user = new User();
        user.setUserName(userName);
        user.setAge(age);
        result.put("result", user);
        return result;
    }
//    paramType：参数放在哪个地方
//            · header --> 请求参数的获取：@RequestHeader
//            · query --> 请求参数的获取：@RequestParam
//            · path（用于restful接口）--> 请求参数的获取：@PathVariable
//            · body（不常用）
//            · form（不常用）
}