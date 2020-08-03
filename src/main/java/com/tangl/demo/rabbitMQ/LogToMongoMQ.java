package com.tangl.demo.rabbitMQ;

import com.alibaba.fastjson.JSON;
import com.tangl.demo.Document.LogDocument;
import com.tangl.demo.repository.LogTomongoRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * @author: TangLiang
 * @date: 2020/8/3 16:26
 * @since: 1.0
 */
@Component
public class LogToMongoMQ {
    @Autowired
    private LogTomongoRepository logTomongoRepository;

    //监听logToMongo队列
    @RabbitListener(queuesToDeclare = @Queue("logToMongo"))
    public void receiveMessage(Message message) throws UnsupportedEncodingException {
        String msg = new String(message.getBody(), "utf-8");
        LogDocument logDocument = JSON.parseObject(msg, LogDocument.class);
        logTomongoRepository.save(logDocument);
    }
}
