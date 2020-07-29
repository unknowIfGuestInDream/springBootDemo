package com.tangl.demo.async;

import org.apache.http.conn.HttpClientConnectionManager;

import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

/**
 * 关闭失效连接
 *
 * @author: TangLiang
 * @date: 2020/7/29 8:59
 * @since: 1.0
 */
public class IdleConnectionEvictor extends Thread {
    private final HttpClientConnectionManager manager;

    private Integer waitTime;

    private Integer idleConTime;

    private volatile boolean shutdown = true;

    public IdleConnectionEvictor(HttpClientConnectionManager manager, Integer waitTime, Integer idleConTime) {
        this.manager = manager;
        this.waitTime = waitTime;
        this.idleConTime = idleConTime;
        this.start();
    }

    @Override
    public void run() {
        try {
            if (shutdown) {
                synchronized (this) {
                    wait(waitTime);
                    manager.closeIdleConnections(idleConTime, TimeUnit.SECONDS);
                    // 关闭失效的连接
                    manager.closeExpiredConnections();
                }
            }
        } catch (Exception e) {

        }
    }

    @PreDestroy
    public void shutdown() {
        shutdown = false;
        synchronized (this) {
            notifyAll();
        }
    }
}
