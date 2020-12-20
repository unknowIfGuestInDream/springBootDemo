package com.tangl.demo.Document.mongo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: TangLiang
 * @date: 2020/12/20 12:48
 * @since: 1.0
 */

@Data
@ApiModel("语音库")
public class MgVoiceLibrary {
    @ApiModelProperty("语音ID")
    private String lid;

    @ApiModelProperty("语音类型 2科目二 3科目三")
    private Integer type;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("是否是模板 0 不是 1 是")
    private Integer isTemp;

    @ApiModelProperty("模板代码  ，也是模板图片标题")
    private String tempCode;

    @ApiModelProperty("模板标题")
    private String tempTitle;

    @ApiModelProperty("模板内容，供恢复默认使用")
    private String tempContent;

    @ApiModelProperty("修改日期")
    private Date updateDate;
}
