package com.tangl.demo.job.batch.start;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 任务嵌套
 *
 * @author: TangLiang
 * @date: 2020/12/20 19:46
 * @since: 1.0
 */
@Component
public class NestedJobDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    //任务Job除了可以由Step或者Flow构成外，我们还可以将多个任务Job转换为特殊的Step，
    // 然后再赋给另一个任务Job，这就是任务的嵌套

    /*
    我们通过childJobOne()和childJobTwo()方法创建了两个任务Job，这里没什么好说的，前面都介绍过。
    关键在于childJobOneStep()方法和childJobTwoStep()方法。
    在childJobOneStep()方法中，我们通过JobStepBuilder构建了一个名称为childJobOneStep的Step，
    顾名思义，它是一个任务型Step的构造工厂，可以将任务转换为“特殊”的步骤。在构建过程中，
    我们还需要传入任务执行器JobLauncher、任务仓库JobRepository和事务管理器PlatformTransactionManager。
    将任务转换为特殊的步骤后，将其赋给父任务parentJob即可，流程和前面介绍的一致
     */

    // 父任务
    @Bean
    public Job parentJob() {
        return jobBuilderFactory.get("parentJob")
                .start(childJobOneStep())
                .next(childJobTwoStep())
                .build();
    }

    // 将任务转换为特殊的步骤
    private Step childJobOneStep() {
        return new JobStepBuilder(new StepBuilder("childJobOneStep"))
                .job(childJobOne())
                .launcher(jobLauncher)
                .repository(jobRepository)
                .transactionManager(platformTransactionManager)
                .build();
    }

    // 将任务转换为特殊的步骤
    private Step childJobTwoStep() {
        return new JobStepBuilder(new StepBuilder("childJobTwoStep"))
                .job(childJobTwo())
                .launcher(jobLauncher)
                .repository(jobRepository)
                .transactionManager(platformTransactionManager)
                .build();
    }

    // 子任务一
    private Job childJobOne() {
        return jobBuilderFactory.get("childJobOne")
                .start(
                        stepBuilderFactory.get("childJobOneStep")
                                .tasklet((stepContribution, chunkContext) -> {
                                    System.out.println("子任务一执行步骤。。。");
                                    return RepeatStatus.FINISHED;
                                }).build()
                ).build();
    }

    // 子任务二
    private Job childJobTwo() {
        return jobBuilderFactory.get("childJobTwo")
                .start(
                        stepBuilderFactory.get("childJobTwoStep")
                                .tasklet((stepContribution, chunkContext) -> {
                                    System.out.println("子任务二执行步骤。。。");
                                    return RepeatStatus.FINISHED;
                                }).build()
                ).build();
    }
}
