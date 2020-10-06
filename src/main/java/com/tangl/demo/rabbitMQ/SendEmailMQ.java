package com.tangl.demo.rabbitMQ;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author: TangLiang
 * @date: 2020/8/6 15:11
 * @since: 1.0
 */
@Component
public class SendEmailMQ {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender jms;

    @Autowired
    private TemplateEngine templateEngine;

    //监听sendEmail队列
    @RabbitListener(queuesToDeclare = @Queue("sendEmail"))
    public void createLog(Message message) throws UnsupportedEncodingException {
        String msg = new String(message.getBody(), "utf-8");
        Map<String, String> map = JSON.parseObject(msg, Map.class);
        String code = map.get("code");
        String id = map.get("id");
        String mail = map.get("mail");
        String subject = map.get("subject");
        MimeMessage mimeMessage = null;
        try {
            mimeMessage = jms.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(mail); // 接收地址
            helper.setSubject(subject); // 标题
            // 处理邮件模板
            Context context = new Context();
            context.setVariable("id", id);
            context.setVariable("code", code);
            String template = templateEngine.process("emailTemplate", context);
            helper.setText(template, true);
            jms.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
