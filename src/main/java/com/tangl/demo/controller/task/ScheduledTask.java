package com.tangl.demo.controller.task;

import java.util.concurrent.ScheduledFuture;

/**
 * ScheduledFuture是ScheduledExecutorService定时任务线程池的执行结果。
 *
 * @author: TangLiang
 * @date: 2021/1/3 17:46
 * @since: 1.0
 */
public final class ScheduledTask {

    volatile ScheduledFuture<?> future;

    /**
     * 取消定时任务
     */
    public void cancel() {
        ScheduledFuture<?> future = this.future;
        if (future != null) {
            future.cancel(true);
        }
    }
}
