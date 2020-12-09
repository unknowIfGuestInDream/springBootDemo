package com.tangl.demo.repository;

import com.tangl.demo.Document.LogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * 会员商品浏览历史Repository
 *
 * @author: TangLiang
 * @date: 2020/8/1 9:41
 * @since: 1.0
 */
public interface LogTomongoRepository extends MongoRepository<LogDocument, String> {
    List<LogDocument> findByOperateTypeLike(String operateType);

    List<LogDocument> findByCreateTimeBetween(String start, String end);
}
