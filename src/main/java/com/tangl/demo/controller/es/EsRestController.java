package com.tangl.demo.controller.es;

import com.tangl.demo.Document.es.EsProduct;
import com.tangl.demo.common.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
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

    //https://blog.csdn.net/stilll123456/article/details/88380035
    @ApiOperation(value = "权重分页搜索")
    @RequestMapping(value = "/getDataByWeights", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getDataByWeights(String keyword, Integer pageNumber, Integer pageSize) throws IOException {
        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();

        // 多索引查询
        List<String> ids = new ArrayList<>();
        ids.add("esuser");
        searchQuery.withIds(ids);
        //searchQuery.withIndices(EsConstant.INDEX_NAME.TRAVEL);

        // 组合查询，boost即为权重，数值越大，权重越大
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.multiMatchQuery(keyword, "title").boost(3))
                .should(QueryBuilders.multiMatchQuery(keyword, "passCity", "description").boost(2))
                .should(QueryBuilders.multiMatchQuery(keyword, "content", "keyword").boost(1));
        searchQuery.withQuery(queryBuilder);

        // 高亮设置
        List<String> highlightFields = new ArrayList<String>();
        highlightFields.add("title");
        highlightFields.add("passCity");
        highlightFields.add("description");
        highlightFields.add("content");
        highlightFields.add("keyword");
        HighlightBuilder.Field[] fields = new HighlightBuilder.Field[highlightFields.size()];
//        for (int x = 0; x < highlightFields.size(); x++) {
//            fields[x] = new HighlightBuilder.Field(highlightFields.get(x)).preTags(EsConstant.HIGH_LIGHT_START_TAG)
//                    .postTags(EsConstant.HIGH_LIGHT_END_TAG);
//        }
        for (int x = 0; x < highlightFields.size(); x++) {
            fields[x] = new HighlightBuilder.Field(highlightFields.get(x)).preTags("title")
                    .postTags("title");
        }
        searchQuery.withHighlightFields(fields);

        // 分页设置
        searchQuery.withPageable(PageRequest.of(pageNumber, pageSize));
        //Page<Travel> page = null;
//        page = elasticsearchTemplate.queryForPage(searchQuery.build(), Travel.class, new SearchResultMapper() {
//
//            @Override
//            @SuppressWarnings("unchecked")
//            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
//
//                // 获取高亮搜索数据
//                List<Travel> list = new ArrayList<Travel>();
//                SearchHits hits = response.getHits();
//                for (SearchHit searchHit : hits) {
//                    if (hits.getHits().length <= 0) {
//                        return null;
//                    }
//                    Travel data = new Travel();
//                    // 公共字段
//                    data.setId(new Double(searchHit.getId()).longValue());
//                    data.setType(String.valueOf(searchHit.getSourceAsMap().get("type")));
//                    data.setStatus(String.valueOf(searchHit.getSourceAsMap().get("status")));
//                    data.setTitle(String.valueOf(searchHit.getSourceAsMap().get("title")));
//                    data.setContent(String.valueOf(searchHit.getSourceAsMap().get("content")));
//                    data.setIgnoreContent(String.valueOf(searchHit.getSourceAsMap().get("ignoreContent")));
//                    data.setDescription(String.valueOf(searchHit.getSourceAsMap().get("description")));
//                    Object createTime = searchHit.getSourceAsMap().get("createTime");
//                    Object updateTime = searchHit.getSourceAsMap().get("updateTime");
//                    if (createTime != null) {
//                        data.setCreateTime(new Date(Long.valueOf(createTime.toString())));
//                    }
//                    if (updateTime != null) {
//                        data.setUpdateTime(new Date(Long.valueOf(updateTime.toString())));
//                    }
//
//                    // 个性字段
//                    data.setKeyword(String.valueOf(searchHit.getSourceAsMap().get("keyword")));
//                    data.setPassCity(String.valueOf(searchHit.getSourceAsMap().get("passCity")));
//
//                    // 反射调用set方法将高亮内容设置进去
//                    try {
//                        for (String field : highlightFields) {
//                            HighlightField highlightField = searchHit.getHighlightFields().get(field);
//                            if (highlightField != null) {
//                                String setMethodName = parSetName(field);
//                                Class<? extends Travel> poemClazz = data.getClass();
//                                Method setMethod = poemClazz.getMethod(setMethodName, String.class);
//
//                                String highlightStr = highlightField.fragments()[0].toString();
//                                // 截取字符串
//                                if ("content".equals(field) && highlightStr.length() > 50) {
//                                    highlightStr = StringUtil.truncated(highlightStr,
//                                            EsConstant.HIGH_LIGHT_START_TAG, EsConstant.HIGH_LIGHT_END_TAG);
//                                }
//
//                                setMethod.invoke(data, highlightStr);
//                            }
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    list.add(data);
//                }
//
//                if (list.size() > 0) {
//                    AggregatedPage<T> result = new AggregatedPageImpl<T>((List<T>) list, pageable,
//                            response.getHits().getTotalHits());
//
//                    return result;
//                }
//                return null;
//            }
//        });
        return AjaxResult.success();
    }

    /**
     * 查询分类列表
     * @param searchMap
     * @return
     */
