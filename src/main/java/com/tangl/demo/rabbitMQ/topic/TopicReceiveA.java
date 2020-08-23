package com.tangl.demo.rabbitMQ.topic;

import com.tangl.demo.rabbitMQ.delay.RabbitMQConfiguration;
import com.tangl.demo.util.DateUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: TangLiang
 * @date: 2020/8/23 19:34
 * @since: 1.0
 */
@Component
public class TopicReceiveA {

    @RabbitListener(queues = "topic.a")
    public void consumer(Message message) {
        System.out.println("TopicReceiveA: " + new String(message.getBody()));
    }
}
