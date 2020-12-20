package com.tangl.demo.Document.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

/**
 * 语音播报
 *
 * @author: TangLiang
 * @date: 2020/12/20 12:44
 * @since: 1.0
 */
@Document(collection = "mg_voice")  //存入的表名称
@Data
public class MgVoice {
    @MongoId  //标志为主键
    private String cid;

    /**
     * M 男声  F 女声
     */
    private String voiceType;

    /**
     * 速度
     */
    private Integer voiceSpeed;

    private List<MgVoiceLibrary> voiceLibrarys;
}
