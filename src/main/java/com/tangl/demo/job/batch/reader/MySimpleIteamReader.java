package com.tangl.demo.job.batch.reader;

import org.springframework.batch.item.ItemReader;

import java.util.Iterator;
import java.util.List;

/**
 * 自定义一个ItemReader的实现类，实现简单数据的读取
 *
 * @author: TangLiang
 * @date: 2020/12/20 19:56
 * @since: 1.0
 */
public class MySimpleIteamReader implements ItemReader<String> {

    //泛型指定读取数据的格式，这里读取的是String类型的List，
    // read()方法的实现也很简单，就是遍历集合数据
    private Iterator<String> iterator;

    public MySimpleIteamReader(List<String> data) {
        this.iterator = data.iterator();
    }

    @Override
    public String read() {
        // 数据一个接着一个读取
        return iterator.hasNext() ? iterator.next() : null;
    }
}
