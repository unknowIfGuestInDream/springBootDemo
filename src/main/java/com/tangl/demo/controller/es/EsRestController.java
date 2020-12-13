package com.tangl.demo.controller.es;

import com.tangl.demo.Document.es.EsProduct;
import com.tangl.demo.common.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author: TangLiang
 * @date: 2020/12/13 12:24
 * @since: 1.0
 */
@Controller
@Api(tags = "ElasticsearchRestTemplate操作")
@RequestMapping("/esRest")
public class EsRestController {

    @Autowired
    private ElasticsearchRestTemplate template;

    @ApiOperation(value = "判断索引是否存在")
    @RequestMapping(value = "/existIndex", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> existIndex() throws IOException {
        IndexOperations ops = template.indexOps(EsProduct.class);
        boolean exists = ops.exists();
        return AjaxResult.success(exists);
    }

    @ApiOperation(value = "通过id查询数据：")
    @RequestMapping(value = "/getDataById", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getDataById(String id) throws IOException {
        //通过id查询，已经废弃
        EsProduct user = template.queryForObject(GetQuery.getById(id), EsProduct.class);
        //和上一样
        EsProduct user1 = template.get(id, EsProduct.class);
        return AjaxResult.success(user.toString(), user1);
    }

    @ApiOperation(value = "查询所有")
    @RequestMapping(value = "/getDataByIndex", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getDataByIndex() throws IOException {
        final SearchHits<EsProduct> search = template.search(Query.findAll(), EsProduct.class);
//        final Iterator<SearchHit<EsProduct>> iterator = search.iterator();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }
//        System.out.println(search.getTotalHits()); //查询命中的数量
//        System.out.println(search.getMaxScore()); //查询命中的最大分值

        List<SearchHit<EsProduct>> esProducts = search.toList();
        return AjaxResult.success(esProducts);
    }
}
