package com.tangl.demo.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * @author: TangLiang
 * @date: 2020/12/18 21:36
 * @since: 1.0
 */
@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker //@EnableWebSocketMessageBroker注解表示开启使用STOMP协议来传输基于代理的消息，Broker就是代理的意思。
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    //https://www.jianshu.com/p/9103c9c7e128

    /**
     * registerStompEndpoints方法表示注册STOMP协议的节点，并指定映射的URL
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册一个 /simple 端点，前端通过这个端点进行连接
        registry.addEndpoint("/simple")
                //解决跨域问题
                .setAllowedOrigins("*")
                .withSockJS();
    }

    /**
     * configureMessageBroker方法用来配置消息代理，由于我们是实现推送功能，这里的消息代理是/topic
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //定义了一个客户端订阅地址的前缀信息，也就是客户端接收服务端发送消息的前缀信息
        registry.enableSimpleBroker("/topic");
        // 客户端向服务端发送消息需有/app 前缀
        //registry.setApplicationDestinationPrefixes("/app");
        // 指定用户发送（一对一）的前缀 /user/
        //registry.setUserDestinationPrefix("/user/");
    }
}
