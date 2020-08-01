package com.tangl.demo.controller.mongo;

/**
 * @author: TangLiang
 * @date: 2020/8/1 9:55
 * @since: 1.0
 */

import com.tangl.demo.Document.MemberReadHistory;
import com.tangl.demo.service.MemberReadHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员商品浏览记录管理Controller
 * <p>
 * Created by macro on 2018/8/3.
 */
@Controller
@Api(tags = "会员商品浏览记录管理")
@RequestMapping("/member/readHistory")
public class MemberReadHistoryController {
    @Autowired
    private MemberReadHistoryService memberReadHistoryService;

    @ApiOperation("创建浏览记录")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody

    public Map create(@RequestBody MemberReadHistory memberReadHistory) {
        Map result = new HashMap();
        int count = memberReadHistoryService.create(memberReadHistory);
        if (count > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        return result;
    }

    @ApiOperation("删除浏览记录")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Map delete(@RequestParam("ids") List<String> ids) {
        Map result = new HashMap();
        int count = memberReadHistoryService.delete(ids);
        if (count > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        return result;
    }

    @ApiOperation("展示浏览记录")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Map list(Long memberId) {
        Map result = new HashMap();
        List<MemberReadHistory> memberReadHistoryList = memberReadHistoryService.list(memberId);
        result.put("success", true);
        result.put("result", memberReadHistoryList);
        return result;
    }

}
