package com.tangl.demo.controller.mongo;

import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.tangl.demo.annotation.LogAnno;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * mongodb索引操作
 *
 * @author: TangLiang
 * @date: 2020/12/20 10:16
 * @since: 1.0
 */
@RestController
@Api(tags = "mongodb索引操作")
@RequestMapping("/mongodb/index")
@Slf4j
public class IndexController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @ApiOperation("创建升序索引")
    @RequestMapping(value = "/createAscendingIndex", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "field", value = "字段名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "collectionName", value = "集合名", dataType = "String", paramType = "query")
    })
    @LogAnno(operateType = "创建升序索引")
    public Object createAscendingIndex(String field, String collectionName) {
        // 创建索引
        return mongoTemplate.getCollection(collectionName).createIndex(Indexes.ascending(field));
    }

    @ApiOperation("创建降序索引")
    @RequestMapping(value = "/createDescendingIndex", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "field", value = "字段名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "collectionName", value = "集合名", dataType = "String", paramType = "query")
    })
    @LogAnno(operateType = "创建降序索引")
    public Object createDescendingIndex(String field, String collectionName) {
        // 创建索引
        return mongoTemplate.getCollection(collectionName).createIndex(Indexes.descending(field));
    }

    @ApiOperation("创建升序复合索引")
    @RequestMapping(value = "/createCompositeIndex", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "field1", value = "字段名称1", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "field2", value = "字段名称2", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "collectionName", value = "集合名", dataType = "String", paramType = "query")
    })
    @LogAnno(operateType = "创建升序复合索引")
    public Object createCompositeIndex(String field1, String field2, String collectionName) {
        // 创建索引
        return mongoTemplate.getCollection(collectionName).createIndex(Indexes.ascending(field1, field2));
    }

    @ApiOperation("创建文字索引")
    @RequestMapping(value = "/createTextIndex", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "field", value = "字段名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "collectionName", value = "集合名", dataType = "String", paramType = "query")
    })
    @LogAnno(operateType = "创建文字索引")
    public Object createTextIndex(String field, String collectionName) {
        // 创建索引
        return mongoTemplate.getCollection(collectionName).createIndex(Indexes.text(field));
    }

    @ApiOperation("创建哈希索引")
    @RequestMapping(value = "/createHashIndex", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "field", value = "字段名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "collectionName", value = "集合名", dataType = "String", paramType = "query")
    })
    @LogAnno(operateType = "创建哈希索引")
    public Object createHashIndex(String field, String collectionName) {
        // 创建索引
        return mongoTemplate.getCollection(collectionName).createIndex(Indexes.hashed(field));
    }

    @ApiOperation("创建升序唯一索引")
    @RequestMapping(value = "/createUniqueIndex", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "indexName", value = "字段名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "collectionName", value = "集合名", dataType = "String", paramType = "query")
    })
    @LogAnno(operateType = "创建升序唯一索引")
    public Object createUniqueIndex(String indexName, String collectionName) {
        // 配置索引选项
        IndexOptions options = new IndexOptions();
        // 设置为唯一索引
        options.unique(true);
        // 创建索引
        return mongoTemplate.getCollection(collectionName).createIndex(Indexes.ascending(indexName), options);
    }

    @ApiOperation("创建局部索引")
    @RequestMapping(value = "/createPartialIndex", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "field", value = "字段名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "collectionName", value = "集合名", dataType = "String", paramType = "query")
    })
    @LogAnno(operateType = "创建局部索引")
    public Object createPartialIndex(String field, String collectionName) {
        // 配置索引选项
        IndexOptions options = new IndexOptions();
        // 设置过滤条件
        options.partialFilterExpression(Filters.exists(field, true));
        // 创建索引
        return mongoTemplate.getCollection(collectionName).createIndex(Indexes.ascending(field), options);
    }

    @ApiOperation("获取当前【集合】对应的【所有索引】的【名称列表】")
    @RequestMapping(value = "/getIndexAll", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "collectionName", value = "集合名", dataType = "String", paramType = "query")
    })
    @LogAnno(operateType = "获取当前【集合】对应的【所有索引】的【名称列表】")
    public Object getIndexAll(String collectionName) {
        // 获取集合中所有列表
        ListIndexesIterable<Document> indexList = mongoTemplate.getCollection(collectionName).listIndexes();
        // 创建字符串集合
        List<Document> list = new ArrayList<>();
        // 获取集合中全部索引信息
        for (Document document : indexList) {
            log.info("索引列表：{}", document);
            list.add(document);
        }
        return list;
    }

    @ApiOperation("根据索引名称移除索引")
    @RequestMapping(value = "/removeIndex", method = RequestMethod.DELETE)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "indexName", value = "字段名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "collectionName", value = "集合名", dataType = "String", paramType = "query")
    })
    @LogAnno(operateType = "根据索引名称移除索引")
    public void removeIndex(String indexName, String collectionName) {
        // 删除集合中某个索引
        mongoTemplate.getCollection(collectionName).dropIndex(indexName);
    }

    @ApiOperation("移除全部索引")
    @RequestMapping(value = "/removeIndexAll", method = RequestMethod.DELETE)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "collectionName", value = "集合名", dataType = "String", paramType = "query")
    })
    @LogAnno(operateType = "移除全部索引")
    public void removeIndexAll(String collectionName) {
        // 删除集合中全部索引
        mongoTemplate.getCollection(collectionName).dropIndexes();
    }
}
