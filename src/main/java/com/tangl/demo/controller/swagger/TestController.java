package com.tangl.demo.controller.swagger;

import com.tangl.demo.annotation.LogAnno;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * swagger 用户测试方法
 *
 * @author ruoyi
 */
@Api("用户信息管理")
@RestController
@RequestMapping("/test/user")
public class TestController {
    private final static Map<Integer, UserEntity> users = new LinkedHashMap<Integer, UserEntity>();

    {
        users.put(1, new UserEntity(1, "admin", "admin123", "15888888888"));
        users.put(2, new UserEntity(2, "ry", "admin123", "15666666666"));
    }

    @ApiOperation("获取用户列表")
    @GetMapping("/list")
    @LogAnno(operateType = "获取用户列表")
    public Map userList() {
        Map map = new HashMap();
        List<UserEntity> userList = new ArrayList<UserEntity>(users.values());
        map.put("success", true);
        map.put("userList", userList);
        return map;
    }

    @ApiOperation("获取用户详细")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "path")
    @GetMapping("/{userId}")
    @LogAnno(operateType = "获取用户详细")
    public Map getUser(@PathVariable Integer userId) {
        Map map = new HashMap();
        if (!users.isEmpty() && users.containsKey(userId)) {
            map.put("success", true);
            map.put("userId", users.get(userId));
        } else {
            map.put("success", false);
            map.put("message", "用户不存在");
        }
        return map;
    }

    @ApiOperation("新增用户")
    @ApiImplicitParam(name = "userEntity", value = "新增用户信息", dataType = "UserEntity")
    @PostMapping("/save")
    @LogAnno(operateType = "新增用户")
    public Map save(UserEntity user) {
        Map map = new HashMap();
        if (user == null || user.getUserId() == null) {
            map.put("success", false);
            map.put("message", "用户ID不能为空");
        } else {
            map.put("success", true);
            map.put(user.getUserId(), user);
        }
        return map;
    }

    @ApiOperation("更新用户")
    @ApiImplicitParam(name = "userEntity", value = "新增用户信息", dataType = "UserEntity")
    @PutMapping("/update")
    @LogAnno(operateType = "更新用户")
    public Map update(UserEntity user) {
        Map map = new HashMap();
        if (user == null || user.getUserId() == null) {
            map.put("success", false);
            map.put("message", "用户ID不能为空");
        } else if (users.isEmpty() || !users.containsKey(user.getUserId())) {
            map.put("success", false);
            map.put("message", "用户不存在");
        } else {
            map.put("success", true);
            map.put(user.getUserId(), user);
        }
        users.remove(user.getUserId());
        return map;
    }

    @ApiOperation("删除用户信息")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "path")
    @DeleteMapping("/{userId}")
    @LogAnno(operateType = "删除用户信息")
    public Map delete(@PathVariable Integer userId) {
        Map map = new HashMap();
        if (!users.isEmpty() && users.containsKey(userId)) {
            users.remove(userId);
            map.put("success", true);
        } else {
            map.put("success", false);
            map.put("message", "用户不存在");
        }
        return map;
    }
}

@ApiModel("用户实体")
class UserEntity {
    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("用户手机")
    private String mobile;

    public UserEntity() {

    }

    public UserEntity(Integer userId, String username, String password, String mobile) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.mobile = mobile;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
