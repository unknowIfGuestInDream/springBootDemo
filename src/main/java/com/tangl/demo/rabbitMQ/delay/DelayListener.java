package com.tangl.demo.rabbitMQ.delay;

import com.tangl.demo.util.DateUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: TangLiang
 * @date: 2020/8/23 16:23
 * @since: 1.0
 */
@Component
public class DelayListener {

    // 消息转换器
    @RabbitListener(queues = RabbitMQConfiguration.QUEUE_NAME)
    public void consumer(Message message) {
        System.out.println(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, new Date()));
        System.out.println(new String(message.getBody()));
    }

}
