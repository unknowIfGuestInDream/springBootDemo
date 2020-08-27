package com.tangl.demo.controller.mongo;

import com.tangl.demo.Document.User;
import com.tangl.demo.service.impl.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: TangLiang
 * @date: 2020/8/27 17:38
 * @since: 1.0
 */
@RestController("mongoUser")
@Api(tags = "mongo用户管理")
@RequestMapping("/mongouser")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @ApiOperation("查询用户")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    @ApiOperation("新建用户")
    public User createUser(User user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("修改用户")
    public void updateUser(@PathVariable String id, User user) {
        userService.updateUser(id, user);
    }

    /**
     * 根据用户 id查找
     * 存在返回，不存在返回 null
     */
    @ApiOperation("获得用户")
    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userService.getUser(id).orElse(null);
    }

    /**
     * 根据年龄段来查找
     */
    @ApiOperation("根据年龄段来查找用户")
    @GetMapping("/age/{from}/{to}")
    public List<User> getUserByAge(@PathVariable Integer from, @PathVariable Integer to) {
        return userService.getUserByAge(from, to);
    }

    /**
     * 根据用户名查找
     */
    @GetMapping("/name/{name}")
    @ApiOperation("根据用户名查找用户")
    public List<User> getUserByName(@PathVariable String name) {
        return userService.getUserByName(name);
    }

    /**
     * 根据用户描述模糊查找
     */
    @ApiOperation("根据用户描述模糊查找用户")
    @GetMapping("/description/{description}")
    public List<User> getUserByDescription(@PathVariable String description) {
        return userService.getUserByDescription(description);
    }

    /**
     * 根据多个检索条件查询
     */
    @ApiOperation("根据多个检索条件查询用户")
    @GetMapping("/condition")
    public Page<User> getUserByCondition(int size, int page, User user) {
        return userService.getUserByCondition(size, page, user);
    }

}
