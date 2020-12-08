package com.tangl.demo.hutool;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * hutool json测试
 *
 * @author: TangLiang
 * @date: 2020/12/8 11:44
 * @since: 1.0
 */
@SpringBootTest
public class JsonTest {

    //JSON字符串解析
    //JSON对象转字符串（一行） jsonObject.toString();
    // 也可以美化一下，即显示出带缩进的JSON： jsonObject.toStringPretty();
    @Test
    public void StringToJson() {
        String jsonStr = "{\"b\":\"value2\",\"c\":\"value3\",\"a\":\"value1\"}";
        //方法一：使用工具类转换
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        //方法二：new的方式转换
        JSONObject jsonObject2 = new JSONObject(jsonStr);
        System.out.println(jsonObject.toString());
        System.out.println(jsonObject2.toStringPretty());
    }

    //javaBean解析为JSON
    @Test
    public void javaBeanToJson() {
        UserA userA = new UserA();
        userA.setName("nameTest");
        userA.setDate(new Date());
        userA.setSqs(CollectionUtil.newArrayList(new Seq(null), new Seq("seq2")));

        // false表示不跳过空值
        JSONObject json = JSONUtil.parseObj(userA, false);
        Console.log(json.toStringPretty());

        //输出的字段顺序和Bean的字段顺序不一致，如果想保持一致，可以
        // 第二个参数表示保持有序
        JSONObject json2 = JSONUtil.parseObj(userA, false, true);
        Console.log(json2.toStringPretty());

        //默认的，Hutool将日期输出为时间戳，如果需要自定义日期格式，可以调用：
        json.setDateFormat("yyyy-MM-dd HH:mm:ss");
        Console.log(json.toStringPretty());
    }

    //初始化JSONObject赋值
    @Test
    public void hutoolJsontest1() {
        JSONObject json1 = JSONUtil.createObj()
                .put("a", "value1")
                .put("b", "value2")
                .put("c", "value3");
        System.out.println(json1);
        System.out.println(json1.get("a"));
        System.out.println(json1.getStr("b"));
    }

    //Map解析为Json
    @Test
    public void hutoolJsontest2() {
        Map mail = new HashMap();
        mail.put("id", "006");
        mail.put("code", "884557");
        mail.put("mail", "1195542421@qq.com");
        mail.put("subject", "邮件摸板测试");
        JSONObject json = JSONUtil.parseObj(mail);
        System.out.println(json.toStringPretty());
        //JSONUtil 将map转换为json的方法
        JSONObject json1 = JSONUtil.parseFromMap(mail);
        System.out.println(json.toStringPretty());

        Map map = JSONUtil.toBean(json1,HashMap.class);
        System.out.println(map.size());
    }

}
