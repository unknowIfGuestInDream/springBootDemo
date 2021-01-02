package com.tangl.demo.controller.MQTT;

import com.tangl.demo.annotation.LogAnno;
import com.tangl.demo.config.MQTT.MqttGateway;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * MQTT测试接口
 *
 * @author: TangLiang
 * @date: 2020/10/12 22:19
 * @since: 1.0
 */
@Api(tags = "MQTT测试接口")
@RestController
@RequestMapping("/mqtt")
public class MqttController {

    @Autowired
    private MqttGateway mqttGateway;

    @PostMapping("/sendToDefaultTopic")
    @ApiOperation("向默认主题发送消息")
    @LogAnno(operateType = "向默认主题发送消息")
    public Map<String, Object> sendToDefaultTopic(String payload) {
        Map<String, Object> result = new HashMap();
        result.put("success", true);
        mqttGateway.sendToMqtt(payload);
        return result;
    }

    @PostMapping("/sendToTopic")
    @ApiOperation("向指定主题发送消息")
    @LogAnno(operateType = "向指定主题发送消息")
    public Map<String, Object> sendToTopic(String payload, String topic) {
        Map<String, Object> result = new HashMap();
        result.put("success", true);
        mqttGateway.sendToMqtt(payload, topic);
        return result;
    }
}
