package com.tangl.demo.controller.mongo;

import com.tangl.demo.Document.LogDocument;
import com.tangl.demo.common.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author: TangLiang
 * @date: 2020/12/10 8:58
 * @since: 1.0
 */
@Controller
@Api(tags = "mongodb聚合操作")
@RequestMapping("/mongodb/aggregation")
public class AggregationController {
    @Autowired
    private MongoTemplate mongoTemplate;

    //参考文章: https://blog.csdn.net/qq330983778/article/details/104079851

    /*
    例子中对order集合中的userId和type进行分组，使用count获取总数并映射到num字段中，
     对totalProduct字段进行求和并映射到count字段中。
     在所有的Group聚合操作中，除了count之外，其他操作使用格式基本上都是相同的。其在JAVA中的调用都是使用下面格式
    GroupOperation noRepeatGroup = Aggregation.group( {分组字段1},{分组字段2})
                .count().as({count结果映射的字段})
                .sum({需要进行sum的字段}).as({sum结果映射的字段})
                     */
    @ApiOperation("Group聚合操作")
    @RequestMapping(value = "/group", method = RequestMethod.POST)
    @ResponseBody
    public Map group() {

//        Criteria criteria = Criteria.where("userId").is(userId);
//        Aggregation customerAgg = Aggregation.newAggregation(
//                Aggregation.project("buyerNick","payment","num","tid","userId","address","mobile","orders"),
//                Aggregation.match(criteria),
//                Aggregation.unwind("orders"),
//                Aggregation.group("buyerNick").first("buyerNick").as("buyerNick").first("mobile").as("mobile").
//                        first("address").as("address").sum("payment").as("totalPayment").sum("num").as("itemNum").count().as("orderNum"),
//                Aggregation.sort(new Sort(new Sort.Order(Sort.Direction.DESC, "totalPayment"))),
//                Aggregation.skip(startRows),
//                Aggregation.limit(pageSize)
//        );

        GroupOperation noRepeatGroup = Aggregation.group("operateType", "browser")
                .count().as("num");
        TypedAggregation<LogDocument> noRepeatAggregation =
                Aggregation.newAggregation(LogDocument.class, noRepeatGroup);
        AggregationResults<Document> noRepeatDataInfoVos = mongoTemplate.aggregate(noRepeatAggregation, Document.class);
        List<Document> noRepeatDataList = noRepeatDataInfoVos.getMappedResults();
        return AjaxResult.success(noRepeatDataList);
    }

    /*
    project主要作用就是调整文档的字段内容。比如内容中获取数据条目数最开始映射到num字段中，
     但是在下面Field field = Fields.field("num2", "num")这里将num的映射转为了num2的映射。
     这个方法在后续使用lookup进行联表查询的时候会非常有用。因为正常进行联表查询的时候被连接表会作为结果集中的子集出现，
     而使用project可以将子集数据作为文档的同一层级数据展示出来。
     */
    @ApiOperation("project操作")
    @RequestMapping(value = "/project", method = RequestMethod.POST)
    @ResponseBody
    public Map project() {
        GroupOperation noRepeatGroup = Aggregation.group("operateType", "browser")
                .count().as("num");
        Field field = Fields.field("num2", "num");
        ProjectionOperation project = Aggregation.project("operateType", "browser")
                .andInclude(Fields.from(field));

        TypedAggregation<LogDocument> noRepeatAggregation =
                Aggregation.newAggregation(LogDocument.class, noRepeatGroup, project);
        AggregationResults<Document> noRepeatDataInfoVos = mongoTemplate.aggregate(noRepeatAggregation, Document.class);
        List<Document> noRepeatDataList = noRepeatDataInfoVos.getMappedResults();
        return AjaxResult.success(noRepeatDataList);
    }

    /*
    Aggregation.bucket方法允许使用者设置一个字段，以及一个多段的查询区间。
    mongodb将根据这个字段的内容以及设置的分组区间生成多个数据桶。
    我们可以获取每个桶的聚合结果。下面的例子中就是将Order集合中的数据根据type字段的值分为[0,1),[1,2),[2,3),[3,other)四组。
    字段介绍:
    bucket：需要进行分桶依据的字段
    withBoundaries：分桶区间的范围内容，需要注意的是这个范围是包含前一个条件的值不包含后一个条件的值
    withDefaultBucket：所有没被设定范围统计到的数据会被放入到other这个桶中，其中other是这个桶的id
    andOutput：需要输出的内容，一般是针对某个字段的聚合结果
     */
    @ApiOperation("Bucket操作")
    @RequestMapping(value = "/bucket", method = RequestMethod.POST)
    @ResponseBody
    public Map bucket() {
        BucketOperation bucketOperation =
                // 分组的字段
                Aggregation.bucket("version")
                        // 根据范围进行分组
                        .withBoundaries("50", "60", "70", "80")
                        // 默认分组
                        .withDefaultBucket("other")
                        // 求总
                        .andOutput("_id").count().as("count");
        // 求和
        //.andOutput("totalProduct").sum().as("sum")
        // 求平均
        //.andOutput("totalMoney").avg().as("avg");
        TypedAggregation<LogDocument> newAggregation =
                Aggregation.newAggregation(LogDocument.class, bucketOperation);
        AggregationResults<Document> noRepeatDataInfoVos = mongoTemplate.aggregate(newAggregation, Document.class);
        List<Document> noRepeatDataList = noRepeatDataInfoVos.getMappedResults();
        return AjaxResult.success(noRepeatDataList);
    }

    /*
    bucketAuto是指定的参数和字段，根据对应字段的内容，将数据平均分配为指定数量的桶。
    比如下面例子中数据根据type字段中的值分成2组
    字段介绍:
    bucketAuto: 需要设置进行分桶依据的字段，以及需要分的桶数量
    andOutput：需要输出的内容，一般是针对某个字段的聚合结果
     */
    @ApiOperation("BucketAuto操作")
    @RequestMapping(value = "/bucketAuto", method = RequestMethod.POST)
    @ResponseBody
    public Map bucketAuto() {
        // 分组的字段
        BucketAutoOperation autoOperation = Aggregation.bucketAuto("version", 2)
                // 求总
                .andOutput("_id").count().as("count");
        // 求和
        //.andOutput("totalProduct").sum().as("sum")
        // 求平均
        //.andOutput("totalMoney").avg().as("avg");
        TypedAggregation<LogDocument> newAggregation =
                Aggregation.newAggregation(LogDocument.class, autoOperation);
        AggregationResults<Document> noRepeatDataInfoVos = mongoTemplate.aggregate(newAggregation, Document.class);
        List<Document> noRepeatDataList = noRepeatDataInfoVos.getMappedResults();
        return AjaxResult.success(noRepeatDataList);
    }

}
