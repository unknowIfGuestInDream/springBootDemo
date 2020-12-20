package com.tangl.demo.job.batch.read;

import com.tangl.demo.job.batch.reader.MySimpleIteamReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 测试定义的MySimpleIteamReader
 *
 * @author: TangLiang
 * @date: 2020/12/20 19:58
 * @since: 1.0
 */
@Component
public class MySimpleItemReaderDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    //我们通过mySimpleItemReader()方法创建了一个MySimpleIteamReader，
    // 并且传入了List数据。上面代码大体和上一节中介绍的差不多，最主要的区别就是Step的创建过程稍有不同
    //在MySimpleItemReaderDemo类中，我们通过StepBuilderFactory创建步骤Step，
    // 不过不再是使用tasklet()方法创建，而是使用chunk()方法。
    // chunk字面上的意思是“块”的意思，可以简单理解为数据块，
    // 泛型<String, String>用于指定读取的数据和输出的数据类型，
    // 构造器入参指定了数据块的大小，比如指定为2时表示每当读取2组数据后做一次数据输出处理。
    // 接着reader()方法指定读取数据的方式，该方法接收ItemReader的实现类，
    // 这里使用的是我们自定义的MySimpleIteamReader。writer()方法指定数据输出方式，
    // 因为这块不是本文的重点，所以先简单遍历输出即可。
    @Bean
    public Job mySimpleItemReaderJob() {
        return jobBuilderFactory.get("mySimpleItemReaderJob")
                .start(step())
                .build();
    }

    private Step step() {
        return stepBuilderFactory.get("step")
                .<String, String>chunk(2)
                .reader(mySimpleItemReader())
                .writer(list -> list.forEach(System.out::println))  // 简单输出，后面再详细介绍writer
                .build();
    }

    private ItemReader<String> mySimpleItemReader() {
        List<String> data = Arrays.asList("java", "c++", "javascript", "python");
        return new MySimpleIteamReader(data);
    }
}
