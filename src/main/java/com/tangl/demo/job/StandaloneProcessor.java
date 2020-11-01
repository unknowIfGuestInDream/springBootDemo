package com.tangl.demo.job;

import com.github.kfcfans.powerjob.worker.core.processor.ProcessResult;
import com.github.kfcfans.powerjob.worker.core.processor.TaskContext;
import com.github.kfcfans.powerjob.worker.core.processor.sdk.BasicProcessor;
import com.github.kfcfans.powerjob.worker.log.OmsLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: TangLiang
 * @date: 2020/11/1 10:39
 * @since: 1.0
 */
@Slf4j
@Component
public class StandaloneProcessor implements BasicProcessor {

    @Override
    public ProcessResult process(TaskContext context) {
        //OmsLogger可以直接将日志上报到powerjob-server
        OmsLogger omsLogger = context.getOmsLogger();
        omsLogger.info("StandaloneProcessor start process,context is {}.", context);
        log.info("jobParams is {}", context.getJobParams());
        return new ProcessResult(true, "Process success!");
    }
}
