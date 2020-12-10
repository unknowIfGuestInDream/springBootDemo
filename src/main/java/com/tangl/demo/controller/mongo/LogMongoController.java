package com.tangl.demo.controller.mongo;

import com.tangl.demo.Document.LogDocument;
import com.tangl.demo.annotation.LogAnno;
import com.tangl.demo.common.AjaxResult;
import com.tangl.demo.repository.LogTomongoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author: TangLiang
 * @date: 2020/8/3 20:23
 * @since: 1.0
 */
@Controller
@Api(tags = "日志管理")
@RequestMapping("/manageLog")
public class LogMongoController {
    @Autowired
    private LogTomongoRepository logTomongoRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @ApiOperation("修改日志")
    @RequestMapping(value = "/updateLogs", method = RequestMethod.POST)
    @ResponseBody
    public Map updateLogs(String id, String name) {
        Map result = new HashMap();
        Optional<LogDocument> optional = logTomongoRepository.findById(id);
        if (optional.isPresent()) {
            LogDocument logDocument = optional.get();
            logDocument.setId(id);
            logDocument.setIpName(name);
            logTomongoRepository.save(logDocument);
            result.put("success", true);
        } else {
            result.put("success", false);
        }

        return result;
    }

    @ApiOperation("删除所有日志")
    @RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
    @ResponseBody
    public Map deleteAll() {
        Map result = new HashMap();
        logTomongoRepository.deleteAll();
        result.put("success", true);
        return result;
    }

    @LogAnno(operateType = "查询日志")
    @ApiOperation("查询日志")
    @RequestMapping(value = "/listLogs", method = RequestMethod.GET)
    @ResponseBody
    public Map listLogs() {
        Map result = new HashMap();
        List<LogDocument> logDocumentList = logTomongoRepository.findAll();
        result.put("success", true);
        result.put("result", logDocumentList);
        return result;
    }

    @LogAnno(operateType = "查询一条日志")
    @ApiOperation("查询一条日志")
    @RequestMapping(value = "/listOneLog", method = RequestMethod.GET)
    @ResponseBody
    public Map listone() {
        Map result = new HashMap();
        Optional<LogDocument> logDocument = logTomongoRepository.findOne(Example.of(new LogDocument()));
        result.put("success", true);
        result.put("result", logDocument);
        return result;
    }

    @ApiOperation("查询日志分页+精确查询")
    @RequestMapping(value = "/findExample", method = RequestMethod.GET)
    @ResponseBody
    public Map findExample(String id, Integer page, Integer size) {
        LogDocument logDocument = new LogDocument();
        if (StringUtils.isNotEmpty(id)) {
            logDocument.setId(id);
        }
        Example<LogDocument> example = Example.of(logDocument);

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<LogDocument> pageList = logTomongoRepository.findAll(example, pageRequest);

        Map result = new HashMap();
        result.put("success", true);
        result.put("result", pageList);
        return result;
    }

    //参考文章 https://blog.csdn.net/moshowgame/article/details/80282813
    //https://blog.csdn.net/long476964/article/details/79677526?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromBaidu-1.control&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromBaidu-1.control
    @ApiOperation("ExampleMatcher匹配查询")
    @RequestMapping(value = "/findExampleLike", method = RequestMethod.GET)
    @ResponseBody
    public Map findExampleLike(String id, String operateType, String browser, Integer page, Integer size) {
        LogDocument logDocument = new LogDocument();
        if (StringUtils.isNotEmpty(id)) {
            logDocument.setId(id);
        }
        if (StringUtils.isNotEmpty(operateType)) {
            logDocument.setOperateType(operateType);
        }
        if (StringUtils.isNotEmpty(browser)) {
            logDocument.setBrowser(browser);
        }
        //实例化对象 设置搜索的字段
        //GenericPropertyMatchers.contains() 全部模糊查询，即%{address}%
        //startsWith() 模糊查询匹配开头，即{username}%
        ExampleMatcher matching = ExampleMatcher.matching()
                .withMatcher("operateType", ExampleMatcher.GenericPropertyMatchers.contains()) //采用“开始匹配”的方式查询
                .withMatcher("browser", ExampleMatcher.GenericPropertyMatchers.contains())
                //.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
                .withIgnoreCase(true) //改变默认大小写忽略方式：忽略大小写
                .withIgnorePaths("_id");  //忽略属性。因为是基本类型，需要忽略掉

        Example<LogDocument> example = Example.of(logDocument, matching);

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<LogDocument> pageList = logTomongoRepository.findAll(example, pageRequest);

        Map result = new HashMap();
        result.put("success", true);
        result.put("result", pageList);
        return result;
    }

