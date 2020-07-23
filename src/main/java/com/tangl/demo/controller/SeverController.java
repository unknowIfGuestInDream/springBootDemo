package com.tangl.demo.controller;

import com.tangl.demo.annotation.LogAnno;
import com.tangl.demo.domain.Server;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务监控
 *
 * @author: TangLiang
 * @date: 2020/7/23 9:42
 * @since: 1.0
 */
@Controller
@RequestMapping("/server/")
public class SeverController {
    @GetMapping(value = "server")
    public String server(ModelMap mmap) throws Exception {
        Server server = new Server();
        server.copyTo();
        mmap.put("server", server);
        return "pages/server/server";
    }

    @GetMapping(value = "servers")
    public String servers() throws Exception {
        return "pages/server/servers";
    }

    @RequestMapping("selectServer")
    @ResponseBody
    @LogAnno(operateType = "服务监控")
    public Map selectServer() throws Exception {
        Map<String, Object> result = new HashMap<>();
        Server server = new Server();
        server.copyTo();
        result.put("server", server);
        result.put("success", true);
        return result;
    }
}
