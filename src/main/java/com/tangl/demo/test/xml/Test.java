package com.tangl.demo.test.xml;

import com.tangl.demo.util.XmlBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author: TangLiang
 * @date: 2020/6/28 15:57
 * @since: 1.0
 */
public class Test {
    public static void main(String[] args) throws Exception {
        Resource resource = new ClassPathResource("citylist.xml");
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), "utf-8"));
        StringBuffer buffer = new StringBuffer();
        String line = "";

        while ((line = br.readLine()) != null) {
            buffer.append(line);
        }

        br.close();

        // XML转为Java对象
        CityList cityList = (CityList) XmlBuilder.xmlStrToOject(CityList.class, buffer.toString());
        System.out.println(cityList.toString());
        System.out.println(cityList.getCityList().size());
        cityList.getCityList().forEach(s->{
            System.out.println(s.getCityId());
        });
    }
}
