package com.tangl.demo.test.xml2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author: TangLiang
 * @date: 2020/6/28 16:54
 * @since: 1.0
 */
@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class StoreList {

    @XmlElement(name = "item")
    private List<Store> storeList;

    public List<Store> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<Store> storeList) {
        this.storeList = storeList;
    }

    @Override
    public String toString() {
        String result = "StoreList{";
//        storeList.stream().map(store -> {
//            result += " store_id=" + store.getStore_id() +
//                   " store_desc=" + store.getStore_desc();
//        }).collect()
//        return storeList.forEach(store -> {
//            result += " store_id=" + store.getStore_id() +
//                    " store_desc=" + store.getStore_desc();
//                });
//                + "storeList=" + storeList +
//                '}';
        return null;
    }
}
