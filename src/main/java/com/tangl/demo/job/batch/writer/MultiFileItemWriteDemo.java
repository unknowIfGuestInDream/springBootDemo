package com.tangl.demo.job.batch.writer;

import com.tangl.demo.job.batch.entity.TestData;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * 多文本输出
 *
 * @author: TangLiang
 * @date: 2020/12/21 19:08
 * @since: 1.0
 */
@Component
public class MultiFileItemWriteDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private ListItemReader<TestData> simpleReader;
    @Autowired
    private ItemStreamWriter<TestData> fileItemWriter;
    @Autowired
    private ItemStreamWriter<TestData> xmlFileItemWriter;

    @Bean
    public Job multiFileItemWriterJob() {
        return jobBuilderFactory.get("multiFileItemWriterJob")
                .start(step())
                .build();
    }

    private Step step() {
        return stepBuilderFactory.get("step")
                .<TestData, TestData>chunk(2)
                .reader(simpleReader)
                .writer(classifierMultiFileItemWriter())
                .stream(fileItemWriter)
                .stream(xmlFileItemWriter)
                .build();
    }

    //ClassifierCompositeItemWriter可以设置不同条件下使用不同的ItemWriter输出数据，
    // 此外在Step中，还需通过StepBuilderFactory的stream()方法传入使用到的ItemWriter
    // （这里需要注意的是，注入的时候，类型应选择ItemStreamWriter）
    // 将数据分类，然后分别输出到对应的文件(此时需要将writer注册到ioc容器，否则报
    // WriterNotOpenException: Writer must be open before it can be written to)
    private ClassifierCompositeItemWriter<TestData> classifierMultiFileItemWriter() {
        ClassifierCompositeItemWriter<TestData> writer = new ClassifierCompositeItemWriter<>();
        writer.setClassifier((Classifier<TestData, ItemWriter<? super TestData>>) testData -> {
            try {
                // id能被2整除则输出到普通文本，否则输出到xml文本
                return testData.getId() % 2 == 0 ? fileItemWriter : xmlFileItemWriter;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
        return writer;
    }

    //如果不想用分类，希望所有数据都输出到对应格式的文本中，则可以使用CompositeItemWriter作为代理输出
    //private Step step() {
    //        return stepBuilderFactory.get("step")
    //                .<TestData, TestData>chunk(2)
    //                .reader(simpleReader)
    //                .writer(multiFileItemWriter())
    //                .build();
    //    }
    //    // 输出数据到多个文件
    //    private CompositeItemWriter<TestData> multiFileItemWriter() {
    //        // 使用CompositeItemWriter代理
    //        CompositeItemWriter<TestData> writer = new CompositeItemWriter<>();
    //        // 设置具体写代理
    //        writer.setDelegates(Arrays.asList(fileItemWriter, xmlFileItemWriter));
    //        return writer;
    //    }
}