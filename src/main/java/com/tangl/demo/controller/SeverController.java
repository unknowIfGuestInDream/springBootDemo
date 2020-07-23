package com.tangl.demo.controller;

import com.tangl.demo.domain.Server;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
}
