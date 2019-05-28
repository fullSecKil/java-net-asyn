package com.example.asynctest.controller;

import com.example.asynctest.pojo.DeferredResultHolder;
import com.example.asynctest.pojo.MockQueue;
import lombok.extern.jbosslog.JBossLog;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

@RestController
@JBossLog
public class AsyncController {
    @Autowired
    private MockQueue mockQueue;
    @Autowired
    private DeferredResultHolder deferredResultHolder;

    /**
     * 单线程测试
     *
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/order")
    public Object order() throws InterruptedException {
        log.info("主线程开始");
        Thread.sleep(1000);
        log.info("主线程返回");
        return "success1";
    }

    /**
     * 用Callable实现异步
     *
     * @return
     */
    @GetMapping("/orderAsync")
    public Object orderAsync() {
        log.info("主线程开始");
        Callable result = new Callable() {
            @Override
            public Object call() throws Exception {
                log.info("副线程开始");
                Thread.sleep(10000);
                log.info("副线程开始");
                return "success2";
            }
        };
        log.info("主线程返回");
        return result;
    }

    @GetMapping("/orderMockQueue")
    public DeferredResult orderQueue() throws InterruptedException {
        log.info("主线程开始");

        // 随机生成8位数
        String orderNumber = RandomStringUtils.randomNumeric(8);
        mockQueue.setPlaceOrder(orderNumber);

        DeferredResult result = new DeferredResult();
        deferredResultHolder.getMap().put(orderNumber, result);
        Thread.sleep(1000);
        log.info("主线程返回");

        return result;
    }
}
