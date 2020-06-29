package com.tangl.demo.test.xml3;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

/**
 * @author: TangLiang
 * @date: 2020/6/28 17:18
 * @since: 1.0
 */
public class JaxbUtil {

    @SuppressWarnings("uncheck")
    public static <T> T xmlToBean(String xmlPath, Class<T> load) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(load);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (T) unmarshaller.unmarshal(new StringReader(xmlPath));
    }


}
