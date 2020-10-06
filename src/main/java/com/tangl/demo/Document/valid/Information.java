package com.tangl.demo.Document.valid;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author: TangLiang
 * @date: 2020/9/2 8:52
 * @since: 1.0
 */
@Data
@ApiModel("信息实体")
public class Information implements Serializable {
    @ApiModelProperty("名字")
    @NotEmpty(message = "用户名不能为空")
    @Length(min = 6, max = 12, message = "用户名长度必须位于6到12之间")
    private String userName;

    @ApiModelProperty("密码")
    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, message = "密码长度不能小于6位")
    private String passWord;

    @ApiModelProperty("邮箱")
    @Email(message = "请输入正确的邮箱")
    private String email;

    @ApiModelProperty("身份证")
    @Pattern(regexp = "^(\\d{18,18}|\\d{15,15}|(\\d{17,17}[x|X]))$", message = "身份证格式错误")
    private String idCard;
}
