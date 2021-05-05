package com.tangl.demo.controller.mongo;

import com.tangl.demo.annotation.LogAnno;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * MongoDB RunCommand 命令操作
 *
 * @author: TangLiang
 * @date: 2020/12/20 10:28
 * @since: 1.0
 */
@RestController
@Api(tags = "mongodb RunCommand 命令操作")
@RequestMapping("/mongodb/runCommand")
public class RunCommandController {
    @Autowired
    private MongoTemplate mongoTemplate;

    //详情可以查看：https://docs.mongodb.com/manual/reference/command/
    @ApiOperation("执行 mongoDB 自定义命令")
    @RequestMapping(value = "/runCommand", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jsonCommand", value = "自定义命令", dataType = "String", paramType = "query")})
    @LogAnno(operateType = "执行 mongoDB 自定义命令")
    public Object runCommand(String jsonCommand) {
        // 自定义命令
        //String jsonCommand = "{\"buildInfo\":1}";
        // 将 JSON 字符串解析成 MongoDB 命令
        Bson bson = Document.parse(jsonCommand);
        // 执行自定义命令
        return mongoTemplate.getDb().runCommand(bson);
    }

    /**
     * https://www.docs4dev.com/docs/zh/mongodb/v3.6/reference/reference-command-connPoolStats.html
     */
    @ApiOperation("获取MongoDB Collection信息")
    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jsonCommand", value = "自定义命令", dataType = "String", paramType = "query")})
    @LogAnno(operateType = "获取MongoDB Collection信息")
    public Object getInfo(String jsonCommand) {
        // 执行自定义命令
        return mongoTemplate.getDb().runCommand(new Document("collStats", "collectionName"));
    }
}
