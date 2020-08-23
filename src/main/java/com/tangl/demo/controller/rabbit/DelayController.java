package com.tangl.demo.controller.rabbit;

import com.tangl.demo.annotation.LogAnno;
import com.tangl.demo.rabbitMQ.delay.RabbitMQConfiguration;
import com.tangl.demo.rabbitMQ.topic.TopicSender;
import com.tangl.demo.util.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.Date;

/**
 * 短信测试
 * https://office.ucpaas.com/
 *
 * @author: TangLiang
 * @date: 2020/8/2 10:53
 * @since: 1.0
 */
@RestController
@Api(tags = "消息队列")
@RequestMapping("/rabbitmq/delay")
public class DelayController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private TopicSender topicSender;

    @ApiOperation("延迟消息")
    @LogAnno(operateType = "延迟消息接口")
    @RequestMapping(value = "/delayTest", method = RequestMethod.GET)
    public String delayTest() {
        MessageProperties properties = new MessageProperties();
        properties.setDelay(10000);
        Message message = new Message("delay_test_message".getBytes(), properties);
        System.out.println(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, new Date()));
        rabbitTemplate.send(RabbitMQConfiguration.EXCHANGE_NAME, RabbitMQConfiguration.QUEUE_NAME, message);
        return null;
    }

    @ApiOperation("topic消息")
    @LogAnno(operateType = "topic消息接口")
    @RequestMapping(value = "/topicTest", method = RequestMethod.GET)
    public String topicTest() throws InterruptedException {
        topicSender.send1();
        Thread.sleep(1000);
        topicSender.send2();
        return null;
    }
}
