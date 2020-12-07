package com.tangl.demo.controller.mail;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

/**
 * @author: TangLiang
 * @date: 2020/8/6 13:37
 * @since: 1.0
 */
@RestController
//@RequestMapping("/email")
@Api(tags = "邮件业务")
public class EmailController {

    @Autowired
    private JavaMailSender jms;
    @Autowired
    private TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String from;

    @ApiOperation("发送简单邮件")
    @RequestMapping(value = "/sendSimpleEmail", method = RequestMethod.GET)
    public String sendSimpleEmail() {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo("1195542421@qq.com"); // 接收地址
            message.setSubject("一封简单的邮件"); // 标题
            message.setText("使用Spring Boot发送简单邮件。"); // 内容
            jms.send(message);
            return "发送成功";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @ApiOperation("发送HTML格式的邮件")
    @RequestMapping(value = "/sendHtmlEmail", method = RequestMethod.GET)
    public String sendHtmlEmail() {
        MimeMessage message = null;
        try {
            message = jms.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo("1195542421@qq.com"); // 接收地址
            helper.setSubject("一封HTML格式的邮件"); // 标题
            // 带HTML格式的内容
            StringBuffer sb = new StringBuffer("<p style='color:#6db33f'>使用Spring Boot发送HTML格式邮件。</p>");
            helper.setText(sb.toString(), true);  //true表示发送HTML格式邮件
            jms.send(message);
            return "发送成功";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @ApiOperation("发送带附件的邮件")
    @RequestMapping(value = "/sendAttachmentsMail", method = RequestMethod.GET)
    public String sendAttachmentsMail() {
        MimeMessage message = null;
        try {
            message = jms.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo("1195542421@qq.com"); // 接收地址
            helper.setSubject("一封带附件的邮件"); // 标题
            helper.setText("详情参见附件内容！"); // 内容
            // 传入附件
            FileSystemResource file = new FileSystemResource(new File("E:\\demo.xlsx"));
            helper.addAttachment("测试附件.xlsx", file);
            jms.send(message);
            return "发送成功";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @ApiOperation("发送带静态资源的邮件")
    @RequestMapping(value = "/sendInlineMail", method = RequestMethod.GET)
    public String sendInlineMail() {
        MimeMessage message = null;
        try {
            message = jms.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo("1195542421@qq.com"); // 接收地址
            helper.setSubject("一封带静态资源的邮件"); // 标题
            helper.setText("<html><body>博客图：<img src='cid:img'/></body></html>", true); // 内容
            // 传入附件
            FileSystemResource file = new FileSystemResource(new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Hydrangeas.jpg"));
            helper.addInline("img", file);
            jms.send(message);
            return "发送成功";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @ApiOperation("发送模板邮件")
    @RequestMapping(value = "/sendTemplateEmail", method = RequestMethod.GET)
    public String sendTemplateEmail(String id) {
        MimeMessage message = null;
        try {
            message = jms.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo("1195542421@qq.com"); // 接收地址
            helper.setSubject("邮件摸板测试"); // 标题
            // 处理邮件模板
            Context context = new Context();
            context.setVariable("id", id);
            context.setVariable("code", "774875");
            String template = templateEngine.process("emailTemplate", context);
            helper.setText(template, true);
            jms.send(message);
            return "发送成功";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @ApiOperation("测试返回模板")
    @RequestMapping(value = "/getTemplateTest", method = RequestMethod.GET)
    public String getTemplateTest(String id) {
        try {
            Context context = new Context();
            context.setVariable("id", id);
            context.setVariable("code", "774875");
            String template = templateEngine.process("emailTemplate", context);
            return template;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @ApiOperation("测试返回二级菜单下的模板")
    @RequestMapping(value = "/getTemplateTestLevel", method = RequestMethod.GET)
    public String getTemplateTestLevel(String id) {
        try {
            Context context = new Context();
            context.setVariable("curDate", new Date());
            context.setVariable("code", "774875");
            String template = templateEngine.process("pages/Hello", context);
            return template;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

}
