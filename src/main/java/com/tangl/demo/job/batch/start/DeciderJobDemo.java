package com.tangl.demo.job.batch.start;

import com.tangl.demo.job.batch.decider.MyDecider;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 任务决策器
 *
 * @author: TangLiang
 * @date: 2020/12/20 19:42
 * @since: 1.0
 */
@Component
public class DeciderJobDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private MyDecider myDecider;

    //任务deciderJob首先执行step1，然后指定自定义决策器，
    // 如果决策器返回weekend，那么执行step2，
    // 如果决策器返回workingDay，那么执行step3。
    // 如果执行了step3，那么无论step3的结果是什么，都将执行step4
    @Bean
    public Job deciderJob() {
        return jobBuilderFactory.get("deciderJob")
                .start(step1())
                .next(myDecider)
                .from(myDecider).on("weekend").to(step2())
                .from(myDecider).on("workingDay").to(step3())
                .from(step3()).on("*").to(step4())
                .end()
                .build();
    }

    private Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("执行步骤一操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("执行步骤二操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("执行步骤三操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Step step4() {
        return stepBuilderFactory.get("step4")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("执行步骤四操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}