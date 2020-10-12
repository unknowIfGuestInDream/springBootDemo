package com.tangl.demo.config.MQTT;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * MQTT相关配置
 *
 * @author: TangLiang
 * @date: 2020/10/12 22:13
 * @since: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class MqttConfig {
    /**
     * RabbitMQ连接用户名
     */
    @Value("${rabbitmq.mqtt.username}")
    private String username;
    /**
     * RabbitMQ连接密码
     */
    @Value("${rabbitmq.mqtt.password}")
    private String password;
    /**
     * RabbitMQ的MQTT默认topic
     */
    @Value("${rabbitmq.mqtt.defaultTopic}")
    private String defaultTopic;
    /**
     * RabbitMQ的MQTT连接地址
     */
    @Value("${rabbitmq.mqtt.url}")
    private String url;
}
