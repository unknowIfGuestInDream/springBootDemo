package com.tangl.demo.easyExcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.tangl.demo.easyexcel.DemoData;
import com.tangl.demo.easyexcel.DemoDataListener;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: TangLiang
 * @date: 2020/7/9 9:41
 * @since: 1.0
 */
@SpringBootTest
public class read {

    //https://www.yuque.com/easyexcel/doc/read

    /**
     * 读取excel
     */
    @Test
    public void readTest() {
        String fileName = "E:\\demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 参数一：读取的excel文件路径
        // 参数二：读取sheet的一行，将参数封装在DemoData实体类中
        // 参数三：读取每一行的时候会执行DemoDataListener监听器
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet()
                // 这里注意 我们也可以registerConverter来指定自定义转换器， 但是这个转换变成全局了， 所有java为string,excel为string的都会用这个转换器。
                // 如果就想单个字段使用请使用@ExcelProperty 指定converter
                //.registerConverter(new CustomStringStringConverter())
                .doRead();

        // 写法2：
//        ExcelReader excelReader = EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).build();
//        ReadSheet readSheet = EasyExcel.readSheet(0).build();
//        excelReader.read(readSheet);
//        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
//        excelReader.finish();
    }

    /**
     * 简单的写入数据到excel
     */
    @Test
    public void simpleWrite() {
        String fileName = "E:\\demo1.xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        // 参数一：写入excel文件路径
        // 参数二：写入的数据类型是DemoData
        // data()方法是写入的数据，结果是List<DemoData>集合
        EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());
    }

    private List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date().toString());
            data.setDoubleData("0.56");
            list.add(data);
        }
        return list;
    }

    /**
     * 读取多个sheet
     */
    @Test
    public void repeatedRead() {
        String fileName = "E:\\demo.xlsx";
        // 读取全部sheet
        // 这里需要注意
        // DemoDataListener的doAfterAllAnalysed 会在每个sheet读取完毕后调用一次。
        // 然后所有sheet都会往同一个DemoDataListener里面写
        DemoDataListener dl = new DemoDataListener();
        EasyExcel.read(fileName, DemoData.class, dl).doReadAll();
        dl.getList().forEach(System.out::println);

        // 读取部分sheet
        fileName = "E:\\demo.xlsx";
        ExcelReader excelReader = EasyExcel.read(fileName).build();
        // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
        // readSheet参数设置读取sheet的序号
        ReadSheet readSheet1 = EasyExcel.readSheet(0).head(DemoData.class).registerReadListener(new DemoDataListener()).build();
        ReadSheet readSheet2 = EasyExcel.readSheet(1).head(DemoData.class).registerReadListener(new DemoDataListener()).build();
        // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
        excelReader.read(readSheet1, readSheet2);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
    }
}
