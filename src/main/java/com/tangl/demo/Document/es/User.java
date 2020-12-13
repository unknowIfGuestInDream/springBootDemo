package com.tangl.demo.Document.es;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.List;

/**
 * @author: TangLiang
 * @date: 2020/12/13 13:51
 * @since: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//索引库名为index_name，分片为5，每片备份1片
@Document(indexName = "esuser", shards = 5, replicas = 1)
public class User implements Serializable {
    @Id
    private String id;
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Integer)
    private Integer age;
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String work;
    @Field(type = FieldType.Nested)
    private List<String> hobby;
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String desc;
}
