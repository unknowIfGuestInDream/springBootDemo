package com.tangl.demo.job.batch.writer;

import com.tangl.demo.job.batch.entity.TestData;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 输出JSON数据
 * @author: TangLiang
 * @date: 2020/12/21 18:58
 * @since: 1.0
 */
@Component
public class JsonFileItemWriterDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ListItemReader<TestData> simpleReader;

    @Bean
    public Job jsonFileItemWriterJob() throws Exception {
        return jobBuilderFactory.get("jsonFileItemWriterJob")
                .start(step())
                .build();
    }

    private Step step() throws Exception {
        return stepBuilderFactory.get("step")
                .<TestData, TestData>chunk(2)
                .reader(simpleReader)
                .writer(jsonFileItemWriter())
                .build();
    }

    private JsonFileItemWriter<TestData> jsonFileItemWriter() throws IOException {
        // 文件输出目标地址
//        FileSystemResource file = new FileSystemResource("/Users/mrbird/Desktop/file.json");
//        Path path = Paths.get(file.getPath());
//        if (!Files.exists(path)) {
//            Files.createFile(path);
//        }
        ClassPathResource file = new ClassPathResource("batch/fileWriter.json");
        // 将对象转换为json
        JacksonJsonObjectMarshaller<TestData> marshaller = new JacksonJsonObjectMarshaller<>();
        JsonFileItemWriter<TestData> writer = new JsonFileItemWriter<>(file, marshaller);
        // 设置别名
        writer.setName("testDatasonFileItemWriter");
        return writer;
    }
}
