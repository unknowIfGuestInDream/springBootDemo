package com.tangl.demo.controller.es;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.tangl.demo.Document.es.User;
import com.tangl.demo.common.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: TangLiang
 * @date: 2020/12/13 12:24
 * @since: 1.0
 */
@Controller
@Api(tags = "es高级客户端")
@RequestMapping("/restClient")
public class RestClientController {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @ApiOperation(value = "创建索引")
    @RequestMapping(value = "/createIndex", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createIndex(String index) throws IOException {
        CreateIndexRequest createReq = new CreateIndexRequest(index);
        CreateIndexResponse resp = restHighLevelClient.indices().create(createReq, RequestOptions.DEFAULT);

        return AjaxResult.success(JSONUtil.parse(resp));
    }

    @ApiOperation(value = "判断索引是否存在")
    @RequestMapping(value = "/existIndex", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> existIndex(String index) throws IOException {
        final GetIndexRequest indexRequest = new GetIndexRequest(index);
        final boolean exists = restHighLevelClient.indices().exists(indexRequest, RequestOptions.DEFAULT);
        return AjaxResult.success(exists);
    }

    @ApiOperation(value = "删除索引")
    @RequestMapping(value = "/deleteIndex", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> deleteIndex(String index) throws IOException {
        DeleteIndexRequest deleteReq = new DeleteIndexRequest(index);
        AcknowledgedResponse resp = restHighLevelClient.indices().delete(deleteReq, RequestOptions.DEFAULT);
        return AjaxResult.success(JSONUtil.parse(resp));
    }

    @ApiOperation(value = "通过id查询数据：")
    @RequestMapping(value = "/getDataById", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getDataById(String index, String id) throws IOException {
        final GetRequest request = new GetRequest(index, id);
        final GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        final String string = response.toString();
        return AjaxResult.success(string);
    }

    @ApiOperation(value = "通过索引查询数据：")
    @RequestMapping(value = "/getDataByIndex", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getDataByIndex(String index) throws IOException {
        SearchResponse search = restHighLevelClient.search(new SearchRequest(index), RequestOptions.DEFAULT);
        return AjaxResult.success(search);
    }

    @ApiOperation(value = "创建文档")
    @RequestMapping(value = "/addDoc", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addDoc(String index, String id) throws IOException {
        User user = User.builder()
                .id(id)
                .name("elvin")
                .age(29)
                .work("java软件开发工程师")
                .hobby(new ArrayList<String>() {{
                    add("篮球");
                    add("羽毛球");
                    add("乒乓球");
                    add("游泳");
                }})
                .desc("一个喜欢运动, 爱老婆的boy").build();
        IndexRequest req = new IndexRequest(index);
        req.id(user.getId());
        req.timeout("5s");
        req.source(JSON.toJSONString(user), XContentType.JSON);
        IndexResponse resp = restHighLevelClient.index(req, RequestOptions.DEFAULT);
        System.out.println("添加文档 -> " + req.toString());
        System.out.println("结果 -> " + resp.toString());
        System.out.println("状态 -> " + resp.status());
        return AjaxResult.success(JSONUtil.parseObj(resp).toStringPretty());
    }

    @ApiOperation(value = "判断文档是否存在")
    @RequestMapping(value = "/existDoc", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> existDoc(String index, String id) throws IOException {
        GetRequest req = new GetRequest(index, id);
        boolean exists = restHighLevelClient.exists(req, RequestOptions.DEFAULT);
        return AjaxResult.success(exists);
    }

    @ApiOperation(value = "根据id获取文档")
    @RequestMapping(value = "/getDocById", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getDocById(String index, String id) throws IOException {
        GetRequest req = new GetRequest(index, id);
        GetResponse resp = restHighLevelClient.get(req, RequestOptions.DEFAULT);
        return AjaxResult.success(resp.toString());
    }

    @ApiOperation(value = "修改文档")
    @RequestMapping(value = "/updateDoc", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateDoc(String index, String id) throws IOException {
        User user = new User();
        user.setDesc("热爱生活和热爱工作");

        UpdateRequest req = new UpdateRequest(index, id);
        req.id(id);
        req.timeout("5s");
        req.doc(JSON.toJSONString(user), XContentType.JSON);

        UpdateResponse resp = restHighLevelClient.update(req, RequestOptions.DEFAULT);
        System.out.println("修改文档 --> " + req.toString());
        System.out.println("修改结果 --> " + resp.status());
        return AjaxResult.success(JSONUtil.parse(resp).toStringPretty());
    }

    @ApiOperation(value = "根据id删除文档")
    @RequestMapping(value = "/deleteDocById", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> deleteDocById(String index, String id) throws IOException {
        DeleteRequest req = new DeleteRequest(index, id);
        DeleteResponse resp = restHighLevelClient.delete(req, RequestOptions.DEFAULT);
        return AjaxResult.success(resp.toString());
    }

    @ApiOperation(value = "批量添加文档")
    @RequestMapping(value = "/bulkAddDoc", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> bulkAddDoc(String index) throws IOException {
        List<User> userList = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            User user = User.builder()
                    .id(String.valueOf(i))
                    .name("elvin" + i)
                    .age(29)
                    .work("java软件开发工程师")
                    .hobby(new ArrayList<String>() {{
                        add("篮球");
                        add("羽毛球");
                        add("乒乓球");
                        add("游泳");
                    }})
                    .desc("一个喜欢运动, 爱老婆的boy")
                    .build();
            userList.add(user);
        }

        BulkRequest req = new BulkRequest();
        req.timeout("5s");
        for (int i = 0; i < userList.size(); i++) {
            req.add(new IndexRequest(index)
                    .id(String.valueOf(i + 1))
                    .source(JSON.toJSONString(userList.get(i)), XContentType.JSON)
            );
        }
        BulkResponse resp = restHighLevelClient.bulk(req, RequestOptions.DEFAULT);
        System.out.println("批量添加文档 -> " + resp.hasFailures());
        return AjaxResult.success(JSONUtil.parse(resp).toStringPretty());
    }


    @ApiOperation(value = "查询文档")
    @RequestMapping(value = "/searchDoc", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> searchDoc(String index) throws IOException {
        SearchRequest req = new SearchRequest(index);

        //构建查询条件
        SearchSourceBuilder reqSourceBuilder = new SearchSourceBuilder();
        //设置分页
        reqSourceBuilder.from(0);
        reqSourceBuilder.size(5);
        //设置高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        reqSourceBuilder.highlighter(highlightBuilder);
        //term查询
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "java");
        reqSourceBuilder.query(termQueryBuilder);
        reqSourceBuilder.timeout(new TimeValue(5, TimeUnit.SECONDS));

        req.source(reqSourceBuilder);

        SearchResponse resp = restHighLevelClient.search(req, RequestOptions.DEFAULT);
        System.out.println("测试查询文档-----" + JSON.toJSONString(resp.getHits()));
        System.out.println("=====================");
        for (SearchHit documentFields : resp.getHits().getHits()) {
            System.out.println("测试查询文档--遍历参数--" + documentFields.getSourceAsMap());
        }

        System.out.println("----------替换高亮列-----------");
        //解析结果
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : resp.getHits().getHits()) {
            //解析高亮的字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField title = highlightFields.get("title");
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //解析高亮的字段
            if (title != null) {
                Text[] fragments = title.fragments();
                String n_title = "";
                for (Text text : fragments) {
                    n_title += text;
                }
                //将高亮的字段替换掉原来的字段
                sourceAsMap.put("title", n_title);
            }
            list.add(sourceAsMap);
        }
        list.forEach(n -> System.out.println(JSON.toJSONString(n)));
        return AjaxResult.success(list);
    }

}
