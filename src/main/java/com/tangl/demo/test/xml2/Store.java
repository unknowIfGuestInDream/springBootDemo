package com.tangl.demo.test.xml2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author: TangLiang
 * @date: 2020/6/28 16:51
 * @since: 1.0
 */
@XmlRootElement(name = "item")
@XmlType(name = "", propOrder = {
        "store_id",
        "store_desc"
})
@XmlAccessorType(XmlAccessType.FIELD)
public class Store {

    private String store_id;

    private String store_desc;

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getStore_desc() {
        return store_desc;
    }

    public void setStore_desc(String store_desc) {
        this.store_desc = store_desc;
    }
}
