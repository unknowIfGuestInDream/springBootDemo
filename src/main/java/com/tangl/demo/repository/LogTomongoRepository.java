package com.tangl.demo.repository;

import com.tangl.demo.Document.LogDocument;
import com.tangl.demo.rabbitMQ.LogToMongoMQ;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 会员商品浏览历史Repository
 *
 * @author: TangLiang
 * @date: 2020/8/1 9:41
 * @since: 1.0
 */
public interface LogTomongoRepository extends MongoRepository<LogDocument, String> {
}
