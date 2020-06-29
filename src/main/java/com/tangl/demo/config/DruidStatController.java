package com.tangl.demo.config;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * /druid-stat 接口获取到统计数据
 *
 * @author 唐亮
 * @date 22:40 2020/6/28
 * @return
 */
@RestController
public class DruidStatController {
    @GetMapping("/druid-stat")
    public Object druidStat() {
        // DruidStatManagerFacade#getDataSourceStatDataList 该方法可以获取所有数据源的监控数据
        // 除此之外 DruidStatManagerFacade 还提供了一些其他方法，我们可以按需选择使用。
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }
}
