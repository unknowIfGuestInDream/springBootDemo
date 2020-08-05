package com.tangl.demo.Document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author: TangLiang
 * @date: 2020/8/3 15:56
 * @since: 1.0
 */
@Document
@Data
public class LogDocument {
    @Id
    private String id;
    private String operateType;
    private String browser;
    private String version;
    private String os;
    private String ipName;
    private String ip;
    private String url;
    private String signature;
    private String params;
    private String createTime;
}
