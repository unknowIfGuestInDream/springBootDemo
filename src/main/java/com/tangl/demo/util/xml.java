package com.tangl.demo.util;

import com.tangl.demo.test.xml.City;
import com.tangl.demo.test.xml.CityList;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author: TangLiang
 * @date: 2020/6/28 17:03
 * @since: 1.0
 */
public class xml {
    public static List<City> listCity() throws Exception {
        // 读取XML文件
        Resource resource = new ClassPathResource("citylist.xml");
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), "utf-8"));
        StringBuffer buffer = new StringBuffer();
        String line = "";

        while ((line = br.readLine()) !=null) {
            buffer.append(line);
        }

        br.close();

        // XML转为Java对象
        CityList cityList = (CityList)XmlBuilder.xmlStrToOject(CityList.class, buffer.toString());
        return cityList.getCityList();
    }
}
