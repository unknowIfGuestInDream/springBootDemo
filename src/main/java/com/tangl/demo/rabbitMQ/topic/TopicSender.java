package com.tangl.demo.rabbitMQ.topic;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author: TangLiang
 * @date: 2020/8/23 19:38
 * @since: 1.0
 */
@Component
@Async
public class TopicSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //private AmqpTemplate amqpTemplate;
    public void send1() {
        String context = "topic message1";
        rabbitTemplate.convertAndSend("topicExchange", "topic.1", context);
    }

    public void send2() {
        String context = "topic message2";
        rabbitTemplate.convertAndSend("topicExchange", "topic.a", context);
    }
}
