package com.tangl.demo.controller.MQTT;

import com.tangl.demo.common.AjaxResult;
import com.tangl.demo.config.MQTT.MqttGateway;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public AjaxResult sendToDefaultTopic(String payload) {
        mqttGateway.sendToMqtt(payload);
        return AjaxResult.success(null);
    }

    @PostMapping("/sendToTopic")
    @ApiOperation("向指定主题发送消息")
    public AjaxResult sendToTopic(String payload, String topic) {
        mqttGateway.sendToMqtt(payload, topic);
        return AjaxResult.success(null);
    }
}
