package com.tangl.demo.test.xml2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author: TangLiang
 * @date: 2020/6/28 17:04
 * @since: 1.0
 */
@XmlRootElement(name = "string")
@XmlAccessorType(XmlAccessType.FIELD)
public class StoreAll {
    @XmlElement(name = "items")
    private List<StoreList> storeAllList;

    public List<StoreList> getStoreAllList() {
        return storeAllList;
    }

    public void setStoreAllList(List<StoreList> storeAllList) {
        this.storeAllList = storeAllList;
    }


}
