package com.tangl.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: TangLiang
 * @date: 2020/7/28 19:30
 * @since: 1.0
 */
@Controller
public class HttpWebController {
    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("findPlat")
    @ResponseBody
    public Object findPlat(String indress) {
        indress = "http://localhost:8080/server/selectServer";
        ResponseEntity<Map> en = restTemplate.getForEntity(indress, Map.class);
        return en.getBody();
    }

}
