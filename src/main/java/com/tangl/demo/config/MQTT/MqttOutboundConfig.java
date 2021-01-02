package com.tangl.demo.config.MQTT;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * MQTT消息发布者相关配置 发送端
 *
 * @author: TangLiang
 * @date: 2020/10/12 22:17
 * @since: 1.0
 */
@Configuration
public class MqttOutboundConfig {

    @Autowired
    private MqttConfig mqttConfig;

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{mqttConfig.getUrl()});
        options.setUserName(mqttConfig.getUsername());
        options.setPassword(mqttConfig.getPassword().toCharArray());
        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，
        // 这里设置为true表示每次连接到服务器都以新的身份连接
        options.setCleanSession(true);
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(10);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，
        // 但这个方法并没有重连的机制
        options.setKeepAliveInterval(20);
        factory.setConnectionOptions(options);
        return factory;
    }

    /**
     * 发送消息和消费消息Channel可以使用相同MqttPahoClientFactory
     *
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler =
                new MqttPahoMessageHandler("publisherClient", mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(mqttConfig.getDefaultTopic());
        return messageHandler;
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }
}
