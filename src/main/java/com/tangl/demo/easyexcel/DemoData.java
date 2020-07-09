package com.tangl.demo.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import org.springframework.stereotype.Component;

public class DemoData {
    /**
     * 用名字去匹配，这里需要注意，如果名字重复，会导致只有一个字段读取到数据
     */
    @ExcelProperty(value = "字符串标题"
            //, converter = CustomStringStringConverter.class
    )
    private String string;
    /**
     * 这里用string 去接日期才能格式化
     */
    @ExcelProperty("日期标题")
    @DateTimeFormat("yyyy年MM月dd日 HH时mm分ss秒")
    private String date;
    /**
     * 强制读取第三个 这里不建议 index 和 name 同时用，要么一个对象只用index，要么一个对象只用name去匹配
     */
    @ExcelProperty(index = 2)
    /**
     * 我想接收百分比的数字
     */
    @NumberFormat("#.##%")
    private String doubleData;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoubleData() {
        return doubleData;
    }

    public void setDoubleData(String doubleData) {
        this.doubleData = doubleData;
    }

    @Override
    public String toString() {
        return "DemoData{" +
                "string='" + string + '\'' +
                ", date='" + date + '\'' +
                ", doubleData='" + doubleData + '\'' +
                '}';
    }
    // getting setting
}