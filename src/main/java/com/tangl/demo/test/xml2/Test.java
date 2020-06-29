package com.tangl.demo.test.xml2;

import com.tangl.demo.test.xml.CityList;
import com.tangl.demo.util.XmlBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author: TangLiang
 * @date: 2020/6/28 16:56
 * @since: 1.0
 */
public class Test {
    public static void main(String[] args) throws Exception {
        Resource resource = new ClassPathResource("string.xml");
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), "utf-8"));
        StringBuffer buffer = new StringBuffer();
        String line = "";

        while ((line = br.readLine()) != null) {
            buffer.append(line);
        }

        br.close();

        // XML转为Java对象
        StoreList storeList = (StoreList) XmlBuilder.xmlStrToOject(StoreList.class, buffer.toString());
        storeList.getStoreList().forEach(s->{
            System.out.println(s.getStore_id());
            System.out.println(s.getStore_desc());
        });

//        StoreAll storeAll = (StoreAll) XmlBuilder.xmlStrToOject(StoreAll.class, buffer.toString());
//        System.out.println(storeAll);
//        System.out.println(storeAll.getStoreAllList().size());
//        storeAll.getStoreAllList().forEach(s->{
//            System.out.println(s.getStoreList().size());
//            s.getStoreList().forEach(d->{
//                System.out.println(d.getStore_desc());
//                System.out.println(d.getStore_id());
//            });
//        });


    }


}
