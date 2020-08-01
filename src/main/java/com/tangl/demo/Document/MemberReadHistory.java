package com.tangl.demo.Document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 用户商品浏览历史记录
 *
 * @author: TangLiang
 * @date: 2020/8/1 9:42
 * @since: 1.0
 */
@Document
@Data
public class MemberReadHistory {
    //文档对象的ID域添加@Id注解，需要检索的字段添加@Indexed注解
    @Id
    private String id;
    @Indexed
    private Long memberId;
    private String memberNickname;
    private String memberIcon;
    @Indexed
    private Long productId;
    private String productName;
    private String productPic;
    private String productSubTitle;
    private String productPrice;
    private Date createTime;
}
