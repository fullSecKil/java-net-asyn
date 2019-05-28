package com.example.asynctest.pojo;

import lombok.extern.jbosslog.JBossLog;
import org.springframework.stereotype.Component;

/**
 * 模拟消息队列处理 类
 */
@Component
@JBossLog
public class MockQueue {
    // 下单消息
    private String placeOrder;
    // 订单完成消息
    private String completeOrder;

    public String getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(String placeOrder) {
        new Thread(()->{
            log.info("接到下单请求" + placeOrder);
            // 模拟处理
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 给completeOrder 赋值
            this.completeOrder = placeOrder;
            log.info("下单请求处理完毕" + placeOrder);
        }).start();
    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}
