package com.tangl.demo.service;

import com.tangl.demo.Document.MemberReadHistory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员浏览记录管理Service
 *
 * @author: TangLiang
 * @date: 2020/8/1 9:47
 * @since: 1.0
 */
public interface MemberReadHistoryService {
    /**
     * 生成浏览记录
     */
    int create(MemberReadHistory memberReadHistory);


    /**
     * 批量删除浏览记录
     */
    int delete(List<String> ids);


    /**
     * 获取用户浏览历史记录
     */
    List<MemberReadHistory> list(Long memberId);
}
