package com.tangl.demo.controller.rabbit;

import com.alibaba.fastjson.JSONObject;
import com.tangl.demo.rabbitMQ.delay.RabbitMQConfiguration;
import com.tangl.demo.util.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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

    @ApiOperation("延迟消息")
    @RequestMapping(value = "/delayTest", method = RequestMethod.GET)
    public String delayTest() throws URISyntaxException {
        MessageProperties properties = new MessageProperties();
        properties.setDelay(10000);
        Message message = new Message("delay_test_message".getBytes(), properties);
        System.out.println(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, new Date()));
        rabbitTemplate.send(RabbitMQConfiguration.EXCHANGE_NAME, RabbitMQConfiguration.QUEUE_NAME, message);
        return null;
    }
}