//    private List<String> searchCategoryList(Map searchMap) {
//        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
//        // 构建查询
//        BoolQueryBuilder boolQueryBuilder = buildBasicQuery(searchMap);
//        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
//        // 分类聚合名
//        String groupName = "sku_category";
//        // 构建聚合查询
//        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms(groupName).field("categoryName");
//        nativeSearchQueryBuilder.addAggregation(termsAggregationBuilder);
//        // 获取聚合分页结果
//        AggregatedPage<Goods> goodsList = (AggregatedPage<Goods>) goodsRepository.search(nativeSearchQueryBuilder.build());
//        // 在查询结果中找到聚合 - 根据聚合名称
//        StringTerms stringTerms = (StringTerms) goodsList.getAggregation(groupName);
//        // 获取桶
//        List<StringTerms.Bucket> buckets = stringTerms.getBuckets();
//        // 使用流Stream 将分类名存入集合
//        List<String> categoryList = buckets.stream().map(bucket -> bucket.getKeyAsString()).collect(Collectors.toList());
//        // 打印分类名称
//        categoryList.forEach(System.out::println);
//        return categoryList;
//    }

    /**
     * 构建基本查询 - 搜索关键字、分类、品牌、规格、价格
     *
     * @param searchMap
     * @return
     */
    private BoolQueryBuilder buildBasicQuery(Map searchMap) {
        // 构建布尔查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 关键字查询
        boolQueryBuilder.must(QueryBuilders.matchQuery("name", searchMap.get("keywords")));
        // 分类、品牌、规格 都是需要精准查询的，无需分词
        // 商品分类过滤
        if (searchMap.get("category") != null) {
            boolQueryBuilder.filter(QueryBuilders.matchPhraseQuery("categoryName", searchMap.get("category")));
        }
        // 商品品牌过滤
        if (searchMap.get("brand") != null) {
            boolQueryBuilder.filter(QueryBuilders.matchPhraseQuery("brandName", searchMap.get("brand")));
        }
        // 规格过滤
        if (searchMap.get("spec") != null) {
            Map<String, String> map = (Map) searchMap.get("spec");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                // 规格查询[spec.xxx],因为规格是不确定的，所以需要精确查找，加上.keyword，如spec.颜色.keyword
                boolQueryBuilder.filter(QueryBuilders.matchPhraseQuery("spec." + entry.getKey() + ".keyword", entry.getValue()));
            }
        }
        // 价格过滤
        if (searchMap.get("price") != null) {
            // 价格： 0-500  0-*
            String[] prices = ((String) searchMap.get("price")).split("-");
            if (!prices[0].equals("0")) {  // 加两个0是，因为价格转换成分
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gt(prices[0] + "00"));
            }
            if (!prices[1].equals("*")) {  // 价格有上限
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").lt(prices[1] + "00"));
            }
        }
        return boolQueryBuilder;
    }


}
