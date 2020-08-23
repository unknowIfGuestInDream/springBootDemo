package com.tangl.demo.rabbitMQ.topic;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: TangLiang
 * @date: 2020/8/23 19:34
 * @since: 1.0
 */
@Component
@RabbitListener(queues = "topic.b")
public class TopicReceiveB {

    @RabbitHandler
    public void process(String message) {
        System.out.println("TopicReceiveB: " + message);
    }
}
