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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * mongodb视图操作
 *
 * @author: TangLiang
 * @date: 2020/12/20 10:06
 * @since: 1.0
 */
@RestController
@Api(tags = "mongodb视图操作")
@RequestMapping("/mongodb/view")
@Validated
public class ViewController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @ApiOperation("创建视图")
    @RequestMapping(value = "/createView", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "newViewName", value = "视图名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "collectionName", value = "集合名", dataType = "String", paramType = "query")
    })
    @LogAnno(operateType = "创建视图")
    public Object createView(String newViewName, String collectionName) {
        // 定义视图的管道,可是设置视图显示的内容多个筛选条件
        List<Bson> pipeline = new ArrayList<>();
        // 设置条件，用于筛选集合中的文档数据，只有符合条件的才会映射到视图中
        pipeline.add(Document.parse("{\"$match\":{\"operateType\":\"查询Test\"}}"));
        // 执行创建视图
        mongoTemplate.getDb().createView(newViewName, collectionName, pipeline);
        // 检测新的集合是否存在，返回创建结果
        return mongoTemplate.collectionExists(newViewName) ? "创建视图成功" : "创建视图失败";
    }

    @ApiOperation("删除视图")
    @RequestMapping(value = "/dropView", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "viewName", value = "视图名", dataType = "String", paramType = "query")
    })
    @LogAnno(operateType = "删除视图")
    public Object dropView(String viewName) {
        // 检测视图是否存在
        if (mongoTemplate.collectionExists(viewName)) {
            // 删除视图
            mongoTemplate.getDb().getCollection(viewName).drop();
            return "删除视图成功";
        }
        // 检测新的集合是否存在，返回创建结果
        return !mongoTemplate.collectionExists(viewName) ? "删除视图成功" : "删除视图失败";
    }
}
