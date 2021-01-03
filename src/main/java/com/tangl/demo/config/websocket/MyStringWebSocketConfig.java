package com.tangl.demo.config.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author: TangLiang
 * @date: 2021/1/3 9:59
 * @since: 1.0
 */
@Configuration
@EnableWebSocket
public class MyStringWebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private MyStringWebSocketHandler myStringWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //当客户端通过/connecturl和服务端连接通信时，使用MyStringWebSocketHandler处理会话。
        registry.addHandler(myStringWebSocketHandler, "/connect").setAllowedOrigins("*").withSockJS();
    }
}
