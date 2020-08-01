package com.tangl.demo.repository;

import com.tangl.demo.Document.MemberReadHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * 会员商品浏览历史Repository
 *
 * @author: TangLiang
 * @date: 2020/8/1 9:41
 * @since: 1.0
 */
public interface MemberReadHistoryRepository extends MongoRepository<MemberReadHistory, String> {
    /**
     * 根据会员id按时间倒序获取浏览记录
     *
     * @param memberId 会员id
     */
    @Query("{ 'memberId' : ?0 }")
    List<MemberReadHistory> findByMemberIdOrderByCreateTimeDesc(Long memberId);
}
