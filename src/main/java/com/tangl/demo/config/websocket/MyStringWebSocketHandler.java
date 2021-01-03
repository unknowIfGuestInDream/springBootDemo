package com.tangl.demo.config.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * 因为我们的目的是实现和客户端的通信，并且内容为文本内容，所以我们继承的是TextWebSocketHandler；
 * 如果传输的是二进制内容，则可以继承BinaryWebSocketHandler，更多信息可以自行查看WebSocketHandler的子类。
 *
 * @author: TangLiang
 * @date: 2021/1/3 9:52
 * @since: 1.0
 */
@Component
@Slf4j
public class MyStringWebSocketHandler extends TextWebSocketHandler {

    //和客户端链接成功的时候触发该方法；
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("和客户端建立连接");
    }

    //和客户端连接失败的时候触发该方法；
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        session.close(CloseStatus.SERVER_ERROR);
        log.error("连接异常", exception);
    }

    //和客户端断开连接的时候触发该方法；
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        log.info("和客户端断开连接");
    }

    //和客户端建立连接后，处理客户端发送的请求。
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 获取到客户端发送过来的消息
        String receiveMessage = message.getPayload();
        log.info(receiveMessage);
        // 发送消息给客户端
        session.sendMessage(new TextMessage(fakeAi(receiveMessage)));
        // 关闭连接
        // session.close(CloseStatus.NORMAL);
    }

    private static String fakeAi(String input) {
        if (input == null || "".equals(input)) {
            return "你说什么？没听清︎";
        }
        return input.replace('你', '我')
                .replace("吗", "")
                .replace('?', '!')
                .replace('？', '！');
    }
}