    @ApiOperation("查询日志分页+精确查询+排序")
    @RequestMapping(value = "/findExampleSort", method = RequestMethod.GET)
    @ResponseBody
    public Map findExampleSort(Integer page, Integer size) {
        LogDocument logDocument = new LogDocument();
        logDocument.setOperateType("查询Test");
        Example<LogDocument> example = Example.of(logDocument);

        //按照id降序排列
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "createTime");
        Sort.Order order2 = Sort.Order.desc("operateType");
        Sort sort = Sort.by(order, order2);

        //排序
        Sort sort2 = Sort.by(Sort.Direction.DESC, "operateType", "createTime");

        PageRequest pageRequest = PageRequest.of(page - 1, size, sort);
        Page<LogDocument> pageList = logTomongoRepository.findAll(example, pageRequest);

        Map result = new HashMap();
        result.put("success", true);
        result.put("result", pageList.getContent());
        result.put("total", pageList.getTotalElements());
        result.put("totalpage", pageList.getTotalPages());
        return result;
    }

    @ApiOperation("查询日志总数")
    @RequestMapping(value = "/findLogSum", method = RequestMethod.GET)
    @ResponseBody
    public Map findLogSum() {

        Long total = logTomongoRepository.count();
        Map result = new HashMap();
        result.put("success", true);
        result.put("total", total);
        return result;
    }

    @ApiOperation("查询日志模糊查询")
    @RequestMapping(value = "/findByOperateTypeLike", method = RequestMethod.GET)
    @ResponseBody
    public Map findByOperateTypeLike(String operateType) {

        List<LogDocument> logDocumentList = logTomongoRepository.findByOperateTypeLike(operateType);

        Map result = new HashMap();
        result.put("success", true);
        result.put("result", logDocumentList);
        return result;
    }

    @ApiOperation("查询日志时间查询")
    @RequestMapping(value = "/findByCreateTimeBetween", method = RequestMethod.GET)
    @ResponseBody
    public Map findByCreateTimeBetween(String start, String end) {

        List<LogDocument> logDocumentList = logTomongoRepository.findByCreateTimeBetween(start, end);

        Map result = new HashMap();
        result.put("success", true);
        result.put("result", logDocumentList);
        return result;
    }

    @ApiOperation("复合查询2")
    @RequestMapping(value = "/aggregations", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> aggregations(String minPrice, String maxPrice) {
        Criteria priceCriteria = Criteria.where("version").gt(minPrice).andOperator(Criteria.where("version").lt(maxPrice));
        MatchOperation matchOperation = Aggregation.match(priceCriteria);

//        GroupOperation groupOperation = Aggregation.group("warehouse")
//                .last("warehouse").as("warehouse")
//                .addToSet("id").as("productIds")
//                .avg("price").as("averagePrice")
//                .sum("price").as("totalRevenue");

        ProjectionOperation projectionOperation = Aggregation.project("operateType", "params", "createTime");
        //.and("warehouse").previousOperation();

        List<Document> list = mongoTemplate.aggregate(Aggregation.newAggregation(
                matchOperation,
                //groupOperation,
                projectionOperation
        ), LogDocument.class, Document.class).getMappedResults();
        return AjaxResult.success(list);
    }
}
