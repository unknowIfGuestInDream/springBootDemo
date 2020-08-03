package com.tangl.demo.controller.mongo;

import com.tangl.demo.Document.LogDocument;
import com.tangl.demo.annotation.LogAnno;
import com.tangl.demo.repository.LogTomongoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
}
