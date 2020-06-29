package com.tangl.demo.test.xml3;

import com.tangl.demo.test.xml2.StoreList;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author: TangLiang
 * @date: 2020/6/28 17:20
 * @since: 1.0
 */
public class Test {
    public static void main(String[] args) throws IOException, JAXBException {
        Resource resource = new ClassPathResource("string.xml");
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), "utf-8"));
        StringBuffer buffer = new StringBuffer();
        String line = "";

        while ((line = br.readLine()) != null) {
            buffer.append(line);
        }

        br.close();

        StoreList storeList = JaxbUtil.xmlToBean(buffer.toString(),StoreList.class);
        storeList.getStoreList().forEach(s->{
            System.out.println(s.getStore_id());
        });
    }
}
