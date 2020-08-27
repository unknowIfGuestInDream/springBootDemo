package com.tangl.demo.Document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author: TangLiang
 * @date: 2020/8/27 17:32
 * @since: 1.0
 */
@Document(collection = "user")
@Data
public class User {
    @Id
    private String id;
    private String name;
    private Integer age;
    private String description;
}
