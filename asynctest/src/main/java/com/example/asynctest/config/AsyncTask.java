package com.example.asynctest.config;

import lombok.extern.jbosslog.JBossLog;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@JBossLog
public class AsyncTask {
    // myTaskAsynPool 配置线程池的方法名， 此处如果不写自定义线程的方法名，会使用默认的线程池
    @Async("myTaskAsyncPool")
    public void doTask1(int i) {
        log.info("Task" + i + "started.");
    }
}
