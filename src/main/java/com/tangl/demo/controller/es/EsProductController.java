package com.tangl.demo.controller.es;

import com.tangl.demo.Document.es.EsProduct;
import com.tangl.demo.service.EsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: TangLiang
 * @date: 2020/8/27 11:55
 * @since: 1.0
 */
@Controller
@Api(tags = "搜索商品管理")
@RequestMapping("/esProduct")
public class EsProductController {
    @Autowired
    private EsProductService esProductService;

    @ApiOperation(value = "导入所有数据库中商品到ES")
    @RequestMapping(value = "/importAll", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> importAllList() {
        Map<String, Object> result = new HashMap<>();
        int count = esProductService.importAll();
        result.put("success", true);
        result.put("result", count);
        return result;
    }

    @ApiOperation(value = "根据id删除商品")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> delete(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        esProductService.delete(id);
        result.put("success", true);
        return result;
    }

    @ApiOperation(value = "根据id批量删除商品")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(@RequestParam("ids") List<Long> ids) {
        Map<String, Object> result = new HashMap<>();
        esProductService.delete(ids);
        result.put("success", true);
        return result;
    }

    @ApiOperation(value = "根据id创建商品")
    @RequestMapping(value = "/create/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> create(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        EsProduct esProduct = esProductService.create(id);
        if (esProduct != null) {
            result.put("success", true);
            result.put("result", esProduct);
        } else {
            result.put("success", false);
        }
        return result;
    }

    @ApiOperation(value = "简单搜索")
    @RequestMapping(value = "/search/simple", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> search(@RequestParam(required = false) String keyword,
                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Map<String, Object> result = new HashMap<>();
        Page<EsProduct> esProductPage = esProductService.search(keyword, pageNum, pageSize);
        result.put("success", true);
        result.put("result", esProductPage);
        return result;
    }

    @ApiOperation(value = "综合搜索、筛选、排序")
    @ApiImplicitParam(name = "sort", value = "排序字段:0->按相关度；1->按新品；2->按销量；3->价格从低到高；4->价格从高到低",
            defaultValue = "0", allowableValues = "0,1,2,3,4", paramType = "query", dataType = "integer")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> search(@RequestParam(required = false) String keyword,
                                      @RequestParam(required = false) Long brandId,
                                      @RequestParam(required = false) Long productCategoryId,
                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                      @RequestParam(required = false, defaultValue = "0") Integer sort) {
        Map<String, Object> result = new HashMap<>();
        Page<EsProduct> esProductPage = esProductService.search(keyword, brandId, productCategoryId, pageNum, pageSize, sort);
        result.put("success", true);
        result.put("result", esProductPage);
        return result;
    }

    @ApiOperation(value = "根据商品id推荐商品")
    @RequestMapping(value = "/recommend/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> recommend(@PathVariable Long id,
                                         @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                         @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Map<String, Object> result = new HashMap<>();
        Page<EsProduct> esProductPage = esProductService.recommend(id, pageNum, pageSize);
        result.put("success", true);
        result.put("result", esProductPage);
        return result;
    }

}
