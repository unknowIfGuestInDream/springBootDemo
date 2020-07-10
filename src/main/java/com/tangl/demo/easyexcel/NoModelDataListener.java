package com.tangl.demo.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: TangLiang
 * @date: 2020/7/9 11:17
 * @since: 1.0
 */
@Slf4j
public class NoModelDataListener extends AnalysisEventListener<Map<Integer, String>> {

    //private static final Logger logger = LoggerFactory.getLogger(NoModelDataListener.class);

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
//        if (list.size() >= BATCH_COUNT) {
//            saveData();
//            list.clear();
//        }
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        log.info("所有数据解析完成！");
    }

    public List<Map<Integer, String>> getList() {
        return list;
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
    }
}
