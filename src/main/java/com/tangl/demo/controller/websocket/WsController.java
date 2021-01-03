package com.tangl.demo.controller.websocket;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.tangl.demo.common.AjaxResult;
import com.tangl.demo.domain.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: TangLiang
 * @date: 2020/12/18 22:00
 * @since: 1.0
 */
@Controller
@Slf4j
public class WsController {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WsController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("websocket")
    public String websocket(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        return "pages/websocket/index";
    }

    @GetMapping("websocketclient")
    public String websocketclient(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        return "pages/websocket/client";
    }

    @GetMapping("websocketserver")
    public String websocketserver(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        return "pages/websocket/server";
    }

    @MessageMapping("/welcome")
    @SendTo("/topic/say")
    public AjaxResult say(String message) {
        return AjaxResult.success("welcome," + message + " !");
    }

    /**
     * 定时推送消息
     */
    @Scheduled(fixedRate = 1000)
    @Async
    public void callback() {
        // 发现消息
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagingTemplate.convertAndSend("/topic/callback", "定时推送消息时间: " + df.format(new Date()));
    }

    /**
     * 按照标准时间来算，每隔 2s 执行一次
     */
    //@Scheduled(cron = "0/2 * * * * ?")
    //@Async
    public void websocket() throws Exception {
        log.info("【推送消息】开始执行：{}", DateUtil.formatDateTime(new Date()));
        // 查询服务器状态
        Server server = new Server();
        server.copyTo();
        messagingTemplate.convertAndSend("/topic/server", JSONUtil.toJsonStr(server));
        log.info("【推送消息】执行结束：{}", DateUtil.formatDateTime(new Date()));
    }
}
